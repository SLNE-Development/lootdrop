package de.castcrafter.lootdrop.loot

import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf

object LootDropManager {

    val lootdrops = mutableObjectSetOf<LootDrop>()

    fun cleanupLootDrops(): Int {
        val size = lootdrops.size

        lootdrops.clone().forEach {
            it.despawn()
        }

        return size
    }

}