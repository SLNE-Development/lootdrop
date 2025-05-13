package de.castcrafter.lootdrop.command.commands.event.subevent.subcommands

import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.Messages
import de.castcrafter.lootdrop.plugin
import de.castcrafter.lootdrop.subevent.SubEventManager
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlinx.coroutines.future.await

fun CommandAPICommand.subEventJoinCommand() = subcommand("join") {
    withPermission(PermissionRegistry.SUBEVENT_COMMAND_JOIN)

    playerExecutor { player, _ ->
        val eventLocation = SubEventManager.eventLocation
        val eventStarted = SubEventManager.isEventStarted

        if (eventLocation == null) {
            player.sendMessage(Messages.noEventStartedComponent())
            return@playerExecutor
        }

        if (!eventStarted) {
            player.sendMessage(Messages.noEventStartedComponent())
            return@playerExecutor
        }

        plugin.launch {
            player.teleportAsync(eventLocation).await()
            player.sendText {
                appendPrefix()

                success("Du wurdest zum Event teleportiert")
            }
        }
    }
}