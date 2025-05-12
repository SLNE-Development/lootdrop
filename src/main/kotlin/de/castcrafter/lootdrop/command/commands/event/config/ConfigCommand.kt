package de.castcrafter.lootdrop.command.commands.event.config

import de.castcrafter.lootdrop.command.commands.event.config.subcommands.eventConfigReloadCommand
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand

fun CommandAPICommand.eventConfigCommand() = subcommand("config") {
    withPermission(PermissionRegistry.CONFIG_COMMAND)

    eventConfigReloadCommand()
}