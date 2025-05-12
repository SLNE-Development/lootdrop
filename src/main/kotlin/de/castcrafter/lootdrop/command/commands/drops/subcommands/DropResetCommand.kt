package de.castcrafter.lootdrop.command.commands.drops.subcommands

import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.config.LootDropConfig
import de.castcrafter.lootdrop.plugin
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlinx.coroutines.future.await
import org.bukkit.OfflinePlayer
import java.util.concurrent.CompletableFuture

fun CommandAPICommand.dropsResetCommand() = subcommand("reset") {
    withPermission(PermissionRegistry.DROPS_COMMAND_RESET)

    longArgument(
        "offset",
        min = 0,
        max = LootDropConfig.INSTANCE.trades.maxOf { it.offset }) {
        replaceSuggestions(ArgumentSuggestions.stringCollection { _ ->
            LootDropConfig.INSTANCE.trades.map { it.offset.toString() }
        })
    }

    asyncOfflinePlayerArgument("target", true)

    playerExecutor { player, args ->
        val offset: Long by args
        val target: CompletableFuture<OfflinePlayer?> by args

        plugin.launch {
            val offlineTarget = target.await()
            val config = LootDropConfig.INSTANCE

            if (offlineTarget != null) {
                config.getTrades(offset).forEach { trade ->
                    trade.resetPlayer(offlineTarget.uniqueId)
                }

                player.sendText {
                    appendPrefix()

                    success("Der Drop mit dem Offset ")
                    variableValue(offset.toString())
                    success(" wurde für ")
                    variableValue(offlineTarget.name ?: "Unbekannt")
                    success(" zurückgesetzt")
                }
            } else {
                config.getTrades(offset).forEach { trade ->
                    trade.resetAllPlayers()
                }

                player.sendText {
                    appendPrefix()

                    success("Der Drop mit dem Offset ")
                    variableValue(offset.toString())
                    success(" wurde für alle Spieler zurückgesetzt")
                }
            }
        }
    }
}