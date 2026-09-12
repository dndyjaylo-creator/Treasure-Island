# Treasure Island: Lost Map — Stage 1 Prototype

A 2D top-down adventure/puzzle game built in **Java with libGDX**, targeting Android
(and desktop, for fast testing) from one shared codebase — this fulfills the Java
Game Development Project brief and runs on a phone or emulator.

This build implements **Stage 1: The Forgotten Beach** end-to-end and is structured
so Stages 2–5 (Jungle Path, Ancient Ruins, Hidden Cave, Treasure Temple) can be
added as new `Screen` classes reusing the same `Player`, `Collectible`, and `Gate`
building blocks.

## What's playable right now
- Free top-down movement around the beach (keyboard on desktop, on-screen joystick
  on Android/touch).
- Collect 2 map fragments and a key on the accessible side of the island.
- Use the key to roll aside the boulder blocking the rock-wall corridor.
- Grab the 3rd map fragment on the far side, then reach the exit flag to
  complete the stage.
- All graphics are drawn procedurally (no external art files needed to run),
  so there's nothing to break by a missing asset — swap in real sprites later
  by loading Textures from `android/assets` instead of `GameAssets.java`.

## Project layout
```
core/    — shared game logic (screens, entities, assets) — platform-independent
desktop/ — thin launcher to run/test on your PC (fastest way to iterate)
android/ — thin launcher + manifest to build an installable APK
```

## Running it
I can't compile or launch an Android build inside this sandbox (no Android SDK
or Gradle network access here), so please build it yourself in Android Studio —
it only takes a few minutes:

1. Install [Android Studio](https://developer.android.com/studio) if you don't
   have it.
2. **File → Open**, select the `TreasureIslandLostMap` folder.
3. Let Gradle sync (first sync downloads libGDX + Android build tools — needs
   internet).
4. **Fastest way to test:** pick the `desktop` run configuration (or run
   `DesktopLauncher.main`) — it opens a resizable window on your PC in a few
   seconds, using the exact same game code as Android.
5. **To test on Android:** connect a phone (USB debugging on) or start an
   emulator, select the `android` run configuration, and press Run. It installs
   and launches automatically.

If Android Studio complains about the Gradle wrapper jar being missing, click
"Try Again" / use **File → Sync Project with Gradle Files** — Android Studio
will regenerate the wrapper automatically since it has real internet access.

## Getting an installable APK without Android Studio (via GitHub)
This project includes a ready-made GitHub Actions workflow
(`.github/workflows/build-android.yml`) that builds the APK in the cloud —
useful if you don't have Android Studio installed.

1. **Create a free GitHub account** at github.com if you don't have one.
2. **Create a new repository** (github.com → the `+` icon top-right → "New
   repository"). Any name, e.g. `treasure-island-lost-map`. Leave it empty
   (no README/gitignore) and click Create.
3. **Upload this project** to it. Easiest way with no command line: on the new
   repo's page click "uploading an existing file", then drag in every file and
   folder from this unzipped project (including the hidden `.github` folder —
   if your file browser hides it, show hidden files first). Commit the upload.
4. **Run the build**: on your repo's page go to the **Actions** tab → click
   the "Build Android APK" workflow → **Run workflow** button → Run.
5. Wait a minute or two for the green checkmark, then open that workflow run
   and scroll down to **Artifacts** → download `TreasureIslandLostMap-debug-apk`.
   It downloads as a `.zip` — unzip it to get the actual `.apk` file.
6. **Transfer the `.apk` to your phone** (email it to yourself, upload to
   Google Drive, or use a USB cable) and tap it on your phone to install.
   Android will ask to allow "install from unknown sources" the first time —
   that's expected for any app installed outside the Play Store.

## Controls
- **Desktop:** Arrow keys or WASD to move.
- **Android:** Drag on the on-screen joystick (bottom-left) to move.

## Extending to the next stages
Each stage is one `Screen` class (see `PlayScreen.java` for the pattern):
world layout, a list of `Collectible`s, optional `Gate`s/walls, and a win
condition that calls `game.setScreen(new NextStageScreen(game))`. To add
Stage 2 (Jungle Path), copy `PlayScreen.java` as a starting point, change the
layout/puzzle, and chain it from `StageCompleteScreen`.

## Team
Sajot, Dandy Jaylo · Tion, Jaynette Magna · Usaraga, Ava Daphne
