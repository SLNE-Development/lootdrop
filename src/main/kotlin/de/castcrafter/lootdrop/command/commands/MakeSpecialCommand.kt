package de.castcrafter.lootdrop.command.commands

import de.castcrafter.lootdrop.listener.listeners.SpecialItemListener
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.persistence.PersistentDataType

fun makeSpecialCommand() = commandAPICommand("makespecial") {
    withPermission(PermissionRegistry.MAKESPECIAL_COMMAND)

    playerExecutor { player, _ ->
        val mainHand = player.inventory.itemInMainHand

        if (mainHand.type.isAir) {
            player.sendText {
                appendPrefix()

                error("Du musst ein Item in der Hand halten")
            }
            return@playerExecutor
        }

        val meta = mainHand.itemMeta ?: run {
            player.sendText {
                appendPrefix()

                error("Das Item hat keine Metadaten")
            }

            return@playerExecutor
        }

        meta.persistentDataContainer.set(
            SpecialItemListener.SPECIAL_KEY,
            PersistentDataType.BYTE,
            1.toByte()
        )

        mainHand.itemMeta = meta

        player.sendText {
            appendPrefix()

            success("Das Item ist jetzt ein spezielles Item")
        }
    }
}