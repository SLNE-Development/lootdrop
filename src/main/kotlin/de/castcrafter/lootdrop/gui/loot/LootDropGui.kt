package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.gui.*
import de.castcrafter.lootdrop.loot.LootDrop
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import org.bukkit.entity.Player

fun lootDropGui(player: Player) = playerMenu(title3Gui, player, 3) {
    staticPane(slot(0, 0), 3, 9) {
        item(slot(1, 1), goodLootItem) {
            click = {
                playClickSound()
                lootDropContentGui(true).open()
            }
        }

        item(slot(2, 1), badLootItem) {
            click = {
                playClickSound()
                lootDropContentGui(false).open()
            }
        }

        item(slot(3, 1), goodLootProbabilityItem) {
            click = {
                playClickSound()
                lootDropProbabilityGui(player).open()
            }
        }

        item(slot(6, 1), listLootDropItem) {
            click = {
                playClickSound()
                lootDropListGui(player).open()
            }
        }

        item(slot(7, 1), spawnLootDropItem) {
            click = {
                playClickSound()
                LootDrop(player).spawn()
            }
        }
    }
}