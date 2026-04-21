# Dead Zon

Dead Zon is a standalone Android root utility focused on internal Monet + overlay management, with no runtime dependency on ThemePicker.apk or ThemesStub.apk.

## What v1 includes
- Jetpack Compose + Material 3 AMOLED UI
- Dashboard screen
- Monet control screen with:
  - master Monet switch
  - preset palette picker
  - target overlay toggles (52 mapped targets)
  - search + select all + clear all
  - apply, reset, apply + restart SystemUI
- Settings screen (module notes + planned profile actions)
- Root-based overlay execution using `su` + `cmd overlay`
- Internal registry of Monet overlay mappings extracted from repo references
- Live Dead Zon self-theme sync from selected preset seed color
- DataStore persistence for key Monet state

## Architecture
Code is organized by internal packages:
- `app/` app shell + navigation + DI
- `core/ui/` reusable Compose widgets
- `core/theme/` internal theming + palette sync
- `core/root/` root shell command execution
- `core/overlay/` overlay command builder
- `data/overlay/` overlay registry + repository
- `data/prefs/` DataStore-backed preferences
- `domain/monet/` models + controller
- `feature/dashboard/`
- `feature/monet/`
- `feature/settings/`

## Build and run
1. Open in Android Studio (latest stable with AGP 8.5+).
2. Sync Gradle.
3. Build and run `app` on Android 12+.
4. Grant root access when prompted by your root manager.

## Runtime notes
- If root is missing, Dead Zon reports unavailable root and overlay operations fail gracefully.
- If a mapped overlay package is not installed on the ROM, it is skipped and recorded in logs.
- Overlay assets (`com.paperos.monet.*`) are not bundled in this repo and must exist in ROM or be installed separately.
