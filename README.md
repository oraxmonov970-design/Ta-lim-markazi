# Ta'lim Markazi — Android ilova

O'qituvchi va o'quvchilar uchun ta'lim ilovasi: kitob, test va video/audio darslari, rol tizimi, progress tracking, offline ishlaydi (Room / SQLite).

## GitHub orqali avtomatik APK yasash (GitHub Actions)

Kompyuteringizda Android Studio bo'lmasa ham, GitHub bepul serverlarida APK yasatib olishingiz mumkin:

### 1-qadam: GitHub'da yangi repository yarating
1. [github.com](https://github.com) saytiga kiring (akkount bo'lmasa — ro'yxatdan o'ting, bepul)
2. Yuqori o'ngdagi **+** tugmasi → **New repository**
3. Repository nomi: masalan `talim-markazi` → **Create repository**

### 2-qadam: Loyihani yuklang
1. Yaratilgan repository sahifasida **"uploading an existing file"** havolasini bosing
2. Ushbu ZIP'dan chiqargan `TalimMarkazi` papkasidagi **barcha fayl va papkalarni** (shu jumladan yashirin `.github` papkasini!) shu yerga tashlang (drag & drop)
   - ⚠️ Muhim: ZIP faylning o'zini emas, ichidagi fayllarni yuklang
   - `.github` papkasi ko'rinmasligi mumkin — kompyuteringizda "yashirin fayllarni ko'rsatish" yoqilganligiga ishonch hosil qiling
3. Pastda **Commit changes** tugmasini bosing

### 3-qadam: Build avtomatik boshlanadi
1. Repository sahifasida yuqoridagi **Actions** bo'limiga o'ting
2. **"Build APK"** ishi (workflow) avtomatik ishga tushganini ko'rasiz (sariq nuqta → yashil belgi, 3-5 daqiqa)
3. Ish tugagach, o'sha ish sahifasi pastida **Artifacts** bo'limida **TalimMarkazi-APK** havolasini bosib yuklab oling (ZIP ichida APK bo'ladi)

### 4-qadam: Telefonga o'rnatish
1. Yuklab olingan ZIP'ni oching, ichidan `app-debug.apk` faylni telefonga yuboring
2. Telefonda faylni oching → "Noma'lum manbalardan o'rnatish"ga ruxsat bering → O'rnatish

---

## Standart login (test uchun)

Ilovani birinchi ochganda **"Ro'yxatdan o'tish"** orqali o'zingiz uchun hisob yarating (O'qituvchi yoki O'quvchi rolini tanlab).

## Texnik ma'lumot

- Til: Java
- Minimal Android versiya: 7.0 (API 24)
- Ma'lumotlar bazasi: Room (SQLite), butunlay offline ishlaydi
- Fayllar (kitob/video/audio) ilova ichki xotirasida saqlanadi
