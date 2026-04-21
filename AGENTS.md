# AGENTS.md

## Dead Zon Project Rules

Dead Zon must be built as a fully self-contained Android app inside this repository.

### Product goal
Build a premium AMOLED Android customization app called **Dead Zon** that recreates the Monet / Overlay control flow completely inside the app itself.

### Hard rules
- Do **not** depend on `ThemePicker.apk` or `ThemesStub.apk` at runtime.
- Do **not** launch any external APK, activity, or intent for Monet or theme selection.
- Use any old APK logic only as technical reference, never as runtime dependency.
- Keep the final result as **one standalone app only**.

## Required implementation

### Stack
- Kotlin
- Jetpack Compose
- Material 3

### Architecture
Prefer a clean modular structure such as:
- `app/`
- `core/ui/`
- `core/theme/`
- `core/root/`
- `core/overlay/`
- `feature/dashboard/`
- `feature/monet/`
- `feature/settings/`

### v1 screens
Implement these first:
- Dashboard
- Monet screen
- Preset or color picker
- Target app selection list
- Settings screen if needed

### v1 behavior
The app must include:
- master Monet switch
- internal preset and/or color picker
- target app toggles
- select all / clear all
- apply / reset
- root-based overlay enable/disable logic
- optional restart SystemUI flow when needed
- Dead Zon self-theme sync using the selected palette

## Overlay behavior
- Maintain an internal overlay registry that maps UI keys to overlay package names.
- Read supported target mappings from the repository references when available.
- Disable conflicting overlays before enabling the selected ones.
- Keep overlay application logic inside Dead Zon.
- Assume overlays may already exist in the ROM or may be provided separately later.

## Runtime expectations
- Advanced features are expected to use root access.
- The app should fail gracefully if root is missing.
- Show clear UI state for apply, reset, and error cases.
- Do not hardcode dependence on unavailable overlay assets.

## UI direction
Dead Zon should feel:
- dark AMOLED
- premium
- futuristic
- sharp and polished

Preferred style:
- black base
- tactical red accents or luxury highlight styling
- strong hierarchy
- smooth motion
- premium cards, toggles, and sections

## Working style
Before writing major code:
1. Inspect the repository files first.
2. Read:
   - `README.md`
   - `DEAD_ZON_CODEX_MASTER_PROMPT.md`
   - any reference docs or packs in the repo
3. Produce a short implementation plan.
4. Then build v1 directly in this repository.

## Output expectations
At the end of each major task, summarize:
1. what was created
2. what files were changed
3. what still needs overlay assets or exact package names
4. how to build and run the project

## Safety and quality
- Keep changes scoped and practical.
- Avoid fake or placeholder integrations unless clearly marked.
- Prefer a working internal implementation over patchwork reuse.
- If something is missing from the repo, state it clearly and continue with the best standalone structure possible.
