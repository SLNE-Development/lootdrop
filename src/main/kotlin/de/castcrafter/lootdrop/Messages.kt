package de.castcrafter.lootdrop

import dev.slne.surf.surfapi.core.api.messages.adventure.buildText

object Messages {

    fun noEventStartedComponent() = buildText {
        appendPrefix()

        error("Derzeit findet kein Event statt")
    }

    fun noEventCreatedComponent() = buildText {
        appendPrefix()

        error("Es wurde noch kein Event erstellt")
    }
}
