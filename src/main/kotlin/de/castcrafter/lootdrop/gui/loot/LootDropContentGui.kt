package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import dev.slne.surf.surfapi.core.api.util.toObjectList
import org.bukkit.entity.Player

fun lootDropContentGui(goodLoot: Boolean, player: Player) =
    playerMenu(text("LootDrop Content - ${if (goodLoot) "Gut" else "Schlecht"}"), player) {
        this.setOnBottomClick { event -> event.isCancelled = false }
        this.setOnBottomDrag { event -> event.isCancelled = false }
        this.setOnTopClick { event -> event.isCancelled = false }
        this.setOnTopDrag { event -> event.isCancelled = false }

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