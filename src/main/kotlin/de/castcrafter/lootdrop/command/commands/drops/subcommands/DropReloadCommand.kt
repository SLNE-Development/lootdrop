package de.castcrafter.lootdrop.command.commands.drops.subcommands

import de.castcrafter.lootdrop.config.LootDropConfig
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.dropsReloadCommand() = subcommand("reload") {
    withPermission(PermissionRegistry.DROPS_COMMAND_RELOAD)

    subcommand("confirm") {
        withPermission(PermissionRegistry.DROPS_COMMAND_RELOAD)

        playerExecutor { player, args ->
            LootDropConfig.INSTANCE.loadConfig()
            player.sendText {
                appendPrefix()

                success("Die Drops-Konfiguration wurde neu geladen!")
            }
        }
    }

    playerExecutor { player, args ->
        player.sendText {
            appendPrefix()

            error("Bist du sicher, dass du die Drops-Konfiguration neu laden möchtest? Dies wird den Fortschritt aller Spieler zurücksetzen. Um den Fortschritt zu speichern, führe zuerst /save-all aus!")
        }

        player.sendText {
            appendPrefix()

            error("Benutze /drops reload confirm, um die Konfiguration neu zu laden!")
        }
    }
}