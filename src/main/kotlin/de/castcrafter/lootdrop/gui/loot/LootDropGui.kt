package de.castcrafter.lootdrop.gui.loot

import de.castcrafter.lootdrop.loot.LootDrop
import dev.slne.surf.bitmap.bitmaps.Bitmaps
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.drawOutlineRow
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.playerMenu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.slot
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player

fun lootDropGui(player: Player) = playerMenu(
    text(
        Bitmaps.CLAN_CLOUDSHIFT.provider.translateToString("LootDrop"),
        NamedTextColor.WHITE
    ),
    player,
    5
) {
    drawOutlineRow(0)
    drawOutlineRow(4)

    staticPane(slot(0, 1), 3, 9) {
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
                    spacer("für den LootDrop konfiguriert werden")
                }
            }
        }) {
            click = {
                lootDropContentGui(true).open()
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
                    spacer("für den LootDrop konfiguriert werden")
                }
            }
        }) {
            click = {
                lootDropContentGui(false).open()
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
                    spacer("konfiguriert werden")
                }
            }
        }) {
            click = {
                lootDropProbabilityGui(player).open()
            }
        }

        item(slot(6, 1), ItemStack(Material.BARREL) {
            displayName {
                primary("LootDrop Liste")
            }
            buildLore {
                line { }
                line {
                    spacer("Hier kann eine Liste")
                }
                line {
                    spacer("von LootDrops eingesehen werden")
                }
            }
        }) {
            click = {
                lootDropListGui(player).open()
            }
        }

        item(slot(7, 1), ItemStack(Material.SPAWNER) {
            displayName {
                primary("LootDrop erstellen")
            }

            buildLore {
                line { }
                line {
                    spacer("Hier kann ein LootDrop erstellt werden")
                }
            }
        }) {
            click = {
                LootDrop(player).spawn()
            }
        }
    }
}