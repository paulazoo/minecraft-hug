# General:
Minecraft mod to hug players written in Java with Forge Gradle by Paula Zhu.
- note that animations are built client-side

# To run in dev:
- close IntelliJ
- `cd /path/to/mod/folder`
- `./gradlew genIntellijRuns`
- `./gradlew runClient`

# Reqs:

## Mod dependencies:
- Forge duh.
- KosmX/minecraftPlayerAnimator library!
    - is included on build
    - implementation fg.deobf("dev.kosmx.player-anim:player-animation-lib-forge:0.4.0")

## Dev Environment Reqs:
- JDK 17 (must be Java 17)
- IntelliJ IDE
    - education license/email gives Ultimate for free
- Forge 1.19 (not 1.19.+) MDK

# Troubleshooting 230630:

## Java versions (1.19 minecraft is ok at finding this though):
- /usr/libexec/java_home -V
- make default: export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_202`
- java -version

## DOES NOT WORK WITH FORGE 1.20
