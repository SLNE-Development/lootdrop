package de.castcrafter.lootdrop.gui.loot

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane
import com.github.stefvanschie.inventoryframework.pane.component.PagingButtons
import de.castcrafter.lootdrop.gui.*
import de.castcrafter.lootdrop.loot.LootDropManager
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.childPlayerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import org.bukkit.entity.Player

fun SurfChestSinglePlayerGui.lootDropListGui(player: Player) = childPlayerMenu(title4Gui, 4) {
    staticPane(slot(0, 0), 4) {
        item(slot(4, 3), backItem) {
            click = {
                playClickSound()
                whoClicked.backToParent()
            }
        }

        if (LootDropManager.lootdrops.isEmpty()) {
            item(slot(4, 1), emptyLootDropItem) {
                click = {
                    playClickSound()
                    whoClicked.backToParent()
                }
            }
            return@staticPane
        }
    }

    val pages = PaginatedPane(slot(0, 0), 9, 3).apply {
        populateWithGuiItems(LootDropManager.lootdrops.map { loot ->
            GuiItem(buildLootDropItem(loot.uniqueId.toString())) {
                it.playClickSound()
                lootDropListOneGui(player, loot).open()
            }
        })
    }

    addPane(PagingButtons(slot(0, 3), 9, pages).apply {
        setForwardButton(GuiItem(pageForwardItem) {
            it.playClickSound()
        })
        setBackwardButton(GuiItem(pageBackwardItem) {
            it.playClickSound()
        })
    })

    addPane(pages)
}