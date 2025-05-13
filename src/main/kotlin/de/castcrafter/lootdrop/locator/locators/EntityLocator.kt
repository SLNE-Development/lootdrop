package de.castcrafter.lootdrop.locator.locators

import de.castcrafter.lootdrop.locator.Locator
import de.castcrafter.lootdrop.locator.LocatorResult
import de.castcrafter.lootdrop.locator.Locators
import it.unimi.dsi.fastutil.Pair
import org.bukkit.Location
import org.bukkit.entity.Entity

/**
 * A locator for entities
 */
class EntityLocator : Locator<Entity> {
    override fun locate(
        startingLocation: Location,
        toLocate: Entity
    ): Pair<LocatorResult, LocatorResult> = Locators.getLocator<Location>()?.locate(
        startingLocation,
        toLocate.location
    ) ?: Pair.of(LocatorResult.NONE, LocatorResult.NONE)
}
