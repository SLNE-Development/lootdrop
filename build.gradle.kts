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
//        registerRequired("Nexo")
    }

    runServer {
        withSurfApiBukkit()
    }
}

dependencies {
    compileOnlyApi(libs.nexo)
    compileOnlyApi(libs.chestprotect)
}

group = "de.castcrafter"
version = "2.0.0-SNAPSHOT"
description = "loot_drop"