package de.castcrafter.lootdrop.gui.loot

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui
import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.drawOutlineRow
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

fun lootDropProbabilityGui(player: Player) =
    playerMenu(text("LootDrop Wahrscheinlichtkeit"), player, 5) {
        drawOutlineRow(0)
        drawOutlineRow(4)

        staticPane(slot(0, 1), 3) {
            var guiItemStack: GuiItem? = null

            item(slot(2, 1), ItemStack(Material.RED_DYE) {
                displayName {
                    primary("-10")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Verringerung der Wahrscheinlichkeit")
                    }
                    line {
                        spacer("eines positiven LootDrops um 10")
                    }
                }
            }) {
                click = {
                    LootDropConfigurator.goodChance -= 10

                    player.playSound {
                        type(Sound.UI_BUTTON_CLICK)
                        volume(.5f)
                    }

                    updateProbabilityDisplay(guiItemStack)
                }
            }

            item(slot(3, 1), ItemStack(Material.RED_DYE) {
                displayName {
                    primary("-1")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Verringerung der Wahrscheinlichkeit")
                    }
                    line {
                        spacer("eines positiven LootDrops um 1")
                    }
                }
            }) {
                click = {
                    LootDropConfigurator.goodChance -= 1

                    player.playSound {
                        type(Sound.UI_BUTTON_CLICK)
                        volume(.5f)
                    }

                    updateProbabilityDisplay(guiItemStack)
                }
            }

            item(slot(4, 1), ItemStack(Material.NAME_TAG) {
                displayName {
                    primary(LootDropConfigurator.goodChance)
                }
            }) {
                guiItemStack = this
            }

            item(slot(5, 1), ItemStack(Material.GREEN_DYE) {
                displayName {
                    primary("+1")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Erhöhung der Wahrscheinlichkeit")
                    }
                    line {
                        spacer("eines positiven LootDrops um 1")
                    }
                }
            }) {
                click = {
                    LootDropConfigurator.goodChance += 1

                    player.playSound {
                        type(Sound.UI_BUTTON_CLICK)
                        volume(.5f)
                    }

                    updateProbabilityDisplay(guiItemStack)
                }
            }

            item(slot(6, 1), ItemStack(Material.GREEN_DYE) {
                displayName {
                    primary("+10")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Erhöhung der Wahrscheinlichkeit")
                    }
                    line {
                        spacer("eines positiven LootDrops um 10")
                    }
                }
            }) {
                click = {
                    LootDropConfigurator.goodChance += 10

                    player.playSound {
                        type(Sound.UI_BUTTON_CLICK)
                        volume(.5f)
                    }

                    updateProbabilityDisplay(guiItemStack)
                }
            }
        }
    }

private fun ChestGui.updateProbabilityDisplay(item: GuiItem?) {
    item?.item?.editMeta {
        it.displayName(buildText {
            primary(LootDropConfigurator.goodChance)
        })
    }

    update()
}