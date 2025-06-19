package de.castcrafter.lootdrop.gui

import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType

val title3Gui = text("<shift:-48>뉆", NamedTextColor.WHITE)
val title4Gui = text("<shift:-48>뉅", NamedTextColor.WHITE)

val edgeKey = NamespacedKey(plugin, "edge")

fun buildLootDropItem(name: String) = ItemStack(Material.BRICK) {
    displayName {
        primary(name)
    }

    editPersistentDataContainer { pdc ->
        pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
    }

    editMeta { meta ->
        meta.setCustomModelData(1014)
    }
}

fun InventoryClickEvent.playClickSound() {
    whoClicked.playSound {
        type(Sound.UI_BUTTON_CLICK)
        volume(.5f)
    }
}

val cleanupLootDropsItem by lazy {
    ItemStack(Material.BRICK) {
        displayName {
            primary("LootDrops aufräumen")
        }

        buildLore {
            line { }
            line {
                spacer("Hier kannst du LootDrops aufräumen")
            }
            line {
                spacer("So werden alle LootDrops gelöscht,")
            }
            line {
                spacer("die noch nicht abgeholt wurden")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1021)
        }
    }
}

val emptyLootDropItem by lazy {
    ItemStack(Material.BRICK) {
        displayName {
            primary("Keine LootDrops")
        }

        buildLore {
            line { }
            line {
                spacer("Es gibt aktuell keine LootDrops")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1024)
        }
    }
}

val listLootDropItem by lazy {
    ItemStack(Material.BRICK) {
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

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1025)
        }
    }
}

private fun buildProbabilityItemModifier(
    amount: Int,
    cmd: Int,
    fallback: Material = Material.BRICK,
) = ItemStack(fallback) {
    displayName {
        primary(amount)
    }

    buildLore {
        line { }
        line {
            spacer("Hier kannst du die Wahrscheinlichkeit")
        }
        line {
            spacer("für einen LootDrop um $amount ändern")
        }
    }

    editPersistentDataContainer { pdc ->
        pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
    }

    editMeta { meta ->
        meta.setCustomModelData(cmd)
    }
}

val minus10ProbabilityItem by lazy {
    buildProbabilityItemModifier(amount = -10, cmd = 1021)
}

val minus1ProbabilityItem by lazy {
    buildProbabilityItemModifier(amount = -1, cmd = 1020)
}

val plus1ProbabilityItem by lazy {
    buildProbabilityItemModifier(amount = 1, cmd = 1018)
}

val plus10ProbabilityItem by lazy {
    buildProbabilityItemModifier(amount = 10, cmd = 1019)
}

val goodLootItem by lazy {
    ItemStack(Material.BRICK) {
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

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1018)
        }
    }
}

val badLootItem by lazy {
    ItemStack(Material.BRICK) {
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

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1020)
        }
    }
}


fun buildLootProbabilityItem(chance: Int) = ItemStack(Material.BRICK) {
    displayName {
        primary("$chance%")
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

    editPersistentDataContainer { pdc ->
        pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
    }

    editMeta { meta ->
        meta.setCustomModelData(1026)
    }
}

val goodLootProbabilityItem by lazy {
    ItemStack(Material.BRICK) {
        displayName {
            primary("Wahrscheinlichkeit")
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

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1026)
        }
    }
}

val teleportLootDropItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("Teleportieren")
            decoration(TextDecoration.ITALIC, false)
        })

        buildLore {
            line { }
            line {
                spacer("Hier kannst du dich zum LootDrop")
            }
            line {
                spacer("teleportieren")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1022)
        }
    }
}

val deleteLootDropItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("Löschen")
        })

        buildLore {
            line { }
            line {
                spacer("Hier kann der LootDrop")
            }
            line {
                spacer("gelöscht werden")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1021)
        }
    }
}

val backItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("Zurück")
        })

        buildLore {
            line { }
            line {
                spacer("Hier kannst du zum vorherigen Menü")
            }
            line {
                spacer("zurückkehren")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1017)
        }
    }
}

val spawnLootDropItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("LootDrop erstellen")
        })

        buildLore {
            line { }
            line {
                spacer("Hier kann ein LootDrop erstellt werden")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1023)
        }
    }
}

val pageForwardItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("Nächste Seite")
        })

        buildLore {
            line { }
            line {
                spacer("Hier kannst du zur nächsten Seite")
            }
            line {
                spacer("der LootDrops wechseln")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1016)
        }
    }
}

val pageBackwardItem by lazy {
    ItemStack(Material.BRICK) {
        displayName(buildText {
            primary("Vorherige Seite")
        })

        buildLore {
            line { }
            line {
                spacer("Hier kannst du zur vorherigen Seite")
            }
            line {
                spacer("der LootDrops wechseln")
            }
        }

        editPersistentDataContainer { pdc ->
            pdc.set(edgeKey, PersistentDataType.BOOLEAN, true)
        }

        editMeta { meta ->
            meta.setCustomModelData(1015)
        }
    }
}