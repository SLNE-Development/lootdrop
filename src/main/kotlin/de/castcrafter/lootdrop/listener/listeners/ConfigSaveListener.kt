package de.castcrafter.lootdrop.listener.listeners

import de.castcrafter.lootdrop.config.playeruse.PlayerUseConfig
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldSaveEvent

object ConfigSaveListener : Listener {

    @EventHandler
    fun onWorldSave(event: WorldSaveEvent) {
        if (event.world.environment == World.Environment.NORMAL) {
            PlayerUseConfig.INSTANCE.saveConfig()
        }
    }
}
