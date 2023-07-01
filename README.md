# General:
Minecraft mod to hug players written in Java by Paula Zhu.

# To run in dev:
- close IntelliJ
- `cd /path/to/mod/folder`
- `./gradlew genIntellijRuns`
- `./gradlew runClient`

# Dev Reqs:
- JDK 17 (must be Java 17)
- IntelliJ IDE
    - education license/email gives Ultimate for free
- Forge 1.19 (not 1.19.+) MDK

# Troubleshooting 230630:

## Java versions (1.19 minecraft is ok at finding this though):
- /usr/libexec/java_home -V
- make default: export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_202`
- java -version

## DOES NOT WORK WITH FORGE 1.20 MDK
