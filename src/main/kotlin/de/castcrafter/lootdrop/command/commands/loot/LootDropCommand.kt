package de.castcrafter.lootdrop.command.commands.loot

import de.castcrafter.lootdrop.loot.LootDrop
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.doubleArgument
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.util.objectListOf
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun lootDropCommand() = commandAPICommand("lootdrop") {
    withPermission(PermissionRegistry.LOOTDROP_COMMAND)

    doubleArgument("goodChance", min = 0.1, max = 1.0)

    playerExecutor { player, args ->
        val goodChance: Double by args

        LootDrop(
            initiator = player,
            goodChance = goodChance,
            goodLootContent = objectListOf(
                ItemStack.of(Material.DIAMOND)
            ),
            badLootContent = objectListOf(
                ItemStack.of(Material.TNT, 10),
            )
        ).spawn()
    }
}