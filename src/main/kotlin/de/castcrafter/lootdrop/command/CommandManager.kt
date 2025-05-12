package de.castcrafter.lootdrop.command

import de.castcrafter.lootdrop.command.commands.loot.lootDropCommand

object CommandManager {
    fun registerCommands() {
//        eventCommand()
//        liegeWieCommand()
//        makeSpecialCommand()
//        renameCommand()
        lootDropCommand()
    }
}
