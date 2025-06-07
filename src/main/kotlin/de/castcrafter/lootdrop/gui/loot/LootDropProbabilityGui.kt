package de.castcrafter.lootdrop.gui.loot

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui
import de.castcrafter.lootdrop.gui.*
import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.childPlayerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

fun SurfChestSinglePlayerGui.lootDropProbabilityGui(player: Player, editable: Boolean = true) =
    childPlayerMenu(title3Gui, 3) {
        staticPane(slot(0, 0), 3) {
            var guiItemStack: GuiItem? = null

            item(slot(2, 1), minus10ProbabilityItem) {
                click = {
                    if (editable) {
                        LootDropConfigurator.goodChance -= 10

                        playClickSound()
                        updateProbabilityDisplay(guiItemStack)
                    }
                }
            }

            item(slot(3, 1), minus1ProbabilityItem) {
                click = {
                    if (editable) {
                        LootDropConfigurator.goodChance -= 1

                        playClickSound()
                        updateProbabilityDisplay(guiItemStack)
                    }
                }
            }

            item(slot(4, 1), buildLootProbabilityItem(LootDropConfigurator.goodChance)) {
                guiItemStack = this
                click = {
                    playClickSound()
                    whoClicked.backToParent()
                }
            }

            item(slot(5, 1), plus1ProbabilityItem) {
                click = {
                    if (editable) {
                        LootDropConfigurator.goodChance += 1

                        playClickSound()
                        updateProbabilityDisplay(guiItemStack)
                    }
                }
            }

            item(slot(6, 1), plus10ProbabilityItem) {
                click = {
                    if (editable) {
                        LootDropConfigurator.goodChance += 10

                        playClickSound()
                        updateProbabilityDisplay(guiItemStack)
                    }
                }
            }
        }
    }

private fun ChestGui.updateProbabilityDisplay(item: GuiItem?) {
    item?.item?.editMeta {
        it.displayName(buildText {
            primary("${LootDropConfigurator.goodChance}%")
            decoration(TextDecoration.ITALIC, false)
        })
    }

    update()
}