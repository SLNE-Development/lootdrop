@file:Suppress("UnstableApiUsage")

package de.castcrafter.lootdrop.dialog.sub

import de.castcrafter.lootdrop.dialog.lootDropDialog
import de.castcrafter.lootdrop.loot.LootDropManager
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.builder.actionButton
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase

fun listLootDropDialog() = dialog {
    base {
        title { primary("LootDrop Liste") }
        afterAction(DialogBase.DialogAfterAction.NONE)

        if (LootDropManager.lootdrops.isEmpty()) {
            body {
                plainMessage(400) {
                    info("Aktuell gibt es keine LootDrops")
                }
            }
        }
    }

    type {
        val lootDropList = createLootDropList()

        if (LootDropManager.lootdrops.isNotEmpty()) {
            lootDropList.add(confirmClearLootDropsDialog())
        }

        dialogList(*lootDropList.toTypedArray()) {
            columns(2)
            buttonWidth(250)
            exitAction(createExitAction())
        }
    }
}

private fun confirmClearLootDropsDialog() = dialog {
    base {
        title { error("LootDrops aufräumen") }
        afterAction(DialogBase.DialogAfterAction.NONE)

        body {
            plainMessage(400) {
                error("Bist du sicher, dass du alle LootDrops löschen möchtest?")
            }
            plainMessage(400) {
                info("Dies kann nicht rückgängig gemacht werden!")
            }
        }
    }

    type {
        confirmation(createAcceptClearLootDropAction(), createDenyClearLootDropAction())
    }
}

private fun createAcceptClearLootDropAction(): ActionButton = actionButton {
    label { error("Bestätigen") }
    tooltip { info("Alle LootDrops löschen") }

    action {
        customClick { response, viewer ->
            LootDropManager.cleanupLootDrops()
            viewer.showDialog(createLootDropsClearedNotice())
        }
    }
}

private fun createLootDropsClearedNotice() = dialog {
    base {
        title { primary("LootDrops aufgeräumt") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage(400) {
                success("Alle LootDrops wurden erfolgreich aufgeräumt.")
            }
        }
    }

    type {
        notice {
            label { text("Zurück") }
            action {
                callback {
                    it.showDialog(listLootDropDialog())
                }
            }
        }
    }
}

private fun createDenyClearLootDropAction(): ActionButton = actionButton {
    label { text("Abbrechen") }
    tooltip { info("Zurück zur LootDrop Liste") }

    action {
        playerCallback {
            it.showDialog(listLootDropDialog())
        }
    }
}

private fun createLootDropList(): MutableList<Dialog> = LootDropManager.lootdrops
    .asSequence()
    .map { lootDropOneDialog(it) }
    .toMutableList()

private fun createExitAction() = actionButton {
    label { text("Zurück") }
    tooltip { info("Zurück zum LootDrop Menü") }

    action {
        playerCallback {
            it.showDialog(lootDropDialog())
        }
    }
}