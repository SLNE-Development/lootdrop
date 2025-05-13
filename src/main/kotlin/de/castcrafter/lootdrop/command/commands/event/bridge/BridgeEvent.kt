package de.castcrafter.lootdrop.command.commands.event.bridge

import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.plugin
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.time.Duration.Companion.seconds

fun CommandAPICommand.bridgeEvent() = subcommand("bridge") {
    plugin.launch {
        val items = Material.entries.filter { it.isBlock || it.isItem }

        while (true) {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.inventory.addItem(ItemStack.of(items.random()))
            }

            delay(1.seconds)
        }
    }
}