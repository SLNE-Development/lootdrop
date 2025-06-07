package de.castcrafter.lootdrop.listener.listeners

import dev.slne.surf.surfapi.bukkit.api.extensions.server
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object RespawnListener : Listener {

    private val endWorld
        get() = server.worlds.first { it.environment == World.Environment.THE_END }

    @EventHandler
    fun onJoin(event: PlayerRespawnEvent) {
        val player = event.player
        val respawnLocation = event.respawnLocation

        if (respawnLocation.world.environment != World.Environment.THE_END) {
            player.teleportAsync(endWorld.spawnLocation)
        }
    }
}