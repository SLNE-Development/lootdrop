package de.castcrafter.lootdrop.locator.locators

import de.castcrafter.lootdrop.locator.Locator
import de.castcrafter.lootdrop.locator.LocatorResult
import it.unimi.dsi.fastutil.Pair
import org.bukkit.Location
import kotlin.math.abs

/**
 * A locator for locations
 */
class LocationLocator : Locator<Location> {

    override fun locate(
        startingLocation: Location,
        toLocate: Location
    ): Pair<LocatorResult, LocatorResult> {
        if (startingLocation.world !== toLocate.world) {
            return Pair.of(LocatorResult.WORLD, LocatorResult.WORLD)
        }

        val yDiff = startingLocation.blockY - toLocate.blockY
        
        val yResult = when (yDiff.compareTo(0)) {
            -1 -> LocatorResult.UP
            1 -> LocatorResult.DOWN
            else -> LocatorResult.NONE
        }

        val directionResult = determineDirection(startingLocation, toLocate)

        return Pair.of(directionResult, yResult)
    }

    private fun determineDirection(
        playerLocation: Location,
        targetLocation: Location
    ): LocatorResult {
        if (playerLocation.world !== targetLocation.world) {
            return LocatorResult.WORLD
        }

        // Falls sich das Ziel genau an der gleichen X/Z-Position befindet
        if (playerLocation.blockX == targetLocation.blockX
            && playerLocation.blockZ == targetLocation.blockZ
        ) {
            return LocatorResult.NONE
        }

        // Positionsdifferenz berechnen
        val diffX = targetLocation.x - playerLocation.x
        val diffZ = targetLocation.z - playerLocation.z

        // Spielerblickrichtung holen
        val direction = playerLocation.direction
        val forwardX = direction.x
        val forwardZ = direction.z

        // Skalarprodukte berechnen
        val forwardDot = (diffX * forwardX) + (diffZ * forwardZ)
        val rightDot = (diffX * -forwardZ) + (diffZ * forwardX) // Rechts-Vektor (-Z, X)

        // Richtung bestimmen
        val isForward = forwardDot > 0
        val isRight = rightDot > 0

        return if (abs(forwardDot) > abs(rightDot)) {
            // Ziel liegt stärker in Blickrichtung als seitlich
            if (abs(rightDot) > 0.5 * abs(forwardDot)) {
                if (isForward)
                    (if (isRight) LocatorResult.FRONT_RIGHT else LocatorResult.FRONT_LEFT)
                else
                    (if (isRight) LocatorResult.BACK_RIGHT else LocatorResult.BACK_LEFT)
            } else {
                if (isForward) LocatorResult.FRONT else LocatorResult.BACK
            }
        } else {
            // Ziel liegt stärker seitlich als in Blickrichtung
            if (abs(forwardDot) > 0.5 * abs(rightDot)) {
                if (isRight)
                    (if (isForward) LocatorResult.FRONT_RIGHT else LocatorResult.BACK_RIGHT)
                else
                    (if (isForward) LocatorResult.FRONT_LEFT else LocatorResult.BACK_LEFT)
            } else {
                if (isRight) LocatorResult.RIGHT else LocatorResult.LEFT
            }
        }
    }
}
