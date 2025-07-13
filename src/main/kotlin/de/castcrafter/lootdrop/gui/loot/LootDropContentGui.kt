package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.dialog.badLootChangedNotice
import de.castcrafter.lootdrop.dialog.goodLootChangedNotice
import de.castcrafter.lootdrop.dialog.sub.lootDropOneDialog
import de.castcrafter.lootdrop.gui.backItem
import de.castcrafter.lootdrop.gui.edgeKey
import de.castcrafter.lootdrop.gui.playClickSound
import de.castcrafter.lootdrop.gui.title4Gui
import de.castcrafter.lootdrop.loot.LootDrop
import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import dev.slne.surf.surfapi.core.api.util.toObjectList
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun lootDropContentGui(
    player: Player,
    lootDrop: LootDrop?,
    goodLoot: Boolean,
    editable: Boolean
): SurfChestSinglePlayerGui = playerMenu(title4Gui, player, 4) {
    val lootContent = mutableObjectListOf<ItemStack>()

    if (lootDrop != null) {
        if (goodLoot) {
            lootContent.addAll(lootDrop.goodLootContent)
        } else {
            lootContent.addAll(lootDrop.badLootContent)
        }
    } else {
        if (goodLoot) {
            lootContent.addAll(LootDropConfigurator.goodLootContent)
        } else {
            lootContent.addAll(LootDropConfigurator.badLootContent)
        }
    }

    this.setOnBottomClick { event -> event.isCancelled = !editable }
    this.setOnBottomDrag { event -> event.isCancelled = !editable }
    this.setOnTopClick { event -> event.isCancelled = !editable }
    this.setOnTopDrag { event -> event.isCancelled = !editable }

    staticPane(slot(0, 3), 1) {
        item(slot(4, 0), backItem) {
            click = {
                playClickSound()
                isCancelled = true

                modifyLoot(
                    editable,
                    this.inventory,
                    goodLoot,
                    this.whoClicked as Player,
                    lootDrop
                )
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
        modifyLoot(
            editable,
            it.inventory,
            goodLoot,
            it.player as Player,
            lootDrop
        )
    }
}


private fun modifyLoot(
    editable: Boolean,
    inventory: Inventory,
    goodLoot: Boolean,
    player: Player,
    lootDrop: LootDrop?
) {
    if (editable) {
        val storageContent = inventory.storageContents.filterNotNull().filterNot { item ->
            item.persistentDataContainer.has(edgeKey)
        }.toObjectList()

        if (goodLoot) {
            LootDropConfigurator.goodLootContent = storageContent
        } else {
            LootDropConfigurator.badLootContent = storageContent
        }

        if (goodLoot) {
            player.showDialog(goodLootChangedNotice())
        } else {
            player.showDialog(badLootChangedNotice())
        }
    } else {
        if (lootDrop != null) {
            player.showDialog(lootDropOneDialog(lootDrop))
        } else {
            player.sendText {
                appendPrefix()
                error("Der LootDrop konnte nicht gefunden werden.")
            }
        }
    }
}