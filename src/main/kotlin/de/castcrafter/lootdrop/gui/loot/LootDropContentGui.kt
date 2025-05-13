package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import dev.slne.surf.surfapi.core.api.util.toObjectList
import org.bukkit.entity.Player

fun lootDropContentGui(goodLoot: Boolean, player: Player, editable: Boolean = true) =
    playerMenu(text("LootDrop Content - ${if (goodLoot) "Gut" else "Schlecht"}"), player) {
        this.setOnBottomClick { event -> event.isCancelled = !editable }
        this.setOnBottomDrag { event -> event.isCancelled = !editable }
        this.setOnTopClick { event -> event.isCancelled = !editable }
        this.setOnTopDrag { event -> event.isCancelled = !editable }

        setOnClose {
            val storageContent = inventory.storageContents.mapNotNull { it }.toObjectList()

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

        val lootContent = if (goodLoot) {
            LootDropConfigurator.goodLootContent
        } else {
            LootDropConfigurator.badLootContent
        }

        staticPane(slot(0, 0), 6) {
            if (lootContent.size > this@staticPane.length) {
                error("$lootContent more than ${inventory.size} loot.")
            }

            lootContent.forEachIndexed { index, itemStack ->
                item(slot(index % 9, index / 9), itemStack) {}
            }
        }
    }