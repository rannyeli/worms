# Worms

A turn-based artillery strategy game inspired by the classic Worms series, built in Java with Swing.

Choose your team (USA, Israel, or Russia), deploy your worms across procedurally selected terrain maps, and battle against an AI opponent. Take turns moving, aiming, and launching grenades to eliminate the enemy team.

![Gameplay](docs/gameplay.gif)

## Features

- **3 unique maps** -- grass, hot desert, and cold tundra -- each with distinct terrain and background music
- **3 selectable teams** with themed worm names
- **AI opponent** with Dijkstra-based pathfinding that navigates terrain and calculates aim
- **Turn-based combat** with a 30-second timer per turn
- **Scrollable maps** wider than the screen, navigable via mouse drag

## Prerequisites

- **Java 17+** (tested with OpenJDK 22)

## Build & Run

```bash
# Build the project
./gradlew build

# Run the game
./gradlew run
```

## Package for Distribution

Create a native installer for your platform (no JDK required for end users):

```bash
./gradlew jpackage
```

This produces:

| Platform | Output |
|----------|--------|
| macOS | `.dmg` installer |
| Windows | `.msi` installer |
| Linux | `.deb` package |

The packaged app bundles a minimal JVM runtime, so users can install and play without any Java setup.

## Controls

| Key | Action |
|-----|--------|
| Arrow Left / Right | Move worm |
| Space | Toggle aim / Fire grenade |
| Arrow Up / Down | Adjust aim angle (while aiming) |
| Enter | Jump |
| Ctrl | Skip turn |
| Mouse Drag | Scroll the map |

## Project Structure

```
src/main/
  java/com/worms/      # Game source code
  resources/
    Images/             # Sprites, backgrounds, UI elements
    sounds/             # Music and sound effects (WAV)
    XmlFiles/           # Terrain map definitions (XML)
```

## Author

Ran Elishayev
