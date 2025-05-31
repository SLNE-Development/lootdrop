package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.loot.LootDropConfigurator
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.bitmap.bitmaps.Bitmaps
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.childPlayerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.drawOutlineRow
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import dev.slne.surf.surfapi.core.api.util.toObjectList
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

fun SurfChestSinglePlayerGui.lootDropContentGui(
    goodLoot: Boolean,
    editable: Boolean = true
) = childPlayerMenu(
    text(
        Bitmaps.CLAN_CLOUDSHIFT.provider.translateToString("LootDrop\t\tInhalt\t\t-\t\t${if (goodLoot) "Gut" else "Schlecht"}"),
        NamedTextColor.WHITE
    ),
    5
) {
    val edgeKey = NamespacedKey(plugin, "edge")

    this.setOnBottomClick { event -> event.isCancelled = !editable }
    this.setOnBottomDrag { event -> event.isCancelled = !editable }
    this.setOnTopClick { event -> event.isCancelled = !editable }
    this.setOnTopDrag { event -> event.isCancelled = !editable }
    val lootContent = if (goodLoot) {
        LootDropConfigurator.goodLootContent
    } else {
        LootDropConfigurator.badLootContent
    }

    drawOutlineRow(0).items.forEach {
        it.item.editPersistentDataContainer { container ->
            container.set(
                edgeKey,
                PersistentDataType.BOOLEAN, true
            )
        }
    }
    drawOutlineRow(4).items.forEach {
        it.item.editPersistentDataContainer { container ->
            container.set(
                edgeKey,
                PersistentDataType.BOOLEAN, true
            )
        }
    }

    staticPane(slot(0, 4), 1) {
        item(slot(4, 0), ItemStack(Material.ARROW) {
            displayName {
                primary("Zurück")
            }

            editPersistentDataContainer {
                it.set(edgeKey, PersistentDataType.BOOLEAN, true)
            }
        }) {
            click = {
                isCancelled = true
                whoClicked.backToParent()
            }
        }
    }

    staticPane(slot(0, 1), 3) {
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