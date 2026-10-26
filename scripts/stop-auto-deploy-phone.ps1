$stateFolder = Join-Path $env:LOCALAPPDATA 'SmartWaste\AutoDeploy'
$pidPath = Join-Path $stateFolder 'auto-deploy.pid'

if (-not (Test-Path -LiteralPath $pidPath)) {
    Write-Output 'Auto-update tidak sedang berjalan.'
    exit 0
}

$watcherPid = 0
if (-not [int]::TryParse((Get-Content -LiteralPath $pidPath -Raw).Trim(), [ref]$watcherPid)) {
    Remove-Item -LiteralPath $pidPath -Force -ErrorAction SilentlyContinue
    Write-Output 'File status lama dibersihkan; proses watcher tidak ditemukan.'
    exit 0
}

$process = Get-CimInstance Win32_Process -Filter "ProcessId = $watcherPid" -ErrorAction SilentlyContinue
if ($process -and $process.CommandLine -like '*auto-deploy-phone.ps1*') {
    Stop-Process -Id $watcherPid -Force
    Write-Output 'Auto-update dihentikan.'
} else {
    Remove-Item -LiteralPath $pidPath -Force -ErrorAction SilentlyContinue
    Write-Output 'Proses watcher sudah tidak aktif.'
}
