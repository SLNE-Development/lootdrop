package de.castcrafter.lootdrop.command.commands.drops

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.command.commands.drops.subcommands.dropsReloadCommand
import de.castcrafter.lootdrop.command.commands.drops.subcommands.dropsResetCommand
import de.castcrafter.lootdrop.gui.drops.DropsGui
import de.castcrafter.lootdrop.plugin
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand

fun CommandAPICommand.dropsCommand() = subcommand("drops") {
    withPermission(PermissionRegistry.DROPS_COMMAND)

    dropsReloadCommand()
    dropsResetCommand()

    playerExecutor { player, args ->
        plugin.launch(plugin.entityDispatcher(player)) {
            DropsGui(player).show(player)
        }
    }
}