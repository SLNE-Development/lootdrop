package de.castcrafter.lootdrop.listener.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

object JoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (player.hasPlayedBefore()) {
            return
        }

        player.inventory.addItem(ItemStack(Material.PUMPKIN_PIE, 64))
    }

}
