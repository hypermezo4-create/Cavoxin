## Gradle wrapper directory

Keep the following files in this folder for CI builds:

- `gradle-wrapper.properties` (tracked)
- `gradle-wrapper.jar` (manually added before push)

GitHub Actions workflow `.github/workflows/android-debug.yml` uses `./gradlew assembleDebug` and requires the wrapper JAR to exist in this directory.
