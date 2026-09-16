# Android Full Game Plan

Build the original Merge & Munch Kotlin/Compose farming-city game around farm/cattle production, merge processing, citizen trade, town upgrades, happiness, and Ancient City exploration.

Put domain state and actions in `GameViewModel`, domain models in `GameModels`, and rendering/navigation in Compose. Add crop/animal readiness, order quantity/patience/rewards, building levels/benefits, tunnel depth, versioned persistence, onboarding, accessibility, and polished dialogs/icon labels.

Android 10/API 29 is the baseline. Use `sans-serif-rounded` or bundled rounded typography, current Material APIs, safe touch targets, responsive layouts, release signing through ignored local files, adaptive icons, Play Store metadata, internal testing, and staged rollout.

Do not copy Farm City or other protected content. Validate with the Gradle wrapper, unit tests, API 29 emulator, and a current API emulator.
