package de.castcrafter.lootdrop.gui

import com.nexomc.nexo.api.NexoItems
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

val title3Gui = text("<shift:-48>뉆", NamedTextColor.WHITE)
val title4Gui = text("<shift:-48>뉅", NamedTextColor.WHITE)

val edgeKey = NamespacedKey(plugin, "edge")

fun buildLootDropItem(name: String): ItemStack {
    val item = NexoItems.itemFromId("lootdrop_item")?.build() ?: ItemStack(Material.BARREL)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary(name)
            decoration(TextDecoration.ITALIC, false)
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    return item
}

fun InventoryClickEvent.playClickSound() {
    whoClicked.playSound {
        type(Sound.UI_BUTTON_CLICK)
        volume(.5f)
    }
}

fun ItemMeta.editPersistentDataContainer(block: PersistentDataContainer.() -> Unit): ItemMeta {
    block(persistentDataContainer)
    return this
}

val emptyLootDropItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_cross")?.build() ?: ItemStack(Material.BARRIER)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Keine LootDrops")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Es gibt aktuell keine LootDrops")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val listLootDropItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_menu")?.build() ?: ItemStack(Material.BOOK)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("LootDrop Liste")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann eine Liste")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("von LootDrops eingesehen werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val minus10ProbabilityItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_minus_red")?.build() ?: ItemStack(Material.RED_DYE)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("-10")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Verringerung der Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eines positiven LootDrops um 10")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val minus1ProbabilityItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_minus")?.build() ?: ItemStack(Material.RED_DYE)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("-1")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Verringerung der Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eines positiven LootDrops um 1")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val plus1ProbabilityItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_plus")?.build() ?: ItemStack(Material.GREEN_DYE)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("+1")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Erhöhung der Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eines positiven LootDrops um 1")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val plus10ProbabilityItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_plus_green")?.build() ?: ItemStack(Material.GREEN_DYE)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("+10")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Erhöhung der Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eines positiven LootDrops um 10")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val goodLootItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_plus")?.build() ?: ItemStack(Material.CHEST)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Guter Loot")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann der gute Loot")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("für den LootDrop konfiguriert werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val badLootItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_minus")?.build() ?: ItemStack(Material.TRAPPED_CHEST)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Schlechter Loot")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann der schlechte Loot")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("für den LootDrop konfiguriert werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

fun buildLootProbabilityItem(chance: Int): ItemStack {
    val item = NexoItems.itemFromId("lootdrop_question")?.build() ?: ItemStack(Material.NAME_TAG)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("$chance%")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann die Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("für einen guten LootDrop")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eingesehen werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    return item
}

val goodLootProbabilityItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_question")?.build() ?: ItemStack(Material.NAME_TAG)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Wahrscheinlichkeit")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann die Wahrscheinlichkeit")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("für einen guten LootDrop")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("eingesehen werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val teleportLootDropItem by lazy {
    val item =
        NexoItems.itemFromId("lootdrop_circle")?.build() ?: ItemStack(Material.ENDER_PEARL)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Teleportieren")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kannst du dich zum LootDrop")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("teleportieren")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val deleteLootDropItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_minus_red")?.build() ?: ItemStack(Material.BARRIER)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Löschen")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann der LootDrop")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("gelöscht werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val backItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_back")?.build() ?: ItemStack(Material.BARRIER)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Zurück")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kannst du zum vorherigen Menü")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("zurückkehren")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val spawnLootDropItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_plus_green")?.build() ?: ItemStack(Material.SPAWNER)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("LootDrop erstellen")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kann ein LootDrop erstellt werden")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val pageForwardItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_page_forward")?.build() ?: ItemStack(Material.ARROW)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Nächste Seite")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kannst du zur nächsten Seite")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("der LootDrops wechseln")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}

val pageBackwardItem by lazy {
    val item = NexoItems.itemFromId("lootdrop_page_backward")?.build() ?: ItemStack(Material.ARROW)

    item.editMeta { meta ->
        meta.displayName(buildText {
            primary("Vorherige Seite")
            decoration(TextDecoration.ITALIC, false)
        })

        meta.lore(buildList {
            add(text(""))
            add(buildText {
                spacer("Hier kannst du zur vorherigen Seite")
                decoration(TextDecoration.ITALIC, false)
            })
            add(buildText {
                spacer("der LootDrops wechseln")
                decoration(TextDecoration.ITALIC, false)
            })
        })

        meta.editPersistentDataContainer {
            set(edgeKey, PersistentDataType.BOOLEAN, true)
        }
    }

    item
}