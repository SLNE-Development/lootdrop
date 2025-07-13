@file:Suppress("UnstableApiUsage")
@file:OptIn(NmsUseWithCaution::class)

package de.castcrafter.lootdrop.dialog.sub

import de.castcrafter.lootdrop.gui.loot.lootDropContentGui
import de.castcrafter.lootdrop.loot.LootDrop
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.builder.actionButton
import dev.slne.surf.surfapi.bukkit.api.dialog.clearDialogs
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import dev.slne.surf.surfapi.bukkit.api.nms.NmsUseWithCaution
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase
import org.bukkit.entity.Player

fun lootDropOneDialog(lootDrop: LootDrop): Dialog = dialog {
    base {
        val initialLocation = lootDrop.initialLocation

        title { primary("LootDrop - ${initialLocation.world.name}, ${initialLocation.blockX}, ${initialLocation.blockY}, ${initialLocation.blockZ}") }
        afterAction(DialogBase.DialogAfterAction.NONE)
    }

    type {
        multiAction {
            action(goodLootAction(lootDrop))
            action(badLootAction(lootDrop))
            action(goodLootProbabilityAction(lootDrop))
            action(deleteLootDropAction(lootDrop))
            action(teleportLootDropAction(lootDrop))

            exitAction {
                label { text("Zurück") }
                tooltip { info("Zurück zur LootDrop Liste") }

                action {
                    playerCallback {
                        it.showDialog(listLootDropDialog())
                    }
                }
            }
        }
    }
}

private fun goodLootAction(lootDrop: LootDrop) = actionButton {
    label { text("Guter Loot") }
    tooltip { info("Definiere hier den guten Loot") }
    action {
        playerCallback {
            lootDropContentGui(
                it,
                lootDrop = lootDrop,
                goodLoot = true,
                editable = false
            ).open()
        }
    }
}

private fun badLootAction(lootDrop: LootDrop) = actionButton {
    label { text("Schlechter Loot") }
    tooltip { info("Definiere hier den schlechten Loot") }
    action {
        playerCallback {
            lootDropContentGui(
                it,
                lootDrop = lootDrop,
                goodLoot = false,
                editable = false
            ).open()
        }
    }
}

private fun goodLootProbabilityAction(lootDrop: LootDrop): ActionButton = actionButton {
    label { text("Wahrscheinlichkeit") }
    tooltip { info("Definiere die Wahrscheinlichkeit für guten Loot") }
    action {
        playerCallback {
            it.showDialog(lootDropProbabilityDialog(false, lootDrop))
        }
    }
}

private fun deleteLootDropAction(lootDrop: LootDrop) = actionButton {
    label { error("Löschen") }
    tooltip { info("Lösche diesen LootDrop") }
    action {
        customClick { response, viewer ->
            viewer.showDialog(confirmDeleteLootDropDialog(lootDrop))
        }
    }
}

private fun confirmDeleteLootDropDialog(lootDrop: LootDrop) = dialog {
    base {
        title { primary("LootDrop löschen") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage(400) {
                info("Bist du sicher, dass du den LootDrop mit der ID ${lootDrop.uniqueId} löschen möchtest?")
            }
        }
    }

    type {
        confirmation(
            actionButton {
                label { error("Löschen") }
                tooltip { info("Bestätige das Löschen des LootDrops") }
                action {
                    customClick { response, viewer ->
                        lootDrop.despawn()
                        viewer.showDialog(lootDropDeletedNotice())
                    }
                }
            },
            actionButton {
                label { text("Abbrechen") }
                tooltip { info("Abbrechen und zurück zum LootDrop") }
                action {
                    playerCallback {
                        it.showDialog(lootDropOneDialog(lootDrop))
                    }
                }
            }
        )
    }
}

private fun lootDropDeletedNotice() = dialog {
    base {
        title { primary("LootDrop gelöscht") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage(400) {
                success("Der LootDrop wurde erfolgreich gelöscht.")
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

private fun teleportLootDropAction(lootDrop: LootDrop): ActionButton = actionButton {
    label { text("Teleport") }
    tooltip { info("Teleportiere dich zum LootDrop") }
    action {
        customClick { response, viewer ->
            val player = viewer as? Player ?: return@customClick

            viewer.clearDialogs()
            player.teleport(lootDrop.currentLocation)
        }
    }
}