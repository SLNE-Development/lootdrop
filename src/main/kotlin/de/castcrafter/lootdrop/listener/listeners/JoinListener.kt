package de.castcrafter.lootdrop.listener.listeners

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.extensions.server
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.block.ShulkerBox
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.persistence.PersistentDataType

object JoinListener : Listener {

    private val endGatewayKey = NamespacedKey(plugin, "end_gateway")

    private val shulker by lazy {
        val shulker = ItemStack(Material.CYAN_SHULKER_BOX)
        val shulkerMeta = shulker.itemMeta as BlockStateMeta
        val shulkerBox = shulkerMeta.blockState as ShulkerBox
        val shulkerInventory = shulkerBox.inventory

        shulkerInventory.addItem(ItemStack(Material.ICE))
        shulkerInventory.addItem(ItemStack(Material.LAVA_BUCKET))
        shulkerInventory.addItem(ItemStack(Material.SUGAR_CANE))
        shulkerInventory.addItem(ItemStack(Material.POINTED_DRIPSTONE))
        shulkerInventory.addItem(ItemStack(Material.MELON_SLICE))
        shulkerInventory.addItem(ItemStack(Material.PUMPKIN))
        shulkerInventory.addItem(ItemStack(Material.BAMBOO))
        shulkerInventory.addItem(ItemStack(Material.SPRUCE_SAPLING, 4))
        shulkerInventory.addItem(ItemStack(Material.DIRT, 8))
        shulkerInventory.addItem(ItemStack(Material.GRASS_BLOCK))
        shulkerInventory.addItem(ItemStack(Material.BONE, 8))
        shulkerInventory.addItem(ItemStack(Material.SOUL_TORCH, 16))

        shulkerBox.update()

        shulkerMeta.blockState = shulkerBox
        shulker.itemMeta = shulkerMeta
        shulker
    }

    private val endWorld
        get() = server.worlds.first { it.environment == World.Environment.THE_END }


    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (player.world.environment != World.Environment.THE_END) {
            player.teleportAsync(endWorld.spawnLocation)
        }

        if (player.hasPlayedBefore()) {
            return
        }

        player.inventory.setItem(0, ItemStack(Material.BOW))
        player.inventory.setItem(6, ItemStack(Material.ARROW, 10))
        player.inventory.setItem(7, ItemStack(Material.STRIPPED_PALE_OAK_LOG, 16))
        player.inventory.setItem(8, ItemStack(Material.PUMPKIN_PIE, 16))
    }

    @EventHandler
    fun onEndGateway(event: PlayerTeleportEndGatewayEvent) {
        val player = event.player
        val pdc = player.persistentDataContainer

        if (pdc.has(endGatewayKey)) {
            return
        }

        pdc.set(endGatewayKey, PersistentDataType.BYTE, 1.toByte())

        player.inventory.addItem(shulker)
    }
}
