@file:Suppress("UnstableApiUsage")

package de.castcrafter.lootdrop.dialog

import de.castcrafter.lootdrop.dialog.sub.listLootDropDialog
import de.castcrafter.lootdrop.dialog.sub.lootDropProbabilityDialog
import de.castcrafter.lootdrop.gui.loot.lootDropContentGui
import de.castcrafter.lootdrop.loot.LootDrop
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.builder.actionButton
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase

fun lootDropDialog(): Dialog = dialog {
    base {
        title { primary("LootDrop") }
        afterAction(DialogBase.DialogAfterAction.NONE)
    }

    type {
        multiAction {
            columns(2)

            action(goodLootAction())
            action(badLootAction())
            action(goodLootProbabilityAction())
            action(listLootDropAction())

            action(summonLootDropAction())
        }
    }
}

private fun listLootDropAction(): ActionButton = actionButton {
    label { text("LootDrop Liste") }
    tooltip { info("Zeige eine Liste aller LootDrops") }
    action {
        playerCallback {
            it.showDialog(listLootDropDialog())
        }
    }
}

private fun goodLootAction() = actionButton {
    label { text("Guter Loot") }
    tooltip { info("Definiere hier den guten Loot") }
    action {
        playerCallback {
            lootDropContentGui(
                it,
                lootDrop = null,
                goodLoot = true,
                editable = true
            ).open()
        }
    }
}

private fun badLootAction() = actionButton {
    label { text("Schlechter Loot") }
    tooltip { info("Definiere hier den schlechten Loot") }
    action {
        playerCallback {
            lootDropContentGui(
                it,
                lootDrop = null,
                goodLoot = false,
                editable = true
            ).open()
        }
    }
}

private fun goodLootProbabilityAction(): ActionButton = actionButton {
    label { text("Wahrscheinlichkeit") }
    tooltip { info("Definiere die Wahrscheinlichkeit für guten Loot") }
    action {
        playerCallback {
            it.showDialog(
                lootDropProbabilityDialog(
                    editable = true,
                    lootDrop = null
                )
            )
        }
    }
}

fun goodLootChangedNotice(): Dialog = dialog {
    base {
        title { primary("LootDrop Loot geändert") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage(400) {
                success("Der gute Loot wurde erfolgreich geändert.")
            }
        }
    }

    type {
        notice {
            label { text("Zurück") }
            action {
                callback {
                    it.showDialog(lootDropDialog())
                }
            }
        }
    }
}

fun badLootChangedNotice(): Dialog = dialog {
    base {
        title { primary("LootDrop Loot geändert") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage(400) {
                success("Der schlechte Loot wurde erfolgreich geändert.")
            }
        }
    }

    type {
        notice {
            label { text("Zurück") }
            action {
                callback {
                    it.showDialog(lootDropDialog())
                }
            }
        }
    }
}

private fun summonLootDropAction() = actionButton {
    label { text("LootDrop erstellen") }
    width(250)
    tooltip { info("Erstelle einen LootDrop an deiner aktuellen Position") }
    action {
        playerCallback {
            LootDrop(it).spawn()
            it.showDialog(lootDropDialog())
        }
    }
}