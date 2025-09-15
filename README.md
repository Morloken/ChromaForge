# ChromaForge

ChromaForge is an Android library project aimed at providing customizable chart components for modern Android applications.  
The goal is to build a flexible, extensible library with support for different chart types and user-friendly customization options.

## Project Structure

- **app/**  
  Sample application module for testing and demonstrating ChromaForge features.

- **chromaforge-core/**  
  Core library module containing the base chart implementation and reusable components.

## Current Status

- Project is in **early development**.  
- Basic project setup is complete:
  - Kotlin DSL build scripts (`build.gradle.kts`)
  - Multi-module architecture (app + core)
  - AndroidX, Material, and testing dependencies

At this stage, the library does not yet provide ready-to-use chart components.  
Development focus is on designing the core structure and preparing for extensibility.

## Roadmap

Planned features include:

- Core chart rendering engine
- Multiple chart types (line, bar, pie, etc.)
- Flexible styling (colors, themes, labels, legends)
- Extension points for custom chart types

## Requirements

- Android Studio (latest stable)
- Gradle 8.x
- Kotlin 2.0.x
- Minimum SDK: 24
- Target SDK: 36

## Contributing

Since the project is at an early stage, contributions are welcome.  
Areas where help is most valuable right now:
- API design discussions
- Chart rendering architecture
- Testing and documentation

## License

TBD (to be decided).
