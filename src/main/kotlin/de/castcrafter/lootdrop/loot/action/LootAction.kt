package de.castcrafter.lootdrop.loot.action

import org.bukkit.Location
import org.bukkit.Material

abstract class LootAction(val executingMaterial: Material) {

    fun performActionInternal(initialLocation: Location, targetLocation: Location, amount: Int) {
        for (i in 0..<amount) {
            performAction(initialLocation, targetLocation)
        }
    }

    abstract fun performAction(initialLocation: Location, targetLocation: Location)
}
