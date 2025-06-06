package de.castcrafter.lootdrop.command.commands.event.bridge

import com.github.shynixn.mccoroutine.folia.launch
import com.github.shynixn.mccoroutine.folia.ticks
import de.castcrafter.lootdrop.plugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.surf.surfapi.bukkit.api.util.dispatcher
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.random
import dev.slne.surf.surfapi.core.api.util.toObjectList
import it.unimi.dsi.fastutil.objects.ObjectList
import kotlinx.coroutines.*
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.asKotlinRandom

private var job: Job? = null

fun CommandAPICommand.bridgeEvent() = subcommand("bridge") {
    subcommand("start") {
        entitySelectorArgumentManyPlayers("players")

        timeArgument(
            "timeBetweenEachBlock",
            optional = true
        )
        booleanArgument("playersReceiveSameBlock", true)
        booleanArgument("includeItems", true)
        booleanArgument("includeNonSolids", true)

        anyExecutor { sender, args ->
            val players: List<Player> by args
            val timeBetweenEachBlock: Int? by args
            val playersReceiveSameBlock: Boolean? by args
            val includeItems: Boolean? by args
            val includeNonSolids: Boolean? by args

            val ticksBetweenEachBlockFilled = (timeBetweenEachBlock ?: 20).ticks

            if (players.size < 2) {
                throw CommandAPI.failWithString("You must specify at least two players to start the bridge event.")
            }

            if (job?.isActive == true) {
                throw CommandAPI.failWithString("A bridge event is already running.")
            }

            job = plugin.launch {
                while (isActive) {
                    rollItems(
                        includeItems ?: true,
                        includeNonSolids ?: true,
                        playersReceiveSameBlock ?: false,
                        players
                    ).map { (player, item) ->
                        async(player.dispatcher()) {
                            player.inventory.addItem(item).forEach { dropped ->
                                player.world.dropItemNaturally(
                                    player.location,
                                    dropped.value
                                ) { itemEntity ->
                                    itemEntity.owner = player.uniqueId
                                }
                            }

                            player.playSound {
                                type(Sound.ENTITY_CHICKEN_EGG)
                                volume(.5f)
                            }
                        }
                    }.awaitAll()

                    delay(ticksBetweenEachBlockFilled)
                }
            }

            sender.sendText {
                appendPrefix()

                success("Das Bridge-Event wurde gestartet")
            }
        }
    }

    subcommand("cancel") {
        anyExecutor { sender, args ->
            val currentJob = job
            if (currentJob?.isActive != true) {
                throw CommandAPI.failWithString("No bridge event is currently running.")
            }

            plugin.launch {
                currentJob.cancelAndJoin()
                job = null
                sender.sendText {
                    appendPrefix()

                    success("Das Bridge-Event wurde abgebrochen")
                }
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
    val materials = MaterialCache.get(includeItems, includeNonSolids)
    if (materials.isEmpty()) return emptyMap()

    return if (playersReceiveSameBlock) {
        val shared = ItemStack.of(materials.random(random.asKotlinRandom()))
        players.associateWith { shared }
    } else {
        players.associateWith { ItemStack.of(materials.random(random.asKotlinRandom())) }
    }
}

private object MaterialCache {
    private val all = Material.entries.filterNot { it.isLegacy }.toObjectList()
    private val blocksOnly = all.filterNot { it.isItem }.toObjectList()
    private val solidsAndItems = all.filter { it.isSolid || it.isItem }.toObjectList()
    private val solidBlocks = all.filter { it.isSolid && !it.isItem }.toObjectList()

    fun get(includeItems: Boolean, includeNonSolids: Boolean): ObjectList<Material> =
        when {
            includeItems && includeNonSolids -> all
            !includeItems && includeNonSolids -> blocksOnly
            includeItems && !includeNonSolids -> solidsAndItems
            else -> solidBlocks
        }
}