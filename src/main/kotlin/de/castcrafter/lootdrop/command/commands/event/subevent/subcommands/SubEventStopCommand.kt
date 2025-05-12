package de.castcrafter.lootdrop.command.commands.event.subevent.subcommands

import de.castcrafter.lootdrop.Messages
import de.castcrafter.lootdrop.subevent.SubEventManager
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import org.bukkit.Bukkit

fun CommandAPICommand.subEventStopCommand() = subcommand("stop") {
    withPermission(PermissionRegistry.SUBEVENT_COMMAND_STOP)

    playerExecutor { player, _ ->
        if (!SubEventManager.isEventStarted) {
            player.sendMessage(Messages.noEventStartedComponent())

            return@playerExecutor
        }

        SubEventManager.isEventStarted = false
        SubEventManager.eventLocation = null

        Bukkit.broadcast(buildText {
            appendPrefix()

            success("Das Event wurde beendet")
        })
    }
}