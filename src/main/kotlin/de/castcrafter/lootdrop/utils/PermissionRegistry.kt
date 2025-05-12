package de.castcrafter.lootdrop.utils

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private val PREFIX = "lootdrop"
    private val COMMAND_PREFIX = "$PREFIX.command"
    private val EVENT_COMMAND_PREFIX = "$COMMAND_PREFIX.event"

    val EVENT_COMMAND = create("${EVENT_COMMAND_PREFIX}.main")

    val CONFIG_COMMAND = create("${EVENT_COMMAND_PREFIX}.config")
    val CONFIG_COMMAND_RELOAD = create("${EVENT_COMMAND_PREFIX}.config.reload")

    val DROPS_COMMAND = create("${EVENT_COMMAND_PREFIX}.drops")
    val DROPS_COMMAND_RELOAD = create("${EVENT_COMMAND_PREFIX}.drops.reload")
    val DROPS_COMMAND_RESET = create("${EVENT_COMMAND_PREFIX}.drops.reset")

    val MINES_COMMAND = create("${EVENT_COMMAND_PREFIX}.mines")
    val MINES_COMMAND_SEAMINE = create("${EVENT_COMMAND_PREFIX}.mines.seamine")

    val SUBEVENT_COMMAND = create("${EVENT_COMMAND_PREFIX}.subevent")
    val SUBEVENT_COMMAND_CREATE = create("${EVENT_COMMAND_PREFIX}.subevent.create")
    val SUBEVENT_COMMAND_JOIN = create("${EVENT_COMMAND_PREFIX}.subevent.join")
    val SUBEVENT_COMMAND_START = create("${EVENT_COMMAND_PREFIX}.subevent.start")
    val SUBEVENT_COMMAND_STOP = create("${EVENT_COMMAND_PREFIX}.subevent.stop")

    val LIEGEWIE_COMMAND = create("${COMMAND_PREFIX}.liegewie")
    val MAKESPECIAL_COMMAND = create("${COMMAND_PREFIX}.makespecial")
    val RENAME_COMMAND = create("${COMMAND_PREFIX}.rename")

    val LOOTDROP_COMMAND = create("${COMMAND_PREFIX}.lootdrop")
}