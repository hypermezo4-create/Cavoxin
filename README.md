# Dead Zon

Dead Zon is a standalone Android customization app focused on ROM-level Monet and overlay control.

## Goal
Build Dead Zon from scratch as a fully self-contained Android app that:
- does **not** depend on ThemePicker.apk or ThemesStub.apk at runtime
- does **not** launch any external APK, activity, or intent for theme selection
- includes its own internal Monet UI, preset picker, target toggles, and root-based overlay controller
- can apply selected palettes to both supported system targets and the Dead Zon app itself

## Core Product Direction
Dead Zon is designed as a premium AMOLED control center for ROM customization.

### First main module
**Monet / Overlay Control**

Features:
- master Monet switch
- internal color or preset picker
- target app toggles
- select all / clear all
- apply / reset
- optional restart of SystemUI when needed
- internal Dead Zon theming using the selected palette

## Runtime Rules
The final app must be a **single standalone app**.

### Required behavior
- all control happens inside Dead Zon
- overlay enable/disable logic runs through root
- old ThemePicker / ThemesStub behavior is used only as technical reference
- no external UI dependency is allowed at runtime

### Allowed assumptions
- overlay packages may exist in the ROM or be installed separately
- users are expected to have root access for advanced features

## Technical Direction
Preferred stack:
- Kotlin
- Jetpack Compose
- Material 3
- clean modular architecture

Suggested internal structure:
- `app/`
- `core/ui/`
- `core/theme/`
- `core/root/`
- `core/overlay/`
- `feature/monet/`
- `feature/dashboard/`
- `feature/settings/`

## Monet Controller Responsibilities
The app should maintain an internal overlay registry mapping UI keys to overlay package names.

Examples:
- Settings
- SystemUI
- Home
- Gallery
- Camera
- Security Center
- other supported target packages

The controller should:
1. read the selected palette or preset
2. resolve selected target apps
3. disable conflicting overlays
4. enable requested overlays through root
5. refresh affected targets if needed

## UI Direction
Dead Zon should feel:
- dark AMOLED
- premium
- futuristic
- clean but aggressive

Visual preference:
- black base
- tactical red or luxury accent styling
- strong hierarchy
- polished cards and toggles
- smooth animations

## Repository Notes
This repository is intended to become the primary source for the Dead Zon app.

Reference materials such as old APK analysis, overlay maps, and controller logic can be used during development, but the final product must remain independent from those APKs at runtime.

## Current Build Objective
Build **v1** with:
- Dashboard
- Monet screen
- Preset picker
- Target app list
- Root overlay apply/reset flow
- Dead Zon self-theme sync

## Non-Goals for v1
- launching external theme picker apps
- depending on external APK UI flows
- shipping a half-integrated prototype

## Development Instruction
If there is any ambiguity, prefer:
- standalone implementation
- internal UI recreation
- root-based overlay execution
- clean architecture over patchwork reuse
