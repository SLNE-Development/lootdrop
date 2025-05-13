package de.castcrafter.lootdrop.command

import de.castcrafter.lootdrop.command.commands.event.eventCommand
import de.castcrafter.lootdrop.command.commands.liegeWieCommand
import de.castcrafter.lootdrop.command.commands.loot.lootDropCommand
import de.castcrafter.lootdrop.command.commands.makeSpecialCommand
import de.castcrafter.lootdrop.command.commands.renameCommand

object CommandManager {
    fun registerCommands() {
        eventCommand()
        liegeWieCommand()
        makeSpecialCommand()
        renameCommand()
        lootDropCommand()
    }
}
