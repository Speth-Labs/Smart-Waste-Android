# Arsitektur aplikasi Android

```text
MainActivity (nav + role gate)
       ↓ aksi / data
UI Compose (auth, admin, petugas, components)
       ↓ request
SmartWasteApi (HTTP + JSON)
       ↓ REST + Bearer token
PHP API → MySQL bersama website user
```

## Batas tanggung jawab

- `data/model`: bentuk data yang dipakai aplikasi; bukan tempat untuk tampilan atau request HTTP.
- `domain/WasteFlow`: aturan transisi/label status yang sama untuk Admin dan Petugas.
- `data/remote`: satu pintu komunikasi Android ke endpoint PHP. Jangan membuat URL API duplikat di composable.
- `ui/auth`, `ui/admin`, `ui/components`, `ui/theme`: login, fitur per role, komponen yang bisa dipakai ulang, dan design tokens.
- `MainActivity`: entry point, navigasi, serta orkestrasi state dan aksi role. Pemeriksaan role di server tetap otoritatif.

Untuk fitur baru, tambahkan model/API/domain rule pada lapisan yang sesuai, lalu UI hanya memanggil callback/data yang diberikan. Jika state layar makin rumit, ekstrak orkestrasi layar ke ViewModel/repository dalam perubahan tersendiri; jangan menambah dependency injection atau framework tanpa kebutuhan nyata.

## Aturan konsistensi

- Status backend dan `WasteFlow` harus sama: `menunggu_verifikasi`, `diproses`, `ditugaskan`, `diangkut`, `selesai`, `ditolak`.
- Semua endpoint staf memakai token, dan Admin/Petugas hanya memperoleh data sesuai role dari PHP.
- Perubahan endpoint harus memperbarui `SmartWasteApi.kt`, website `frontend/js/app.js`, dokumentasi kontrak API, serta pengujian terkait.
- Gunakan warna dan font dari `ui/theme`; jangan mendefinisikan ulang token warna yang sama di screen baru.
