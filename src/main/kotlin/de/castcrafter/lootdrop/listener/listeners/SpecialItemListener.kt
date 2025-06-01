package de.castcrafter.lootdrop.listener.listeners

import dev.slne.surf.surfapi.core.api.messages.Colors
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.showTitle
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.persistence.PersistentDataType
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object SpecialItemListener : Listener {

    private val plainTextComponentSerializer = PlainTextComponentSerializer.plainText()
    val ANNOUNCED_KEY = NamespacedKey("lootdrop", "special_item_announced")
    val SPECIAL_KEY_FINDER = NamespacedKey("lootdrop", "special_item_finder")
    val SPECIAL_KEY_TIME = NamespacedKey("lootdrop", "special_item_time")
    val SPECIAL_KEY = NamespacedKey("lootdrop", "special_item")

    @EventHandler
    fun onItemPickup(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val itemStack = event.item.itemStack

        if (event.item.owner != player.uniqueId) {
            return
        }

        processItem(itemStack, player)
    }

    @EventHandler
    fun onItemClick(event: InventoryClickEvent) {
        val itemStack = event.currentItem ?: return
        val player = event.whoClicked as? Player ?: return

        val inventoryName = plainTextComponentSerializer.serialize(event.view.title())
        val defaultName = plainTextComponentSerializer.serialize(
            event.view.topInventory.type.defaultTitle()
        )

        if (!(inventoryName.equals(defaultName, ignoreCase = true))) {
            return
        }

        if (checkSpecial(itemStack).isNotSpecial) {
            return
        }

        processItem(itemStack, player)
    }

    @EventHandler
    fun onAnvilUse(event: PrepareAnvilEvent) {
        val inventory = event.inventory
        val itemStack = inventory.secondItem ?: return

        if (checkSpecial(itemStack).isNotSpecial) {
            return
        }

        val firstItem = inventory.firstItem
        if (inventory.result == null || firstItem != null && firstItem.type != Material.ENCHANTED_BOOK) {
            return
        }

        val resultItem = inventory.result ?: return

        resultItem.editMeta(EnchantmentStorageMeta::class.java) { meta ->
            val data = meta.persistentDataContainer

            data.set(SPECIAL_KEY, PersistentDataType.BYTE, 1.toByte())
            data.set(ANNOUNCED_KEY, PersistentDataType.BYTE, 1.toByte())
        }

        event.result = resultItem
    }

    @EventHandler
    private fun onHopperPickup(event: InventoryPickupItemEvent) {
        val itemStack = event.item.itemStack
        val itemMeta = itemStack.itemMeta
        val data = itemMeta.persistentDataContainer

        if (checkSpecial(itemStack).isNotSpecial) {
            return
        }

        if (data.has(SPECIAL_KEY) || data.has(ANNOUNCED_KEY)) {
            return
        }

        event.isCancelled = true
    }

    fun checkSpecial(itemStack: ItemStack?): SpecialState {
        if (itemStack == null) {
            return SpecialState.NOT_SPECIAL
        }

        val itemMeta = itemStack.itemMeta ?: return SpecialState.NOT_SPECIAL
        val data = itemMeta.persistentDataContainer

        if (!data.has(SPECIAL_KEY)) {
            return SpecialState.NOT_SPECIAL
        }

        if (data.has(ANNOUNCED_KEY)) {
            return SpecialState.SPECIAL_ANNOUNCED
        }

        return SpecialState.SPECIAL_UNANNOUNCED
    }

    fun processItem(itemStack: ItemStack, player: Player) {
        if (player.gameMode == GameMode.CREATIVE || (player.gameMode == GameMode.SPECTATOR)) {
            return
        }

        val meta = itemStack.itemMeta ?: return
        val data = meta.persistentDataContainer

        if (checkSpecial(itemStack).isNotSpecial) {
            return
        }

        data.set(SPECIAL_KEY, PersistentDataType.BYTE, 1.toByte())

        if (data.has(ANNOUNCED_KEY)) {
            return
        }

        data.set(ANNOUNCED_KEY, PersistentDataType.BYTE, 1.toByte())
        data.set(
            SPECIAL_KEY_FINDER,
            PersistentDataType.STRING,
            plainTextComponentSerializer.serialize(
                player.displayName().colorIfAbsent(NamedTextColor.YELLOW)
            )
        )
        data.set(SPECIAL_KEY_TIME, PersistentDataType.LONG, System.currentTimeMillis())

        var displayNameComponent = meta.displayName()

        if (displayNameComponent == null) {
            displayNameComponent = Component.text("")
        }

        var itemStackDisplayName = plainTextComponentSerializer.serialize(displayNameComponent)

        if (itemStackDisplayName.isEmpty()) {
            itemStackDisplayName = plainTextComponentSerializer.serialize(
                Component.translatable(itemStack.type.translationKey())
            )
        }

        if (itemStackDisplayName.isEmpty()) {
            itemStackDisplayName = itemStack.type.name
        }

        val time = ZonedDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formattedTime = time.format(formatter)

        val loreList = buildList<Component> {
            val oldLore = meta.lore()

            if (oldLore != null) {
                addAll(oldLore)

                add(Component.empty())
                add(buildText {
                    spacer("=".repeat(26))
                })
            }

            add(buildText {
                spacer("Gefunden durch: ")
                append(player.displayName().colorIfAbsent(NamedTextColor.YELLOW))
            })

            add(buildText {
                spacer("Gefunden am: ")
                append(Component.text(formattedTime, NamedTextColor.YELLOW))
            })
        }.map { it.decoration(TextDecoration.ITALIC, false) }

        meta.lore(loreList)

        try {
            itemStack.setItemMeta(meta)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        Bukkit.getOnlinePlayers().forEach { online ->
            online.showTitle {
                title {
                    append(Component.text(itemStackDisplayName, Colors.PRIMARY))
                }
                subtitle {
                    spacer("Gefunden durch: ")
                    append(player.displayName().colorIfAbsent(NamedTextColor.YELLOW))
                }
                times {
                    fadeIn(250.milliseconds)
                    stay(10.seconds)
                    fadeOut(250.milliseconds)
                }
            }

            online.playSound {
                type(org.bukkit.Sound.ENTITY_ENDER_DRAGON_GROWL)
                source(Sound.Source.HOSTILE)
                volume(.35f)
                pitch(.75f)
            }
        }
    }

    enum class SpecialState {
        NOT_SPECIAL,
        SPECIAL_ANNOUNCED,
        SPECIAL_UNANNOUNCED;

        val isSpecial: Boolean
            get() = this == SPECIAL_ANNOUNCED || this == SPECIAL_UNANNOUNCED

        val isAnnounced: Boolean
            get() = this == SPECIAL_ANNOUNCED

        val isUnannounced: Boolean
            get() = this == SPECIAL_UNANNOUNCED

        val isNotSpecial: Boolean
            get() = this == NOT_SPECIAL
    }
}
