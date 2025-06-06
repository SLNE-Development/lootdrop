package de.castcrafter.lootdrop.command.commands.event.bridge

import com.github.shynixn.mccoroutine.folia.launch
import com.github.shynixn.mccoroutine.folia.ticks
import de.castcrafter.lootdrop.plugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private var job: Job? = null

fun CommandAPICommand.bridgeEvent() = subcommand("bridge") {
    subcommand("start") {
        entitySelectorArgumentManyPlayers("players")

        integerArgument(
            "ticksBetweenEachBlock",
            min = 1,
            optional = true
        )
        booleanArgument("playersReceiveSameBlock", true)
        booleanArgument("includeItems", true)
        booleanArgument("includeNonSolids", true)

        anyExecutor { sender, args ->
            val players: List<Player> by args
            val ticksBetweenEachBlock: Int? by args
            val playersReceiveSameBlock: Boolean? by args
            val includeItems: Boolean? by args
            val includeNonSolids: Boolean? by args

            val ticksBetweenEachBlockFilled = ticksBetweenEachBlock ?: 20

            if (players.size < 2) {
                throw CommandAPI.failWithString("You must specify at least two players to start the bridge event.")
            }

            if (job != null && job!!.isActive) {
                throw CommandAPI.failWithString("A bridge event is already running.")
            }

            job = plugin.launch {
                rollItems(
                    includeItems ?: true,
                    includeNonSolids ?: true,
                    playersReceiveSameBlock ?: false,
                    players
                ).forEach { (player, item) ->
                    player.inventory.addItem(item).forEach { dropped ->
                        player.world.dropItemNaturally(
                            player.location,
                            dropped.value
                        )
                    }

                    player.playSound {
                        type(Sound.ENTITY_CHICKEN_EGG)
                        volume(.5f)
                    }
                }

                delay(ticksBetweenEachBlockFilled.ticks)
            }

            sender.sendText {
                appendPrefix()

                success("Das Bridge-Event wurde gestartet")
            }
        }
    }

    subcommand("cancel") {
        anyExecutor { sender, args ->
            if (job == null || !job!!.isActive) {
                throw CommandAPI.failWithString("No bridge event is currently running.")
            }

            job!!.cancel()
            job = null

            sender.sendText {
                appendPrefix()

                success("Das Bridge-Event wurde abgebrochen")
            }
        }
    }
}

private fun rollItems(
    includeItems: Boolean,
    includeNonSolids: Boolean,
    playersReceiveSameBlock: Boolean,
    players: List<Player>
): Map<Player, ItemStack> {
    var materials = Material.entries.toList()

    if (!includeItems) {
        materials = materials.filterNot { it.isItem }
    }

    if (!includeNonSolids) {
        materials = materials.filter { it.isSolid }
    }

    if (playersReceiveSameBlock) {
        val item = ItemStack(materials.random())

        return players.associateWith { item }
    }

    return players.associateWith { ItemStack(materials.random()) }
}