package de.castcrafter.lootdrop.listener

import de.castcrafter.lootdrop.listener.listeners.*
import de.castcrafter.lootdrop.locator.LocatorListener
import dev.slne.surf.surfapi.bukkit.api.event.register

object ListenerManager {

    fun registerListeners() {
        SeamineListener.register()
        JoinListener.register()
        ConfigSaveListener.register()
        ChestListener.register()
        SpecialItemListener.register()
        LocatorListener.register()
    }

}
