import dev.slne.surf.surfapi.gradle.util.registerRequired
import dev.slne.surf.surfapi.gradle.util.withSurfApiBukkit

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("de.castcrafter.lootdrop.Main")
    authors.addAll("CastCrafter", "Ammo")

    generateLibraryLoader(false)

    serverDependencies {
        registerRequired("PlaceholderAPI")
    }

    runServer {
        withSurfApiBukkit()
    }
}

dependencies {
    compileOnlyApi(libs.paper.api)
    compileOnlyApi(libs.commandapi)
    compileOnlyApi(libs.placeholder.api)
    compileOnlyApi(libs.oraxen)
    compileOnlyApi(libs.betterhud.standard)
    compileOnlyApi(libs.betterhud.bukkit)
    compileOnlyApi(libs.betterhud.commands)
    compileOnlyApi(libs.chestprotect)

    api(libs.inventory.framework)
    api(libs.configurate.core)
    api(libs.configurate.yml)
}

group = "de.castcrafter"
version = "2.0.0-SNAPSHOT"
description = "loot_drop"

tasks {
    shadowJar {
        relocate(
            "com.github.stefvanschie.inventoryframework",
            "de.castcrafter.lootdrop.inventoryframework"
        )
        relocate("org.spongepowered", "de.castcrafter.lootdrop.spongepowered")
    }
}
