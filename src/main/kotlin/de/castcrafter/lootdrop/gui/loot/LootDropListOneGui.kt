package de.castcrafter.lootdrop.gui.loot

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import com.github.shynixn.mccoroutine.folia.ticks
import de.castcrafter.lootdrop.gui.*
import de.castcrafter.lootdrop.loot.LootDrop
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.childPlayerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestSinglePlayerGui
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import org.bukkit.entity.Player

fun SurfChestSinglePlayerGui.lootDropListOneGui(player: Player, lootDrop: LootDrop) =
    childPlayerMenu(title4Gui, 4) {
        staticPane(slot(0, 3), 1) {
            item(slot(4, 0), backItem) {
                click = {
                    playClickSound()
                    whoClicked.backToParent()
                }
            }
        }

        staticPane(slot(0, 0), 3) {
            item(slot(1, 1), goodLootItem) {
                click = {
                    playClickSound()
                    lootDropContentGui(goodLoot = true, editable = false).open()
                }
            }

            item(slot(2, 1), badLootItem) {
                click = {
                    playClickSound()
                    lootDropContentGui(goodLoot = false, editable = false).open()
                }
            }

            item(slot(3, 1), goodLootProbabilityItem) {
                click = {
                    playClickSound()
                    lootDropProbabilityGui(player, false).open()
                }
            }

            item(slot(5, 1), deleteLootDropItem) {
                click = {
                    playClickSound()
                    lootDrop.despawn()

                    plugin.launch(plugin.entityDispatcher(whoClicked)) {
                        delay(1.ticks)
                        whoClicked.closeInventory()
                    }

                    player.sendText {
                        appendPrefix()

                        success("Der LootDrop wurde gelöscht.")
                    }
                }
            }

            item(slot(7, 1), teleportLootDropItem) {
                click = {
                    playClickSound()

                    plugin.launch {
                        plugin.launch(plugin.entityDispatcher(whoClicked)) {
                            delay(1.ticks)
                            whoClicked.closeInventory()
                        }

                        player.teleportAsync(lootDrop.currentLocation).await()

                        player.sendText {
                            appendPrefix()

                            success("Du wurdest zum LootDrop teleportiert.")
                        }
                    }
                }
            }
        }
    }