package de.castcrafter.lootdrop.command.commands.event.mines

import de.castcrafter.lootdrop.command.commands.event.mines.seamine.seamineCommand
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand

fun CommandAPICommand.minesCommand() = subcommand("mines") {
    withPermission(PermissionRegistry.MINES_COMMAND)

    seamineCommand()
}