package de.castcrafter.lootdrop.gui.loot

import com.github.shynixn.mccoroutine.folia.launch
import de.castcrafter.lootdrop.loot.LootDrop
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.drawOutlineRow
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import kotlinx.coroutines.future.await
import org.bukkit.Material
import org.bukkit.entity.Player

fun lootDropListOneGui(player: Player, lootDrop: LootDrop) =
    playerMenu(text(lootDrop.uniqueId.toString()), player, 5) {
        drawOutlineRow(0)
        drawOutlineRow(4)

        staticPane(slot(0, 1), 3) {
            item(slot(1, 1), ItemStack(Material.CHEST) {
                displayName {
                    primary("Guter Loot")
                }

                buildLore {
                    line { }
                    line {
                        spacer("Hier kann der gute Loot")
                    }
                    line {
                        spacer("für den LootDrop eingesehen werden")
                    }
                }
            }) {
                click = {
                    lootDropContentGui(true, player, false).open()
                }
            }

            item(slot(2, 1), ItemStack(Material.TRAPPED_CHEST) {
                displayName {
                    primary("Schlechter Loot")
                }

                buildLore {
                    line { }
                    line {
                        spacer("Hier kann der schlechte Loot")
                    }
                    line {
                        spacer("für den LootDrop eingesehen werden")
                    }
                }
            }) {
                click = {
                    lootDropContentGui(false, player, false).open()
                }
            }

            item(slot(3, 1), ItemStack(Material.NAME_TAG) {
                displayName {
                    primary("LootDrop Wahrscheinlichkeit")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Hier kann die Wahrscheinlichkeit")
                    }
                    line {
                        spacer("für einen guten LootDrop")
                    }
                    line {
                        spacer("eingesehen werden")
                    }
                }
            }) {
                click = {
                    lootDropProbabilityGui(player).open()
                }
            }

            item(slot(5, 1), ItemStack(Material.BARRIER) {
                displayName {
                    primary("Löschen")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Hier kann der LootDrop")
                    }
                    line {
                        spacer("gelöscht werden")
                    }
                }
            }) {
                click = {
                    lootDrop.despawn()

                    player.sendText {
                        appendPrefix()

                        success("Der LootDrop wurde gelöscht.")
                    }
                }
            }

            item(slot(7, 1), ItemStack(Material.ENDER_PEARL) {
                displayName {
                    primary("Teleportieren")
                }
                buildLore {
                    line { }
                    line {
                        spacer("Hier kannst du dich zum LootDrop teleportieren")
                    }
                }
            }) {
                click = {
                    plugin.launch {
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