package de.castcrafter.lootdrop.command.commands

import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage

fun renameCommand() = commandAPICommand("rename") {
    withPermission(PermissionRegistry.RENAME_COMMAND)

    greedyStringArgument("name")

    playerExecutor { player, args ->
        val name: String by args

        if (name.isBlank()) {
            player.sendText {
                appendPrefix()

                error("Du musst einen Namen angeben")
            }
            return@playerExecutor
        }

        val item = player.inventory.itemInMainHand

        if (item.type.isAir) {
            player.sendText {
                appendPrefix()

                error("Du musst ein Item in der Hand halten")
            }
            return@playerExecutor
        }

        val meta = item.itemMeta ?: run {
            player.sendText {
                appendPrefix()

                error("Das Item hat keine Metadaten")
            }

            return@playerExecutor
        }

        meta.displayName(
            MiniMessage.miniMessage().deserialize(name).decoration(TextDecoration.ITALIC, false)
        )

        item.setItemMeta(meta)

        player.sendText {
            appendPrefix()

            success("Du hast den Namen erfolgreich geändert")
        }
    }
}