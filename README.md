# General:
Minecraft mod to hug players and animals and other entities written in Java with Forge Gradle by Paula Zhu.
- note that animations are built client-side
- https://www.curseforge.com/minecraft/mc-mods/hug-mod
- MOD DEPENDENCY: https://modrinth.com/mod/playeranimator/version/1.0.2-rc1+1.20-forge

# Use
- jar file in build/libs/
- As default, press "O" key to use; custom key bind can be set in options
- Must be standing right in front of another entity to use
- To hug shorter animals, try using sneak and looking down at them
- Hugging increases the health of the player actively doing the hugging by 1/2 of a heart
- Being hugged increases a player's health by 1 heart

- Hugging a villager will also increase the villager's health by 1 heart
- yay

# To run in dev:
- don't need to close IntelliJ
- `cd ./HugMod`
- `./gradlew genIntellijRuns`
    - if doesn't work may need to rerun the build.gradle
- `./gradlew runClient`
- `./gradlew build`
    - jar file in build/libs/

# Currently working with (231001):
- minecraft 1.20
- forge 46.0.12
    - https://nekoyue.github.io/ForgeJavaDocs-NG/
- Java 17.0.7
- gradle 7.4.2
    - `./gradlew --version`
- JVM 21 (JetBrains s.r.o. 21+9-b126.4)
- OS: Mac OS X 12.3 aarch64

# Reqs:

## Mod dependencies:
- Forge 1.20
- KosmX/minecraftPlayerAnimator library!
    - is included on build
    - implementation fg.deobf("dev.kosmx.player-anim:player-animation-lib-forge:0.4.0")

## Dev Environment Reqs:
- JDK 17 (must be Java 17)
- IntelliJ IDE
    - education license/email gives Ultimate for free
- Forge 1.20 46.0.12 MDK

# Troubleshooting 230630:

## Java versions (1.19.x minecraft is ok at finding this though):
- /usr/libexec/java_home -V
- make default: export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_202`
- java -version

## UPDATING TO FORGE 1.20 (231001)
- change mappings to 1.20
- dependencies:
    - change forge
    - https://maven.kosmx.dev/dev/kosmx/player-anim/
    - mods.toml versionRange
