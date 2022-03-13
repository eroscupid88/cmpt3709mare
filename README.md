# CMPT370-9MARE

Software Engineering Project for CMPT370 Winter 2022


## How to build: Run command
./gradlew clean build
## How to Test: Run command

# Unit Test
./gradlew app:test
# Debug test
./gradlew -Pci --console=plain :app:testDebug

# Lint Debug
./gradlew -Pci --console=plain :app:lintDebug -PbuildDir=lint
# Instrumental test
./gradlew connectedAndroidTest


