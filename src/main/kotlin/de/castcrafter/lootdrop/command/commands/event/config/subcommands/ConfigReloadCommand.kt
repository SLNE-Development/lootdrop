package de.castcrafter.lootdrop.command.commands.event.config.subcommands

import de.castcrafter.lootdrop.config.LootDropConfig
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.eventConfigReloadCommand() = subcommand("reload") {
    withPermission(PermissionRegistry.CONFIG_COMMAND_RELOAD)

    subcommand("confirm") {
        withPermission(PermissionRegistry.CONFIG_COMMAND_RELOAD)

        playerExecutor { player, _ ->
            LootDropConfig.INSTANCE.loadConfig()

            player.sendText {
                appendPrefix()

                success("Die Konfiguration wurde neu geladen!")
            }
        }
    }

    playerExecutor { player, _ ->
        player.sendText {
            appendPrefix()

            error(
                "Bist du sicher, dass du die Config neu laden möchtest? Dies " +
                        "setzt ebenso alle aktuellen Fortschritte der Spieler zurück. Um " +
                        "diese zu speichern, führe zuerst /save-all aus!"
            )
        }

        player.sendText {
            appendPrefix()

            error("Nutze /event config reload confirm, um die Config neu zu laden!")
        }
    }
}