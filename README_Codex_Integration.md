# Dead Zon Monet Module — Codex Integration Pack

## Goal
Port the existing **PaperOS/NextOS Monet overlay controller** into the new **Dead Zon** app as the **first core module**.

This pack is built from the files you provided:
- `ThemePicker.apk`
- `ThemesStub.apk`
- `smali.zip`
- the Monet XML snippet you pasted in chat

## What the reverse-engineered logic does
The provided smali contains a working **Monet Overlay Controller**.

### Main controller classes found
- `com/android/internal/util/paperos/xml/paperosMonet.smali`
- `com/paperos/settings/PaperOSGesture.smali`

Both classes implement the same behavior:
1. Bind to the Android overlay service using `ServiceManager.getService("overlay")`
2. Obtain `IOverlayManager`
3. Load a map of preference keys -> overlay package names
4. Listen to a **global Monet switch**
5. Listen to **individual per-app switches**
6. Enable/disable overlays with `IOverlayManager.setEnabled(packageName, enabled, userId)`

### Important implementation detail
The module **does not generate Monet colors itself**.
Instead, it relies on Android's **Theme/Wallpaper picker** for color selection, then it toggles overlay packages for selected apps.

## APK findings
### `ThemePicker.apk`
This is a **full Theme/Wallpaper picker app**, not a tiny helper package.
Evidence from the APK contents:
- multiple DEX files (`classes.dex` through `classes10.dex`)
- strings for:
  - `com.android.wallpaper`
  - `CustomizationPickerActivity`
  - `com.android.customization.*`
  - `Monet`

So this APK is the **color chooser side** of the flow.

### `ThemesStub.apk`
This is **not a full app controller**.
It contains:
- `AndroidManifest.xml`
- `resources.arsc`
- **no classes.dex**

That makes it closer to a **stub/resource companion package**, not the logic engine.

## Intended Dead Zon flow
### User flow
1. Open **Dead Zon**
2. Enter **Monet Control**
3. Turn on **Enable Monet Effect**
4. Press **Choose Monet Color**
5. Launch Android's picker (`com.android.wallpaper.picker.CustomizationPickerActivity`)
6. User picks a color/palette
7. Back in Dead Zon, user selects target apps
8. Dead Zon enables/disables overlay packages per target app

### What applies where
- **System-wide look** comes from the overlay packages you already have or will provide
- **Dead Zon app theme** should be handled inside Dead Zon itself using its own theme engine or dynamic color

## XML behavior from your snippet
Your XML defines:
- one master switch:
  - `paperos_switch_monet_color`
- one color-picker preference:
  - opens `com.android.wallpaper.picker.CustomizationPickerActivity`
- one dependent category:
  - `Control Apps`
- many checkbox toggles for individual app overlays

This is exactly the right structure for the first Dead Zon module.

## Overlay map extracted from smali
There are **52 overlay targets** mapped in the provided smali.
The full machine-readable mapping is in `monet_overlay_map.json`.

## Full preference key -> overlay package list
- `paperos_monet_androidbluetooth` -> `com.paperos.monet.androidbluetooth`
- `paperos_monet_aod` -> `com.paperos.monet.aod`
- `paperos_monet_backup` -> `com.paperos.monet.backup`
- `paperos_monet_bluetooth` -> `com.paperos.monet.bluetooth`
- `paperos_monet_bugreport` -> `com.paperos.monet.bugreport`
- `paperos_monet_calendar` -> `com.paperos.monet.calendar`
- `paperos_monet_cloudservice` -> `com.paperos.monet.cloudservice`
- `paperos_monet_camera` -> `com.paperos.monet.camera`
- `paperos_monet_cleanmaster` -> `com.paperos.monet.cleanmaster`
- `paperos_monet_cloudbackup` -> `com.paperos.monet.cloudbackup`
- `paperos_monet_contacts` -> `com.paperos.monet.contacts`
- `paperos_monet_calculator` -> `com.paperos.monet.calculator`
- `paperos_monet_deskclock` -> `com.paperos.monet.deskclock`
- `paperos_monet_downloads` -> `com.paperos.monet.downloads`
- `paperos_monet_extraphoto` -> `com.paperos.monet.extraphoto`
- `paperos_monet_finddevice` -> `com.paperos.monet.finddevice`
- `paperos_monet_fileexplorer` -> `com.paperos.monet.fileexplorer`
- `paperos_monet_freeform` -> `com.paperos.monet.freeform`
- `paperos_monet_gallery` -> `com.paperos.monet.gallery`
- `paperos_monet_home` -> `com.paperos.monet.home`
- `paperos_monet_incallui` -> `com.paperos.monet.incallui`
- `paperos_monet_miinput` -> `com.paperos.monet.miinput`
- `paperos_monet_misound` -> `com.paperos.monet.misound`
- `paperos_monet_milink_service` -> `com.paperos.monet.milink_service`
- `paperos_monet_mirror` -> `com.paperos.monet.mirror`
- `paperos_monet_mishare` -> `com.paperos.monet.mishare`
- `paperos_monet_mediaeditor` -> `com.paperos.monet.mediaeditor`
- `paperos_monet_miservice` -> `com.paperos.monet.miservice`
- `paperos_monet_misettings` -> `com.paperos.monet.misettings`
- `paperos_monet_mms` -> `com.paperos.monet.mms`
- `paperos_monet_notes` -> `com.paperos.monet.notes`
- `paperos_monet_notification` -> `com.paperos.monet.notification`
- `paperos_monet_personalassistant` -> `com.paperos.monet.personalassistant`
- `paperos_monet_packageinstaller` -> `com.paperos.monet.packageinstaller`
- `paperos_monet_powerkeeper` -> `com.paperos.monet.powerkeeper`
- `paperos_monet_permission` -> `com.paperos.monet.permission`
- `paperos_monet_phone` -> `com.paperos.monet.phone`
- `paperos_monet_settings` -> `com.paperos.monet.settings`
- `paperos_monet_screenrecorder` -> `com.paperos.monet.screenrecorder`
- `paperos_monet_server_telecom` -> `com.paperos.monet.server_telecom`
- `paperos_monet_screenshot` -> `com.paperos.monet.screenshot`
- `paperos_monet_securitycenter` -> `com.paperos.monet.securitycenter`
- `paperos_monet_scanner` -> `com.paperos.monet.scanner`
- `paperos_monet_securitycore` -> `com.paperos.monet.securitycore`
- `paperos_monet_systemui` -> `com.paperos.monet.systemui`
- `paperos_monet_systemuiplugin` -> `com.paperos.monet.systemuiplugin`
- `paperos_monet_soundrecorder` -> `com.paperos.monet.soundrecorder`
- `paperos_monet_touchassistant` -> `com.paperos.monet.touchassistant`
- `paperos_monet_thememanager` -> `com.paperos.monet.thememanager`
- `paperos_monet_updater` -> `com.paperos.monet.updater`
- `paperos_monet_weather2` -> `com.paperos.monet.weather2`
- `paperos_monet_xiaomiaccount` -> `com.paperos.monet.xiaomiaccount`

## Recommended Dead Zon module name
Use one of these:
- **Monet Control**
- **Monet Engine**
- **Color & Overlay**

Best choice for now: **Monet Control**

## Recommended UI inside Dead Zon
### Section 1 — Main controls
- Enable Monet Effect
- Choose Monet Color
- Theme Dead Zon too (optional)

### Section 2 — Target apps
- Select All
- Clear All
- the app checkboxes from the original module

### Section 3 — Actions
- Apply
- Reset to Stock
- Refresh overlay state

## Architecture recommendation
### Preferred
Build a dedicated **Dead Zon Monet module** and re-implement the logic cleanly in Kotlin/Java.

Why this is better than transplanting raw smali:
- cleaner codebase
- easier future expansion
- better styling
- easier support for Dead Zon branding
- easier to add features later like profiles/presets

### Technical requirement
The original logic talks directly to `IOverlayManager`.
That means Dead Zon will need one of these runtime models:
1. **system/priv-app** integration inside the ROM
2. **root** execution fallback
3. **Shizuku** bridge fallback

If Dead Zon is only a normal user app with no elevated privilege, this exact logic will not be enough by itself.

## What Codex should build
### Minimum implementation
1. `MonetFragment` / `MonetActivity`
2. `OverlayController` wrapper
3. `MonetRepository` or simple state storage
4. `MonetOverlayMap` constant object using the JSON mapping
5. UI with master switch + picker + per-app toggles
6. launch intent to `CustomizationPickerActivity`
7. enable/disable overlays via elevated path

### Nice upgrades
- search in target app list
- Select All / Clear All buttons
- groups: System / Xiaomi Apps / Media / Communication / Tools
- import/export overlay profile
- live current-state badges

## Files included in this pack
- `README_Codex_Integration.md` — this document
- `monet_overlay_map.json` — extracted preference/overlay map
- `DeadZonMonetController.kt` — Kotlin skeleton for Codex to expand
- `paperos_monet_fragment_snippet.xml` — XML snippet reconstructed from your chat message
- `source_smali/` — raw smali provided in `smali.zip`

## Missing dependencies still required for full 1:1 behavior
These are still needed if you want **perfect original behavior**:
1. **all actual overlay APK packages** (`com.paperos.monet.*` packages)
2. any ROM-side install location for those overlays
3. final privilege model: system app / priv-app / root / Shizuku
4. any original drawable/styles used by the PaperOS screen if you want the exact same look

Without the overlay APKs themselves, Dead Zon can still build the UI and controller, but there will be nothing real to enable.

## Best practical plan
### Phase 1
Build Dead Zon's **Monet Control** module UI and controller.

### Phase 2
Connect your existing `com.paperos.monet.*` overlay packages.

### Phase 3
Style it in Dead Zon's premium UI.

## Clean Codex prompt
Use this prompt with Codex:

> Build a new Android module inside my app Dead Zon called Monet Control. Use the provided overlay mapping JSON. The screen must have a master Monet switch, a button that launches `com.android.wallpaper.picker.CustomizationPickerActivity`, and a long list of per-app toggles. Each toggle controls an overlay package through an OverlayController abstraction. Support a privileged execution path for system/priv-app or a fallback abstraction for root/Shizuku. Add Select All, Clear All, Apply, and Reset actions. Keep the UI premium AMOLED with clean spacing and reusable components. Do not copy raw smali directly unless necessary; rewrite it cleanly in Kotlin.

## Final recommendation
Do **not** embed the entire ThemePicker APK inside Dead Zon.
Instead:
- use ThemePicker only as the **color picker entry point**
- use Dead Zon as the **overlay controller and UX layer**
- keep your overlay APKs as the actual system color packs
