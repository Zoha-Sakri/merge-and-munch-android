# Android Full Game Plan

## Product Loop
Harvest crop plots and care for cattle -> move goods to the market board -> merge equal-stage ingredients -> serve citizen orders -> earn coins/stars/happiness -> upgrade community buildings, restaurants, decorations, and wonders -> explore original Ancient City tunnels.

## Ownership
`app/src/main/java/com/mergeandmunch/market/GameViewModel.kt` owns state and actions; `MainActivity.kt` and focused Compose screens render it; `GameModels.kt` owns domain data. Android 10/API 29 is the minimum; use current compile/target SDK and the Gradle wrapper.

## Compose Standards
Use rounded native typography, Material accessibility semantics, labeled icons, touch-friendly controls, responsive small-screen layouts, dialogs with clear primary/secondary actions, loading/error/empty states, reduced motion, and state restoration. Add DataStore or another versioned persistence layer before release; keep secrets out of Git.

## Release Plan
Add adaptive launcher icons, splash/loading state, release signing configuration via local secrets, R8/proguard review, Play Integrity decisions, privacy/age-rating content, internal testing, screenshots, store listing, and staged Play Store rollout.

## Originality
Do not copy Farm City or any other game's protected identity, art, writing, characters, maps, UI, catalog, or exact progression. Keep all Merge & Munch content original.

## Validation
`export ANDROID_HOME="$HOME/Library/Android/sdk"; ./gradlew -p android assembleDebug`; run on Android 10/API 29 and a current API emulator; add unit tests for merge, economy, persistence, and unlocks.
