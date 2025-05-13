package de.castcrafter.lootdrop.command.commands.event.subevent

import de.castcrafter.lootdrop.command.commands.event.subevent.subcommands.subEventCreateCommand
import de.castcrafter.lootdrop.command.commands.event.subevent.subcommands.subEventJoinCommand
import de.castcrafter.lootdrop.command.commands.event.subevent.subcommands.subEventStartCommand
import de.castcrafter.lootdrop.command.commands.event.subevent.subcommands.subEventStopCommand
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand

fun CommandAPICommand.subEventCommand() = subcommand("subevent") {
    withPermission(PermissionRegistry.SUBEVENT_COMMAND)

    subEventCreateCommand()
    subEventStartCommand()
    subEventStopCommand()
    subEventJoinCommand()
}