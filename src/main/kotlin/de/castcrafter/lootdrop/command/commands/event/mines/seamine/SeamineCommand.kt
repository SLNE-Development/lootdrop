package de.castcrafter.lootdrop.command.commands.event.mines.seamine

import de.castcrafter.lootdrop.utils.PermissionRegistry
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

fun CommandAPICommand.seamineCommand() = subcommand("seamine") {
    withPermission(PermissionRegistry.MINES_COMMAND_SEAMINE)

    playerExecutor { player, _ ->
        summonMine(player.location)
    }
}

private fun summonMine(location: Location): Boolean {
    println("Summoning mine at: $location")
    val x = floor(location.x) + 0.5
    val y = floor(location.y)
    val z = floor(location.z) + 0.5
    val roundedLocation = Location(location.world, x, y, z)

    val heartOfTheSea = ItemStack(Material.HEART_OF_THE_SEA)
    val meta = heartOfTheSea.itemMeta

    if (meta != null) {
        meta.setCustomModelData(2)
        heartOfTheSea.setItemMeta(meta)
    }

    val armorStand = roundedLocation.world.spawn(
        roundedLocation,
        ArmorStand::class.java
    )

    armorStand.isInvisible = true
    armorStand.setGravity(false)
    armorStand.addScoreboardTag("seamine")
    armorStand.setDisabledSlots(
        EquipmentSlot.HEAD,
        EquipmentSlot.CHEST,
        EquipmentSlot.FEET,
        EquipmentSlot.HAND,
        EquipmentSlot.OFF_HAND,
        EquipmentSlot.LEGS
    )
    armorStand.equipment.helmet = heartOfTheSea

    val chainLocation = armorStand.location.clone()
    chainLocation.x = floor(chainLocation.x)
    chainLocation.z = floor(chainLocation.z)
    while (!chainLocation.block.type.isSolid) {
        chainLocation.block.type = Material.CHAIN
        chainLocation.subtract(0.0, 1.0, 0.0)
    }

    return true
}