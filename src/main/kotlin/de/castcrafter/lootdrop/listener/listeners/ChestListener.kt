package de.castcrafter.lootdrop.listener.listeners

import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.objectListOf
import me.angeschossen.chestprotect.api.events.ProtectionPreCreationEvent
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType


private val CHEST_TYPES = objectListOf(
    Material.CHEST,
    Material.BARREL,
    Material.SHULKER_BOX
)

private val LOOT_KEY = NamespacedKey("lootdrop", "loot")


object ChestListener : Listener {

    @EventHandler
    fun onChestLock(event: ProtectionPreCreationEvent) {
        event.isCancelled = checkBlock(event.location.block, event.player.player, true)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.state !is TileState) {
            return
        }

        event.isCancelled = checkBlock(event.block, event.player, false)
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        // TODO: fix wither
        event.blockList().removeIf { block: Block -> checkBlock(block, null, false) }
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        event.blockList().removeIf { block: Block -> checkBlock(block, null, false) }
    }

    @EventHandler
    fun onLootGenerate(event: LootGenerateEvent) {
        val block = event.lootContext.location.block

        if (!CHEST_TYPES.contains(block.type)) {
            return
        }

        val tileState = block.state as TileState? ?: return
        tileState.persistentDataContainer.set(LOOT_KEY, PersistentDataType.BOOLEAN, true)
    }
}

object ChestChestProtectListener : Listener {

    @EventHandler
    fun onChestLock(event: ProtectionPreCreationEvent) {
        event.isCancelled = checkBlock(event.location.block, event.player.player, true)
    }
}


private fun checkBlock(block: Block, player: Player?, placed: Boolean): Boolean {
    if (!CHEST_TYPES.contains(block.type)) {
        return false
    }

    val tileState = block.state as TileState? ?: return false

    val pdc: PersistentDataContainer = tileState.persistentDataContainer

    if (pdc.has(LOOT_KEY, PersistentDataType.BOOLEAN) && player != null) {
        if (placed) {
            player.sendText {
                appendPrefix()

                error("Diese Kiste kann nicht protected werden. Sei kein Arsch!")
            }

            return true
        }

        player.sendText {
            appendPrefix()

            error("Diese Kiste kann nicht abgebaut werden. Sei kein Arsch!")
        }

        return true
    }

    return false
}