plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "dev.piotrulla.timeshop"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.stellardrift.ca/repository/snapshots/")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")

    implementation("eu.okaeri:okaeri-pluralize:1.1.0")

    implementation("org.bstats:bstats-bukkit:3.0.2")

    implementation("xyz.xenondevs.invui:invui:1.34")

    implementation("com.eternalcode:gitcheck:1.0.0")

    compileOnly("me.clip:placeholderapi:2.11.2")

    val okaeriConfigsVersion = "5.0.0-beta.5"
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${okaeriConfigsVersion}")

    implementation("net.kyori:adventure-platform-bukkit:4.3.3")
    implementation("net.kyori:adventure-text-minimessage:4.18.0-SNAPSHOT")

    implementation("com.eternalcode:multification-bukkit:1.1.3")
    implementation("com.eternalcode:multification-okaeri:1.1.3")

    implementation("com.eternalcode:eternalcode-commons-adventure:1.1.3")
}

tasks.test {
    useJUnitPlatform()
}