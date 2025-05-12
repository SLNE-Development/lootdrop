package de.castcrafter.lootdrop.listener.listeners

import org.bukkit.GameMode
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

private const val EXPLOSION_RANGE = 5.0
val SEAMINE_KEY = NamespacedKey("lootdrop", "seamine")

object SeamineListener : Listener {

    @EventHandler
    fun onPlayerApproachMine(event: PlayerMoveEvent) {
        if (!event.hasChangedBlock()) {
            return
        }

        val player = event.player
        if (!(player.gameMode == GameMode.SURVIVAL || player.gameMode == GameMode.ADVENTURE)) {
            return
        }

        val nearbyEntities = player.getNearbyEntities(
            EXPLOSION_RANGE,
            EXPLOSION_RANGE,
            EXPLOSION_RANGE
        ).filterIsInstance<ArmorStand>()

        nearbyEntities.forEach { nearby ->
            if (!nearby.persistentDataContainer.has(SEAMINE_KEY)) {
                return@forEach
            }

            nearby.world.spawn(nearby.location, TNTPrimed::class.java).fuseTicks = 1

            val nearbyPlayers = nearby.getNearbyEntities(
                EXPLOSION_RANGE,
                EXPLOSION_RANGE,
                EXPLOSION_RANGE
            ).filterIsInstance<Player>()

            nearbyPlayers.forEach { nearbyPlayer ->
                nearbyPlayer.damage(8.0)
            }

            nearby.remove()
        }
    }
}