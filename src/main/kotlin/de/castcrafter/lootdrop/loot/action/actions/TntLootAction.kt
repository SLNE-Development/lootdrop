package de.castcrafter.lootdrop.loot.action.actions

import de.castcrafter.lootdrop.loot.action.LootAction
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.TNTPrimed

object TntLootAction : LootAction(Material.TNT) {
    override fun performAction(initialLocation: Location, targetLocation: Location) {
        targetLocation.world.spawn(targetLocation, TNTPrimed::class.java) {
            it.fuseTicks = 0
        }
    }
}
