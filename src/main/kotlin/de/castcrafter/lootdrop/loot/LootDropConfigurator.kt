package de.castcrafter.lootdrop.loot

import dev.slne.surf.surfapi.core.api.util.objectListOf
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.inventory.ItemStack

object LootDropConfigurator {

    var goodChance: Int = 50
        set(value) {
            field = when {
                value in 1..100 -> value
                value < 1 -> 1
                else -> 100
            }
        }
    var goodLootContent: ObjectList<ItemStack> = objectListOf()
    var badLootContent: ObjectList<ItemStack> = objectListOf()

}