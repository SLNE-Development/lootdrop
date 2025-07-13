package de.castcrafter.lootdrop.command.commands.loot

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.dialog.lootDropDialog
import de.castcrafter.lootdrop.plugin
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor

fun lootDropCommand() = commandAPICommand("lootdrop") {
    withPermission(PermissionRegistry.LOOTDROP_COMMAND)

    playerExecutor { player, _ ->
        plugin.launch(plugin.entityDispatcher(player)) {
            player.showDialog(lootDropDialog())
        }
    }
}