package de.castcrafter.lootdrop.command.commands.event.subevent.subcommands

import de.castcrafter.lootdrop.subevent.SubEventManager
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.subEventCreateCommand() = subcommand("create") {
    withPermission(PermissionRegistry.SUBEVENT_COMMAND_CREATE)

    playerExecutor { player, _ ->
        SubEventManager.eventLocation = player.location

        player.sendText {
            appendPrefix()

            success("Das Event wurde an deiner Position erstellt")
        }
    }
}