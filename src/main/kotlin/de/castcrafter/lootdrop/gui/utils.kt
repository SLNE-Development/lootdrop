package de.castcrafter.lootdrop.gui

import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType

val title4Gui = text("<shift:-48>뉅", NamedTextColor.WHITE)

val edgeKey = NamespacedKey(plugin, "edge")

fun InventoryClickEvent.playClickSound() {
    whoClicked.playSound {
        type(Sound.UI_BUTTON_CLICK)
        volume(.5f)
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