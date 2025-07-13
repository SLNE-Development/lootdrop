@file:Suppress("UnstableApiUsage")
@file:OptIn(NmsUseWithCaution::class)

package de.castcrafter.lootdrop.dialog.sub

import de.castcrafter.lootdrop.dialog.lootDropDialog
import de.castcrafter.lootdrop.loot.LootDrop
import de.castcrafter.lootdrop.loot.LootDropConfigurator
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.builder.actionButton
import dev.slne.surf.surfapi.bukkit.api.dialog.clearDialogs
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import dev.slne.surf.surfapi.bukkit.api.nms.NmsUseWithCaution
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import io.papermc.paper.registry.data.dialog.DialogBase

fun lootDropProbabilityDialog(editable: Boolean, lootDrop: LootDrop?) =
    dialog {
        val value = if (editable) LootDropConfigurator.goodChance else lootDrop?.goodChance ?: 0

        base {
            title { primary("LootDrop Wahrscheinlichkeit") }
            afterAction(DialogBase.DialogAfterAction.NONE)

            if (editable) {
                input {
                    numberRange("probability", 0F..100F) {
                        label { text("Wahrscheinlichkeit") }
                        step(1.0F)
                        this.initial = value.toFloat()
                    }
                }

                body {
                    plainMessage(400) {
                        info("Hier kannst du die Wahrscheinlichkeit für einen guten LootDrop anpassen")
                    }
                }
            } else {
                body {
                    plainMessage(400) {
                        info("Die Wahrscheinlichkeit für einen guten LootDrop beträgt aktuell")
                    }
                    plainMessage(400) {
                        variableValue("$value%")
                    }
                }
            }
        }

        type {
            if (editable) {
                confirmation(createSaveButton(true), createBackButton(true, null))
            } else {
                notice(createBackButton(false, lootDrop))
            }
        }
    }

private fun createSaveButton(edit: Boolean) = actionButton {
    label { success("Speichern") }

    action {
        customClick { response, viewer ->
            val probability = response.getFloat("probability")?.toInt()

            if (probability != null) {
                LootDropConfigurator.goodChance = probability
                viewer.showDialog(createProbabilitySavedNotice(edit))
                return@customClick
            }

            viewer.showDialog(createProbabilityNotSavedNotice(edit))
        }
    }
}

private fun createProbabilitySavedNotice(edit: Boolean) = dialog {
    base {
        title { primary("LootDrop Wahrscheinlichkeit gespeichert") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage {
                success("Die Wahrscheinlichkeit für guten Loot wurde erfolgreich gespeichert.")
            }
        }
    }

    type {
        notice(createBackButton(edit, null))
    }
}

private fun createProbabilityNotSavedNotice(edit: Boolean) = dialog {
    base {
        title { primary("LootDrop Wahrscheinlichkeit nicht gespeichert") }
        afterAction(DialogBase.DialogAfterAction.NONE)
        body {
            plainMessage {
                error("Die Wahrscheinlichkeit für guten Loot konnte nicht gespeichert werden.")
            }
        }
    }

    type {
        notice(createBackButton(edit, null))
    }
}

private fun createBackButton(edit: Boolean, lootDrop: LootDrop?) = actionButton {
    label { text("Zurück") }

    action {
        playerCallback {
            if (edit) {
                it.showDialog(lootDropDialog())
            } else {
                if (lootDrop != null) {
                    it.showDialog(lootDropOneDialog(lootDrop))
                } else {
                    it.clearDialogs()
                    it.sendText {
                        appendPrefix()
                        error("Du kannst die LootDrop Wahrscheinlichkeit nicht bearbeiten.")
                    }
                }
            }
        }
    }
}