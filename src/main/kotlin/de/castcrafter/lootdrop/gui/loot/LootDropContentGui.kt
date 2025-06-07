package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.gui.backItem
import de.castcrafter.lootdrop.gui.edgeKey
import de.castcrafter.lootdrop.gui.playClickSound
import de.castcrafter.lootdrop.gui.title4Gui
import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.childPlayerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.toObjectList

fun SurfChestSinglePlayerGui.lootDropContentGui(
    goodLoot: Boolean,
    editable: Boolean = true
) = childPlayerMenu(title4Gui, 4) {
    this.setOnBottomClick { event -> event.isCancelled = !editable }
    this.setOnBottomDrag { event -> event.isCancelled = !editable }
    this.setOnTopClick { event -> event.isCancelled = !editable }
    this.setOnTopDrag { event -> event.isCancelled = !editable }

    val lootContent = if (goodLoot) {
        LootDropConfigurator.goodLootContent
    } else {
        LootDropConfigurator.badLootContent
    }

    staticPane(slot(0, 3), 1) {
        item(slot(4, 0), backItem) {
            click = {
                playClickSound()
                isCancelled = true
                whoClicked.backToParent()
            }
        }
    }

    staticPane(slot(0, 0), 3) {
        if (lootContent.size > this@staticPane.length) {
            error("$lootContent more than ${inventory.size} loot.")
        }

        lootContent.forEachIndexed { index, itemStack ->
            item(slot(index % 9, index / 9), itemStack) { }
        }
    }

    setOnClose {
        if (!editable) return@setOnClose

        val storageContent = inventory.storageContents.filterNotNull().filterNot { item ->
            item.persistentDataContainer.has(edgeKey)
        }.toObjectList()

        if (goodLoot) {
            LootDropConfigurator.goodLootContent = storageContent
        } else {
            LootDropConfigurator.badLootContent = storageContent
        }

        it.player.sendText {
            appendPrefix()

            success("Der ")
            if (goodLoot) {
                variableValue("gute")
            } else {
                variableValue("schlechte")
            }
            success(" LootDrop Inhalt wurde aktualisiert.")
        }
    }

}