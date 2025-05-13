package de.castcrafter.lootdrop.gui.loot

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane
import com.github.stefvanschie.inventoryframework.pane.component.PagingButtons
import de.castcrafter.lootdrop.loot.LootDropManager
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.drawOutlineRow
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import org.bukkit.Material
import org.bukkit.entity.Player

fun lootDropListGui(player: Player) = playerMenu(text("LootDrop Liste"), player, 5) {
    drawOutlineRow(0)
    drawOutlineRow(4)

    val pages = PaginatedPane(slot(0, 1), 9, 3)

    pages.populateWithGuiItems(LootDropManager.lootdrops.map { lootdrop ->
        GuiItem(ItemStack(Material.BARREL) {
            displayName {
                primary(lootdrop.uniqueId.toString())
            }
        }) {
            lootDropListOneGui(player, lootdrop).open()
        }
    })

    val pagingButtons = PagingButtons(9, pages)
    pagingButtons.setForwardButton(GuiItem(ItemStack(Material.ARROW) {
        displayName {
            primary("Nächste Seite")
        }
    }))
    pagingButtons.setBackwardButton(GuiItem(ItemStack(Material.ARROW) {
        displayName {
            primary("Vorherige Seite")
        }
    }))

    addPane(pagingButtons)
    addPane(pages)
}