package de.castcrafter.lootdrop.loot.action

import de.castcrafter.lootdrop.loot.action.actions.TntLootAction
import dev.slne.surf.surfapi.core.api.util.toObjectList
import org.bukkit.Material

enum class LootActions(val action: LootAction) {
    TNT(TntLootAction);

    companion object {
        fun fromMaterial(material: Material) =
            entries.filter { it.action.executingMaterial == material }.toObjectList()
    }
}
