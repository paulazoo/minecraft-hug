# General:
Minecraft mod to hug players written in Java with Forge Gradle by Paula Zhu.
- note that animations are built client-side

Press keySwapOffHand empty handed while standing right in front of another player to use
Hugging increases the hugged player's health by 1 heart

# To run in dev:
- close IntelliJ
- `cd /path/to/mod/folder`
- `./gradlew genIntellijRuns`
- `./gradlew runClient`
- `./gradlew build`
    - jar file in build/libs/

# Currently working with (230701):
- minecraft 1.19.3
- forge 44.1.0
    - https://nekoyue.github.io/ForgeJavaDocs-NG/
- Java 17.0.7
- gradle 7.4.2
    - `./gradlew --version`
- JVM 21 (JetBrains s.r.o. 21+9-b126.4)
- OS: Mac OS X 12.3 aarch64

# Reqs:

## Mod dependencies:
- Forge 1.19.3
- KosmX/minecraftPlayerAnimator library!
    - is included on build
    - implementation fg.deobf("dev.kosmx.player-anim:player-animation-lib-forge:0.4.0")

## Dev Environment Reqs:
- JDK 17 (must be Java 17)
- IntelliJ IDE
    - education license/email gives Ultimate for free
- Forge 1.19.3 MDK

# Troubleshooting 230630:

## Java versions (1.19.x minecraft is ok at finding this though):
- /usr/libexec/java_home -V
- make default: export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_202`
- java -version

## DOES NOT WORK WITH FORGE 1.20
