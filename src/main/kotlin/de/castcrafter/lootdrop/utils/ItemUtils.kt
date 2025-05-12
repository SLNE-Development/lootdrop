package de.castcrafter.lootdrop.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.SkullMeta

object ItemUtils {

    fun getItemStack(
        material: Material,
        amount: Int,
        durability: Int,
        displayName: Component,
        vararg lore: Component
    ): ItemStack {
        val itemStack = ItemStack(material, amount)
        val itemMeta = itemStack.itemMeta

        if (itemMeta is Damageable) {
            itemMeta.damage = durability
        }

        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, false))

        if (lore.isNotEmpty()) {
            itemMeta.lore(lore.map { it.decoration(TextDecoration.ITALIC, false) })
        }

        itemStack.setItemMeta(itemMeta)

        return itemStack
    }

    @JvmStatic
    fun getPlayerHead(
        player: OfflinePlayer,
        displayName: Component,
        vararg lore: Component
    ): ItemStack {
        val playerHead = getItemStack(Material.PLAYER_HEAD, 1, 0, displayName, *lore)
        val itemMeta = playerHead.itemMeta

        check(itemMeta is SkullMeta) { "ItemMeta is not a SkullMeta" }

        itemMeta.setOwningPlayer(player)
        playerHead.setItemMeta(itemMeta)

        return playerHead
    }
}
