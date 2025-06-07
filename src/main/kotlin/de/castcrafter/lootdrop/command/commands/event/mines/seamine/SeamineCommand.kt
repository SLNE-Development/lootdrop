package de.castcrafter.lootdrop.command.commands.event.mines.seamine

import de.castcrafter.lootdrop.listener.listeners.SEAMINE_KEY
import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType
import kotlin.math.floor

@Suppress("UnstableApiUsage")
private val nexoSeamineItem by lazy {
    ItemStack(Material.BRICK) {
        editMeta {
            it.setCustomModelData(1028)
        }
    }
}

fun CommandAPICommand.seamineCommand() = subcommand("seamine") {
    withPermission(PermissionRegistry.MINES_COMMAND_SEAMINE)

    playerExecutor { player, _ ->
        summonMine(player.location)
    }
}

private fun summonMine(location: Location): Boolean {
    val x = floor(location.x) + 0.5
    val y = floor(location.y)
    val z = floor(location.z) + 0.5
    val roundedLocation = Location(location.world, x, y, z)

    val armorStand = roundedLocation.world.spawn(
        roundedLocation,
        ArmorStand::class.java
    )

    armorStand.isInvisible = true
    armorStand.setGravity(false)
    armorStand.persistentDataContainer.set(SEAMINE_KEY, PersistentDataType.BYTE, 1.toByte())
    armorStand.setDisabledSlots(
        EquipmentSlot.HEAD,
        EquipmentSlot.CHEST,
        EquipmentSlot.FEET,
        EquipmentSlot.HAND,
        EquipmentSlot.OFF_HAND,
        EquipmentSlot.LEGS
    )
    armorStand.equipment.helmet = nexoSeamineItem


    val chainLocation = armorStand.location.clone()
    chainLocation.x = floor(chainLocation.x)
    chainLocation.z = floor(chainLocation.z)

    val lowestPossibleBlock = location.world.minHeight

    while (!chainLocation.block.type.isSolid && chainLocation.block.y > lowestPossibleBlock) {
        chainLocation.block.type = Material.CHAIN
        chainLocation.subtract(0.0, 1.0, 0.0)
    }

    return true
}