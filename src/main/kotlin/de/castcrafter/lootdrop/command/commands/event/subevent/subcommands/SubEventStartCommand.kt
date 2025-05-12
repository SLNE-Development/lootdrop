package de.castcrafter.lootdrop.command.commands.event.subevent.subcommands

import de.castcrafter.lootdrop.Messages
import de.castcrafter.lootdrop.subevent.SubEventManager
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.Bukkit
import org.bukkit.Sound

fun CommandAPICommand.subEventStartCommand() = subcommand("start") {
    withPermission(PermissionRegistry.SUBEVENT_COMMAND_START)

    playerExecutor { player, _ ->
        if (SubEventManager.eventLocation == null) {
            player.sendMessage(Messages.noEventCreatedComponent())
            return@playerExecutor
        }

        SubEventManager.isEventStarted = true

        Bukkit.getOnlinePlayers().forEach { online ->
            online.sendText {
                appendNewline()

                success("Ein neues Event wurde gestartet!")
                success("\u2320").hoverEvent(buildText {
                    spacer("Klicke, um am Event teilzunehmen")
                }).clickEvent(ClickEvent.runCommand("/join"))

                appendNewline()
            }

            online.playSound {
                type(Sound.ENTITY_ENDER_DRAGON_GROWL)
                volume(0.5f)
                pitch(0.75f)
                source(net.kyori.adventure.sound.Sound.Source.HOSTILE)
            }
        }
    }
}