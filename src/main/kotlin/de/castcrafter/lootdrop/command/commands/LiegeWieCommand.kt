package de.castcrafter.lootdrop.command.commands

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.plugin
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.showTitle
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun liegeWieCommand() = commandAPICommand("liegewie") {
    withPermission(PermissionRegistry.LIEGEWIE_COMMAND)

    playerExecutor { _, _ ->
        plugin.launch {
            Bukkit.getOnlinePlayers().forEach { player ->
                withContext(plugin.entityDispatcher(player)) {
                    player.inventory.addItem(ItemStack.of(Material.LIME_BED))
                    player.showTitle {
                        title {
                            success("Liege wie")
                        }
                        subtitle {
                            spacer("")
                        }
                        times {
                            fadeIn(250.milliseconds)
                            stay(5.seconds)
                            fadeOut(250.milliseconds)
                        }
                    }
                }
            }
        }
    }
}