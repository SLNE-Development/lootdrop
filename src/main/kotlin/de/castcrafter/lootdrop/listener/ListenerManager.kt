package de.castcrafter.lootdrop.listener

import de.castcrafter.lootdrop.listener.listeners.ChestChestProtectListener
import de.castcrafter.lootdrop.listener.listeners.ChestListener
import de.castcrafter.lootdrop.listener.listeners.JoinListener
import de.castcrafter.lootdrop.listener.listeners.RespawnListener
import de.castcrafter.lootdrop.listener.listeners.SeamineListener
import de.castcrafter.lootdrop.listener.listeners.SpecialItemListener
import de.castcrafter.lootdrop.locator.LocatorListener
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.extensions.pluginManager

object ListenerManager {

    fun registerListeners() {
        SeamineListener.register()
        JoinListener.register()
        RespawnListener.register()

        ChestListener.register()
        if (pluginManager.isPluginEnabled("ChestProtect")) {
            ChestChestProtectListener.register()
        }

        SpecialItemListener.register()
        LocatorListener.register()
    }

}
