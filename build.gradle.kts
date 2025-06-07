import dev.slne.surf.surfapi.gradle.util.registerSoft
import dev.slne.surf.surfapi.gradle.util.withSurfApiBukkit

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("de.castcrafter.lootdrop.Main")
    authors.addAll("CastCrafter", "Ammo")

    generateLibraryLoader(false)

    serverDependencies {
        registerSoft("ChestProtect")
    }

    runServer {
        withSurfApiBukkit()
    }
}

dependencies {
    compileOnlyApi(libs.chestprotect)
    api(libs.glyphs)
}

group = "de.castcrafter"
version = "2.0.0-SNAPSHOT"
