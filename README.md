# Smart Waste — Android Admin & Petugas

Aplikasi Android Kotlin/Jetpack Compose untuk Admin dan Petugas. Website user dan PHP REST API berada di folder `Waste 2`; keduanya berbagi MySQL XAMPP yang sama.

## Peta kode aplikasi

```text
app/src/main/java/com/uoi/pratikumwithbunafla/
├── MainActivity.kt             # Entry point, navigasi, role gate, orkestrasi data
├── data/
│   ├── model/Models.kt          # Model akun, laporan, dashboard, armada, statistik
│   └── remote/SmartWasteApi.kt  # Satu client untuk seluruh endpoint PHP
├── domain/WasteFlow.kt         # Aturan status laporan yang dipakai bersama
└── ui/
    ├── admin/AdminScreens.kt   # Dashboard, laporan, admin, operasional, petugas
    ├── auth/LoginScreen.kt     # Login username/password dan pemilihan role
    ├── components/UiComponents.kt
    └── theme/                  # Warna, tipografi, Material theme
```

- `app/src/test/` — unit test aturan status.
- `docs/design/` — referensi desain/QA. Berkas konfigurasi LAN dan dokumen sekolah tetap lokal dan tidak disertakan di repo publik.
- `gradle/libs.versions.toml` — katalog versi plugin dan dependency.

## Menjalankan dan mengetes

1. Nyalakan Apache dan MySQL di XAMPP.
2. Buka folder ini di Android Studio atau jalankan `OPEN-AndroidStudio-SmartWaste.bat`.
3. Pilih konfigurasi `app`, lalu jalankan pada emulator. Emulator mengakses API laptop melalui `http://10.0.2.2:8080/Waste2/backend-php/index.php`.
4. Untuk HP fisik, jalankan `scripts/auto-deploy-phone.ps1` saat laptop dan HP tersambung ke Wi-Fi yang sama. Skrip memilih IP Wi-Fi laptop, membangun APK dengan URL REST API LAN, menerbitkan APK dan manifest ke folder web `Waste 2/updates`, lalu memasang APK ke satu HP yang tersambung USB. Apache port `8080` harus dapat dijangkau dari Wi-Fi; jangan membuka port MySQL `3306` ke perangkat lain.
5. APK yang sudah memiliki pemeriksa pembaruan mengecek server saat dibuka dan berkala saat aplikasi aktif. Pengguna menekan tombol pembaruan, lalu Android tetap meminta konfirmasi pemasangan. Semua HP lain perlu memasang APK LAN ini sekali terlebih dahulu; versi lama belum memiliki pemeriksa pembaruan. Untuk kestabilan, atur DHCP reservation agar IP laptop tidak berubah.
6. Validasi dengan:

   ```powershell
   .\gradlew.bat :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
   ```

## Konfigurasi akun dan server

- Admin dan Petugas masuk memakai username dan kata sandi yang dibuat admin pertama. Website tetap memakai login user dengan username/email dan kata sandi.
- PHP membaca `SMARTWASTE_FIRST_ADMIN_EMAIL` untuk alamat email profil admin pertama, serta `SMARTWASTE_DB_HOST`, `SMARTWASTE_DB_PORT`, `SMARTWASTE_DB_NAME`, `SMARTWASTE_DB_USER`, dan `SMARTWASTE_DB_PASSWORD`.
- Nilai default database cocok untuk XAMPP lokal: `127.0.0.1:3306`, database `smart_waste`, user `root`, password kosong.
- Detail API, role, dan alur perubahan ada di [docs/architecture.md](docs/architecture.md) dan README project web `Waste 2`.
