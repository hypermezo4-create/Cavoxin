# Dead Zon — Codex Master Build Prompt

Use this prompt in Codex to build the app from scratch. The app must be fully self-contained and must not launch ThemePicker or ThemesStub.

## Paste this into Codex

```text
You are building a production-ready Android app from scratch named **Dead Zon**.

## Core Goal
Build **Dead Zon** as a fully self-contained **root-based Monet / RRO overlay controller** for custom ROM users.
The app must provide:
- a premium dark AMOLED UI
- an internal Monet screen
- internal color/preset selection
- target-app toggles
- root-powered overlay enable/disable
- app self-theming using the selected palette
- no dependency on external ThemePicker or ThemesStub UI

## Hard Requirements
1. Do **not** launch or depend on external `ThemePicker` or `ThemesStub` UI.
2. Recreate the full Monet control experience **inside Dead Zon**.
3. The app must work as a **single standalone app** for the user.
4. Overlay packages themselves may exist in ROM or be installed separately, but all control must happen inside Dead Zon.
5. Assume the user has **root** and will grant root access.
6. Use root shell commands to enable/disable overlays directly.
7. Keep the architecture clean and modular so future modules can be added later.
8. First module to implement: **Monet Control**.
9. Build the project from zero, not by wrapping another APK.
10. Use a modern, polished UI with strong hierarchy, premium cards, smooth animations, and AMOLED-friendly styling.

## Product Vision
Dead Zon is a ROM tools hub. The first release focuses on a premium Monet overlay controller. Later releases can add more system tweak modules, but the project must be architected now as a scalable ROM control center.

## Tech Stack
- Kotlin
- Jetpack Compose
- Material 3
- Minimum Android version: Android 12 / API 31
- Target modern Android versions
- Root command execution layer
- Clean architecture with feature modules or clearly separated packages
- ViewModel + StateFlow
- DataStore for preferences
- Hilt for dependency injection

## Project Branding / Design Direction
- App name: Dead Zon
- Style: Tactical premium + futuristic AMOLED
- Base surfaces: near-black / deep charcoal
- Accent behavior: driven by selected Monet palette
- Rounded cards, subtle glow, premium spacing, sharp typography
- Avoid clutter; focus on clarity and high-end control-center feel

## Main Screens
### 1. Splash / entry
- Minimal branded splash
- Fast transition into main shell

### 2. Home screen
- Large status card showing whether Monet is enabled
- Current active palette
- Number of selected target apps
- Quick actions: Apply, Reset, Select All, Clear All
- Entry card for Monet Control
- Placeholder cards for future modules (disabled for now)

### 3. Monet Control screen
Sections:
- Master Monet switch
- Palette selector
- Optional custom color picker
- Apply to Dead Zon app switch
- Searchable target app list
- Select All / Clear All actions
- Apply button
- Reset button
- Optional restart SystemUI button
- Logs / recent actions section

### 4. Palette selector screen or bottom sheet
Provide two modes:
- **Preset palettes** (required for v1)
- **Custom color mode** (optional but preferred if stable)

Preset examples:
- Stock Monet
- Crimson
- Gold
- Blue
- Purple
- Emerald
- Graphite
- AMOLED Mono

Each preset should contain:
- name
- preview colors
- internal theme seed color
- optional overlay group id

### 5. Target apps screen / section
Use checkboxes or switches with icons and labels.
Support search and grouping.
Group examples:
- Core system
- Xiaomi / MIUI apps
- Media apps
- Utilities

## Functional Behavior
### A. Master Monet switch
- If OFF: all selected Monet overlays are disabled
- If ON: selected overlays for enabled targets are applied

### B. Per-target toggles
Each target app toggle maps to one overlay package name.
When Apply is pressed:
- disable overlays that should be off
- enable overlays that should be on
- skip missing overlays but show warning in UI/logs

### C. Apply button
When pressed:
1. Validate root access
2. Build list of selected overlays
3. Disable non-selected overlays from the same controller scope
4. Enable selected overlays
5. Optionally restart affected apps or SystemUI if needed
6. Show result state and logs

### D. Reset button
- Disable all Monet overlays managed by Dead Zon
- Reset selected target toggles to default state
- Reset palette to default preset
- Clear temporary logs if needed

### E. Self-theme Dead Zon
Dead Zon itself must visually follow the selected palette.
Do this with internal Material 3 theming, not external ThemePicker UI.
The app theme must react immediately after palette changes.

## Root / Overlay Engine
Create a dedicated root overlay controller layer.

Suggested classes:
- `RootShell.kt`
- `OverlayCommandBuilder.kt`
- `OverlayRepository.kt`
- `MonetController.kt`
- `MonetOverlayRegistry.kt`
- `MonetApplyUseCase.kt`
- `MonetResetUseCase.kt`

### Required root capabilities
- check root availability
- run shell commands safely
- capture stdout / stderr / exit codes
- return structured result objects
- expose logs to UI

### Overlay command behavior
Use overlay manager shell commands under root.
Support both user 0 and current user handling where needed.
Provide safe wrappers for:
- list overlays
- enable overlay
- disable overlay
- dump overlay state

### Command strategy
Implement a command builder that can produce commands like:
- `cmd overlay list`
- `cmd overlay enable --user 0 <package>`
- `cmd overlay disable --user 0 <package>`
- fallback strategy for `--user current` when required for foreground apps

Also support optional kill/restart commands when needed, such as:
- restart SystemUI
- force-stop target app after overlay change (optional advanced feature)

## Overlay Registry
Create an internal registry seeded with the current PaperOS mapping below.
Store it as a Kotlin object or JSON asset and parse it at startup.

### Registry entries to include now

```

Continue the build prompt with the following:

```text
## Data Model
Create models such as:
- `MonetTarget`
- `MonetPreset`
- `OverlayState`
- `ApplyResult`
- `RootCommandResult`
- `MonetUiState`

Example `MonetTarget` fields:
- preferenceKey
- title
- overlayPackage
- isSelectedByDefault
- category
- iconName or resource id

## Persistence
Use DataStore to persist:
- master monet enabled state
- selected preset
- selected custom color
- apply-to-app flag
- selected targets
- last apply timestamp
- optional last logs

## Default Selection
Default all known targets to enabled in v1, matching legacy behavior.
Allow the user to deselect any target manually.

## UX Details
- Fast, fluid Compose UI
- Animated state changes
- Elegant switch rows
- Search bar for target apps
- Sticky action bar or bottom action container for Apply / Reset
- Rich error states for missing overlay package, no root, failed command, or unavailable target
- Snackbar / dialog feedback plus detailed log panel

## Important Product Rule
Never open or deep-link into ThemePicker or ThemesStub.
Do not use external intents for Monet selection.
All selection and control must happen inside Dead Zon.

## Internal Theme Engine
Implement a Dead Zon internal theme engine.
For v1:
- derive app colors from selected preset seed color
- support dark mode only or dark-first mode
- use Material 3 color scheme generation where practical
- update app colors live when preset changes

## Build Strategy
Generate a full Android Studio project including:
- Gradle files
- manifest
- Kotlin source files
- Compose UI files
- theme files
- icons/placeholders
- strings
- a clean package structure

## Suggested Package Structure
`com.deadzon.app`
- `app/`
- `core/root/`
- `core/ui/`
- `core/theme/`
- `data/overlay/`
- `data/prefs/`
- `domain/monet/`
- `feature/home/`
- `feature/monet/`
- `feature/settings/`

## Required Screens / Components to Implement
- `MainActivity`
- `DeadZonApp`
- `DeadZonTheme`
- `HomeScreen`
- `MonetScreen`
- `PalettePickerSheet`
- `TargetAppsList`
- `ApplyBottomBar`
- `LogsCard`
- `RootStatusCard`

## Safety / Reliability
- Never crash if an overlay package is missing
- Show unavailable targets as disabled or warning state
- Validate root before apply
- Keep overlay operations off the main thread
- Expose progress loading state
- Make Apply idempotent where possible

## v1 Acceptance Criteria
1. App launches cleanly
2. User can select a preset palette internally
3. User can enable/disable individual Monet targets
4. User can apply changes using root without external apps
5. User can reset all managed overlays
6. User can theme Dead Zon itself from the same palette
7. No ThemePicker or ThemesStub launch anywhere
8. Clean Compose UI with polished styling
9. Clear logs for command success/failure
10. Project is easy to extend with future modules

## Deliverables
Output the full project source code.
Provide all necessary files.
Do not output only snippets.
Generate a complete, buildable Android Studio project.

## Implementation Note
If custom full Monet generation is too complex for first pass, implement preset-based palettes first, but keep the architecture ready for a later custom-color engine.
```

## Notes for you

- This prompt is intentionally strict so Codex builds a standalone app instead of wrapping old APKs.
- The overlay registry below was extracted from the provided smali and should be used as the initial target map.

## Initial overlay registry

```json
[]
```
