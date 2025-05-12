package de.castcrafter.lootdrop.locator

import it.unimi.dsi.fastutil.Pair
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object LocatorListener : Listener {

    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        val startingLocation = player.eyeLocation

        val toLocate = player.location
        // FIXME: 12.05.2025 14:32 Change back to resolving the class of toLocate::class? Ask twisti how to do this
        val locator = Locators.getLocator<Location>() ?: return

        val result = locator.locate(
            startingLocation,
            toLocate
        ) ?: Pair.of(LocatorResult.NONE, LocatorResult.NONE)

        val direction = result!!.left()
        val height = result.right()

        val directionDisplay = Locators.LOCATOR_RESULT_DISPLAY[direction]
        val heightDisplay = Locators.LOCATOR_RESULT_DISPLAY[height]

        val color = TextColor.fromHexString("#00FF40")

        val actionBar = Component.text()
        actionBar.append(Component.text("ғɪɴᴅᴇ ʟᴀʀʀʏ", color, TextDecoration.BOLD))
        actionBar.append(Component.text(" | ", NamedTextColor.GRAY))
        actionBar.append(Component.text(directionDisplay!!, color))
        actionBar.append(Component.text(" | ", NamedTextColor.GRAY))
        actionBar.append(Component.text(heightDisplay!!, color))

        player.sendActionBar(actionBar.build())
    }
}