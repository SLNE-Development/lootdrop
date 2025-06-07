package de.castcrafter.lootdrop.command.commands.event

import de.castcrafter.lootdrop.command.commands.event.bridge.bridgeEvent
import de.castcrafter.lootdrop.command.commands.event.mines.minesCommand
import de.castcrafter.lootdrop.command.commands.event.subevent.subEventCommand
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandAPICommand

fun eventCommand() = commandAPICommand("event") {
    withPermission(PermissionRegistry.EVENT_COMMAND)

    minesCommand()
    subEventCommand()
    bridgeEvent()
}