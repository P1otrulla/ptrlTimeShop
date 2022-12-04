import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "cc.dreamcode"
version = "1.0.0"
val mainPackage = "cc.dreamcode.timeshop"

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()

    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url = uri("https://repository.minecodes.pl/releases") }
    maven { url = uri("https://repository.minecodes.pl/snapshots") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.dreamcode.cc/releases") }
}

dependencies {
    // =-- Okaeri --=
    // configuration
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:4.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:4.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:4.0.6")
    implementation("eu.okaeri:okaeri-configs-json-simple:4.0.6")

    // pluarlize
    implementation("eu.okaeri:okaeri-pluralize:1.1.0")

    // persistence
    implementation ("eu.okaeri:okaeri-persistence-flat:2.0.0-beta.1")
    implementation ("eu.okaeri:okaeri-persistence-jdbc:2.0.0-beta.1")
    implementation ("eu.okaeri:okaeri-persistence-redis:2.0.0-beta.1")
    implementation ("eu.okaeri:okaeri-persistence-mongo:2.0.0-beta.1")

    // =-- dreamcode --=
    // notice
    implementation("cc.dreamcode.notice:core:1.0.4")
    implementation("cc.dreamcode.notice:bukkit:1.0.4")
    implementation("cc.dreamcode.notice:bukkit-okaeri-serdes:1.0.4")

    // menu
    implementation ("cc.dreamcode.menu:core:0.4.2")
    implementation ("cc.dreamcode.menu:bukkit:0.4.2")
    implementation ("cc.dreamcode.menu:serdes-bukkit:0.4.2")
    implementation ("cc.dreamcode.menu:serdes-bukkit-okaeri:0.4.2")

    // =-- Others --=
    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // engine
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // command framework
    implementation ("dev.rollczi.litecommands:bukkit:2.7.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "$mainPackage.TimeShopPlugin"
    apiVersion = "1.13"
    prefix = "dreamTimeShop"
    author = "Piotrulla"
    name = "dreamTimeShop"
    version = "${project.version}"
    description = "DreamCode.cc - https://discord.gg/dreamcode"

    commands {
        register("timeshop") {
            description = "timeshop command"
            aliases = listOf("sklepczas")
        }
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation")
    options.encoding = "UTF-8"
}

tasks {
    runServer {
        minecraftVersion("1.19.1")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("dreamTimeShop-${project.version}_1.8-1.19.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**",
    )

    mergeServiceFiles()
    minimize()

    val prefix = "$mainPackage.libs"

    listOf(
        "org.bstats",
        "eu.okaeri",
        "net.kyori",
        "cc.dreamcode.notice",
        "org.bstats"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}