package de.castcrafter.lootdrop.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import kotlin.time.Duration

object TimeUtils {

    fun formatDuration(
        duration: Duration,
        timeColor: TextColor = NamedTextColor.YELLOW,
        spacerColor: TextColor = NamedTextColor.YELLOW
    ): Component {
        val seconds = duration.inWholeSeconds
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        val format = "%02d:%02d"
        val replaced = String.format(format, minutes, remainingSeconds)

        var component: Component = Component.text(replaced, timeColor)
        
        component = component.replaceText(
            TextReplacementConfig.builder().match(":")
                .replacement(Component.text(":").color(spacerColor)).build()
        )

        return component.color(timeColor)
    }
}
