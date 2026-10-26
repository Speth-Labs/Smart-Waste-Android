$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$adb = Join-Path $env:LOCALAPPDATA 'Android\Sdk\platform-tools\adb.exe'
$gradleWrapper = Join-Path $projectRoot 'gradlew.bat'
$apk = Join-Path $projectRoot 'app\build\outputs\apk\debug\app-debug.apk'
$packageName = 'com.uoi.pratikumwithbunafla'
$studioJbr = Join-Path $env:ProgramFiles 'Android\Android Studio\jbr'
$webRoot = Join-Path (Split-Path -Parent $projectRoot) 'Waste 2'
$updatesFolder = Join-Path $webRoot 'updates'
$runtimeWebRoot = 'C:\xampp\htdocs\Waste2'
$runtimeUpdatesFolder = Join-Path $runtimeWebRoot 'updates'
$lanConfigPath = Join-Path $projectRoot 'app\lan.properties'
$stateFolder = Join-Path $env:LOCALAPPDATA 'SmartWaste\AutoDeploy'
$logPath = Join-Path $stateFolder 'auto-deploy.log'
$pidPath = Join-Path $stateFolder 'auto-deploy.pid'

if (-not (Test-Path -LiteralPath $adb)) {
    throw "ADB tidak ditemukan: $adb"
}
if (-not (Test-Path -LiteralPath $gradleWrapper)) {
    throw "Gradle wrapper tidak ditemukan: $gradleWrapper"
}

New-Item -ItemType Directory -Path $stateFolder -Force | Out-Null
Set-Content -LiteralPath $pidPath -Value $PID -Encoding ASCII

function Write-Log {
    param([string]$Message)
    $line = "[{0}] {1}" -f (Get-Date -Format 'yyyy-MM-dd HH:mm:ss'), $Message
    Add-Content -LiteralPath $logPath -Value $line -Encoding UTF8
}

function Test-RelevantChange {
    param([string]$RelativePath)

    $path = $RelativePath.Replace('\', '/')
    if ($path.StartsWith('app/src/', [StringComparison]::OrdinalIgnoreCase)) {
        return $true
    }

    return $path -in @(
        'app/lan.properties',
        'app/build.gradle.kts',
        'build.gradle.kts',
        'settings.gradle.kts',
        'gradle.properties',
        'gradle/libs.versions.toml'
    )
}

function Get-LanServerIPv4 {
    $address = Get-NetIPAddress -AddressFamily IPv4 -AddressState Preferred -ErrorAction Stop |
        Where-Object {
            $_.InterfaceAlias -match 'Wi-Fi|Wireless|WLAN' -and
            $_.IPAddress -notlike '169.254.*' -and
            $_.IPAddress -ne '127.0.0.1'
        } |
        Select-Object -First 1 -ExpandProperty IPAddress

    if (-not $address) {
        throw 'Tidak ditemukan IP IPv4 Wi-Fi. Sambungkan laptop ke Wi-Fi yang sama dengan HP.'
    }
    return $address
}

function Get-NextPublishedVersion {
    $manifestPath = Join-Path $updatesFolder 'latest.json'
    if (-not (Test-Path -LiteralPath $manifestPath)) {
        return 2
    }

    $manifest = Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json
    return ([int]$manifest.version_code) + 1
}

function Test-NeedsInitialBuild {
    $manifestPath = Join-Path $updatesFolder 'latest.json'
    if (-not (Test-Path -LiteralPath $manifestPath)) {
        return $true
    }

    $manifest = Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json
    $publishedApk = Join-Path $updatesFolder ("SmartWaste-{0}.apk" -f [int]$manifest.version_code)
    if (-not (Test-Path -LiteralPath $publishedApk)) {
        return $true
    }

    $publishedAt = (Get-Item -LiteralPath $manifestPath).LastWriteTimeUtc
    $sourceFiles = Get-ChildItem -LiteralPath (Join-Path $projectRoot 'app\src') -File -Recurse
    $configFiles = @(
        'app\lan.properties',
        'app\build.gradle.kts',
        'build.gradle.kts',
        'settings.gradle.kts',
        'gradle.properties',
        'gradle\libs.versions.toml'
    ) | ForEach-Object { Join-Path $projectRoot $_ } |
        Where-Object { Test-Path -LiteralPath $_ } |
        ForEach-Object { Get-Item -LiteralPath $_ }

    foreach ($file in @($sourceFiles) + @($configFiles)) {
        if ($file.LastWriteTimeUtc -gt $publishedAt) {
            return $true
        }
    }
    return $false
}

function Get-Sha256Hex {
    param([string]$Path)

    $stream = [System.IO.File]::OpenRead($Path)
    $sha256 = [System.Security.Cryptography.SHA256]::Create()
    try {
        $hash = $sha256.ComputeHash($stream)
        return [System.BitConverter]::ToString($hash).Replace('-', '').ToLowerInvariant()
    }
    finally {
        $stream.Dispose()
        $sha256.Dispose()
    }
}

function Publish-LanUpdate {
    param(
        [int]$VersionCode,
        [string]$VersionName,
        [string]$ServerIp
    )

    $publishFolders = @($updatesFolder, $runtimeUpdatesFolder) | Select-Object -Unique
    foreach ($folder in $publishFolders) {
        if ($folder -eq $runtimeUpdatesFolder -and -not (Test-Path -LiteralPath $runtimeWebRoot)) {
            throw "Folder web XAMPP tidak ditemukan: $runtimeWebRoot"
        }
        New-Item -ItemType Directory -Path $folder -Force | Out-Null
    }

    $apkName = "SmartWaste-$VersionCode.apk"
    foreach ($folder in $publishFolders) {
        Copy-Item -LiteralPath $apk -Destination (Join-Path $folder $apkName) -Force
    }
    $sha256 = Get-Sha256Hex -Path $apk
    $manifest = [ordered]@{
        package_name = $packageName
        version_code = $VersionCode
        version_name = $VersionName
        apk_url = "http://${ServerIp}:8080/Waste2/updates/$apkName"
        sha256 = $sha256
        notes = 'Perbaikan dan pembaruan Smart Waste.'
    }
    $json = $manifest | ConvertTo-Json -Compress
    foreach ($folder in $publishFolders) {
        $manifestPath = Join-Path $folder 'latest.json'
        $temporaryManifestPath = "$manifestPath.tmp"
        [System.IO.File]::WriteAllText($temporaryManifestPath, $json, [System.Text.UTF8Encoding]::new($false))
        Move-Item -LiteralPath $temporaryManifestPath -Destination $manifestPath -Force
    }
    Write-Log "Pembaruan LAN versi $VersionName diterbitkan ke folder web dan XAMPP: http://${ServerIp}:8080/Waste2/updates/latest.json."
}

function Get-ConnectedPhoneSerial {
    $rows = & $adb devices -l 2>$null
    $serials = @(
        foreach ($row in ($rows | Select-Object -Skip 1)) {
            $parts = ($row -split '\s+') | Where-Object { $_ }
            if ($parts.Count -ge 2 -and $parts[1] -eq 'device' -and $parts[0] -notlike 'emulator-*') {
                $parts[0]
            }
        }
    )

    if ($serials.Count -eq 1) {
        return $serials[0]
    }
    if ($serials.Count -gt 1) {
        Write-Log 'Ada lebih dari satu HP fisik terhubung; pemasangan ditunda agar tidak salah perangkat.'
    }
    return $null
}

function Build-DebugApk {
    $oldJavaHome = $env:JAVA_HOME
    $oldTemp = $env:TEMP
    $oldTmp = $env:TMP
    $oldPath = $env:PATH
    $versionCode = 0
    $versionName = ''
    $serverIp = ''

    try {
        $serverIp = Get-LanServerIPv4
        $versionCode = Get-NextPublishedVersion
        $versionName = '1.{0}' -f ($versionCode - 1)
        @(
            "apiHost=$serverIp"
            "versionCode=$versionCode"
            "versionName=$versionName"
        ) | Set-Content -LiteralPath $lanConfigPath -Encoding ASCII

        if (Test-Path -LiteralPath $studioJbr) {
            $env:JAVA_HOME = $studioJbr
            $env:PATH = "$studioJbr\bin;$oldPath"
        }
        if (Test-Path -LiteralPath 'C:\Temp') {
            $env:TEMP = 'C:\Temp'
            $env:TMP = 'C:\Temp'
        }

        Write-Log 'Perubahan source terdeteksi; mulai build APK debug.'
        Push-Location $projectRoot
        try {
            & $gradleWrapper --no-daemon :app:assembleDebug *>> $logPath
            $buildExitCode = $LASTEXITCODE
        }
        finally {
            Pop-Location
        }

        if ($buildExitCode -ne 0 -or -not (Test-Path -LiteralPath $apk)) {
            Write-Log "Build gagal (exit code $buildExitCode); APK lama tidak dipasang. Cek log untuk detail."
            return $false
        }

        Publish-LanUpdate -VersionCode $versionCode -VersionName $versionName -ServerIp $serverIp
        Write-Log 'Build berhasil; menunggu HP terhubung untuk pemasangan.'
        return $true
    }
    catch {
        Write-Log "Build gagal: $($_.Exception.Message)"
        return $false
    }
    finally {
        $env:JAVA_HOME = $oldJavaHome
        $env:TEMP = $oldTemp
        $env:TMP = $oldTmp
        $env:PATH = $oldPath
    }
}

function Install-And-Launch {
    param([string]$Serial)

    Write-Log "Memasang APK ke HP $Serial."
    & $adb -s $Serial install -r $apk *>> $logPath
    if ($LASTEXITCODE -ne 0) {
        Write-Log 'Pemasangan gagal; akan dicoba lagi saat perangkat siap.'
        return $false
    }

    & $adb -s $Serial shell am force-stop $packageName *>> $logPath
    & $adb -s $Serial shell am start -n "$packageName/.MainActivity" *>> $logPath
    if ($LASTEXITCODE -ne 0) {
        Write-Log 'APK terpasang, tetapi aplikasi tidak berhasil dibuka.'
        return $false
    }

    Write-Log 'APK berhasil dipasang dan aplikasi dibuka di HP.'
    return $true
}

$watcher = New-Object System.IO.FileSystemWatcher
$watcher.Path = $projectRoot
$watcher.Filter = '*'
$watcher.IncludeSubdirectories = $true
$watcher.NotifyFilter = [IO.NotifyFilters]::LastWrite -bor [IO.NotifyFilters]::FileName -bor [IO.NotifyFilters]::Size
$watcher.InternalBufferSize = 65536
$watcher.EnableRaisingEvents = $true

$pendingInstall = $false
$pendingBuild = $false
$nextBuildRetry = [datetime]::MinValue
$lastNoPhoneLog = [datetime]::MinValue
Write-Log 'Auto-update aktif. Hanya satu HP fisik yang diizinkan; emulator tidak akan dipakai.'
Write-Log 'Aktifkan USB debugging dan setujui dialog otorisasi di HP saat pertama kali tersambung.'

try {
    if (Test-NeedsInitialBuild) {
        # Queue the latest source so a temporary Wi-Fi/build failure can be retried.
        $pendingBuild = $true
    } else {
        Write-Log 'Manifest update LAN sudah sesuai dengan source; tidak perlu build awal.'
    }

    while ($true) {
        if ($pendingBuild -and (Get-Date) -ge $nextBuildRetry) {
            if (Build-DebugApk) {
                $pendingInstall = $true
                $pendingBuild = $false
            } else {
                $nextBuildRetry = (Get-Date).AddMinutes(1)
                Write-Log 'Build belum berhasil; perubahan tetap antre dan akan dicoba lagi dalam 1 menit.'
            }
        }

        if ($pendingInstall) {
            $phoneSerial = Get-ConnectedPhoneSerial
            if ($phoneSerial) {
                if (Install-And-Launch -Serial $phoneSerial) {
                    $pendingInstall = $false
                }
            }
            elseif (((Get-Date) - $lastNoPhoneLog).TotalSeconds -ge 30) {
                Write-Log 'Belum ada satu HP fisik yang siap; menunggu sambungan USB debugging. Emulator diabaikan.'
                $lastNoPhoneLog = Get-Date
            }
        }

        $change = $watcher.WaitForChanged([IO.WatcherChangeTypes]::All, 3000)
        if ($change.TimedOut -or -not (Test-RelevantChange -RelativePath $change.Name)) {
            continue
        }

        # Debounce editor save bursts so one edit triggers only one build.
        $lastRelevantChange = Get-Date
        while (((Get-Date) - $lastRelevantChange).TotalMilliseconds -lt 1100) {
            $remaining = [math]::Max(100, 1100 - [int]((Get-Date) - $lastRelevantChange).TotalMilliseconds)
            $nextChange = $watcher.WaitForChanged([IO.WatcherChangeTypes]::All, $remaining)
            if ($nextChange.TimedOut) {
                break
            }
            if (Test-RelevantChange -RelativePath $nextChange.Name) {
                $lastRelevantChange = Get-Date
            }
        }

        $pendingBuild = $true
        $nextBuildRetry = [datetime]::MinValue
    }
}
finally {
    $watcher.Dispose()
    Remove-Item -LiteralPath $pidPath -Force -ErrorAction SilentlyContinue
    Write-Log 'Auto-update dihentikan.'
}
