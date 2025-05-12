@file:Suppress("UNCHECKED_CAST")

package de.castcrafter.lootdrop.locator

import de.castcrafter.lootdrop.locator.locators.LocationLocator
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import org.bukkit.Location
import kotlin.reflect.KClass

object Locators {

    val LOCATOR_RESULT_DISPLAY = Object2ObjectOpenHashMap<LocatorResult, String>()
    val locators = Object2ObjectOpenHashMap<KClass<*>, Locator<*>>()

    init {
        // Result Displays
        LOCATOR_RESULT_DISPLAY[LocatorResult.NONE] = "☑"
        LOCATOR_RESULT_DISPLAY[LocatorResult.WORLD] = "☢"

        LOCATOR_RESULT_DISPLAY[LocatorResult.UP] = "↑"
        LOCATOR_RESULT_DISPLAY[LocatorResult.DOWN] = "↓"

        LOCATOR_RESULT_DISPLAY[LocatorResult.RIGHT] = "→"
        LOCATOR_RESULT_DISPLAY[LocatorResult.FRONT_RIGHT] = "↗"
        LOCATOR_RESULT_DISPLAY[LocatorResult.FRONT] = "↑"
        LOCATOR_RESULT_DISPLAY[LocatorResult.FRONT_LEFT] = "↖"
        LOCATOR_RESULT_DISPLAY[LocatorResult.LEFT] = "←"
        LOCATOR_RESULT_DISPLAY[LocatorResult.BACK_LEFT] = "↙"
        LOCATOR_RESULT_DISPLAY[LocatorResult.BACK] = "↓"
        LOCATOR_RESULT_DISPLAY[LocatorResult.BACK_RIGHT] = "↘"

        // Locators
        locators[Location::class] = LocationLocator()
    }

    inline fun <reified T> getLocator(): Locator<T>? =
        locators[T::class]?.let { locator -> locator as Locator<T> }
}
