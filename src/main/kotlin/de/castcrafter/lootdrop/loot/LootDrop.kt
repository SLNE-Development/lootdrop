package de.castcrafter.lootdrop.loot

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import com.github.shynixn.mccoroutine.folia.ticks
import de.castcrafter.lootdrop.loot.action.LootActions
import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.event.unregister
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.showTitle
import dev.slne.surf.surfapi.core.api.util.objectListOf
import it.unimi.dsi.fastutil.objects.ObjectList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.bukkit.*
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import kotlin.time.Duration.Companion.milliseconds

class LootDrop(
    private val initiator: OfflinePlayer?,
    private val initialLocation: Location = run {
        val initiatorPlayer = initiator?.player

        if (initiator != null && initiator.isOnline && initiatorPlayer != null) {
            return@run initiatorPlayer.location.clone().add(0.0, 20.0, 0.0)
        }

        val first = Bukkit.getWorlds().first()
        val highestBlock = first.getHighestBlockAt(0, 0, HeightMap.MOTION_BLOCKING)

        return@run Location(first, 0.0, highestBlock.y.toDouble() + 20.toDouble(), 0.0)
    },
    private val goodChance: Double,
    private val goodLootContent: ObjectList<ItemStack> = objectListOf(),
    private val badLootContent: ObjectList<ItemStack> = objectListOf(),
) : Listener {

    private lateinit var backingArmorStand: ArmorStand
    private lateinit var animator: Job

    val uniqueId get() = backingArmorStand.uniqueId

    fun spawn() {
        register()

        backingArmorStand = initialLocation.world.spawn(initialLocation, ArmorStand::class.java) {
            it.isInvisible = true
            it.setDisabledSlots(
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.HAND,
                EquipmentSlot.OFF_HAND,
            )
            it.equipment.helmet = run {
                val item = ItemStack.of(Material.HEART_OF_THE_SEA)

                item.editMeta { meta ->
                    meta.setCustomModelData(1)
                }

                return@run item
            }
        }

        animator = plugin.launch(plugin.entityDispatcher(backingArmorStand)) {
            while (true) {
                backingArmorStand.velocity = Vector(0.0, -0.03, 0.0)
                backingArmorStand.world.spawnParticle(
                    Particle.CAMPFIRE_SIGNAL_SMOKE,
                    backingArmorStand.location.clone().add(0.0, 6.0, 0.0),
                    1,
                    0.3,
                    0.0,
                    0.3,
                    0.0
                )
                delay(1.ticks)
            }
        }

        Bukkit.getOnlinePlayers().forEach { player ->
            player.showTitle {
                title {
                    primary("Loot Drop")
                }
                subtitle {
                    spacer("Location: ${initialLocation.blockX}, ${initialLocation.blockY}, ${initialLocation.blockZ}")
                }
                times {
                    fadeIn(10.milliseconds)
                    stay(200.milliseconds)
                    fadeOut(10.milliseconds)
                }
            }

            player.playSound {
                type(Sound.BLOCK_BEACON_ACTIVATE)
                source(net.kyori.adventure.sound.Sound.Source.BLOCK)
            }
        }
    }

    private fun collect(collector: Player) {
        val armorStandLocation = backingArmorStand.location.clone()

        despawn()

        val randomDouble = plugin.random.nextDouble()
        val lootContent = if (randomDouble <= goodChance) {
            goodLootContent
        } else {
            badLootContent
        }

        lootContent.forEach { loot ->
            val actions = LootActions.fromMaterial(loot.type)

            actions.forEach { action ->
                action.action.performActionInternal(
                    armorStandLocation,
                    armorStandLocation,
                    loot.amount
                )
            }

            if (actions.isNotEmpty()) {
                return
            }

            armorStandLocation.world.dropItemNaturally(armorStandLocation, loot)
        }

        collector.playSound {
            type(Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR)
            volume(.5f)
            source(net.kyori.adventure.sound.Sound.Source.BLOCK)
        }
    }

    private fun despawn() {
        animator.cancel()
        backingArmorStand.remove()
        unregister()
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val armorStand = event.entity as? ArmorStand ?: return

        if (armorStand.uniqueId != uniqueId) {
            return
        }

        val damagee = event.damager as? Player ?: run {
            val projectile = event.damager as? Projectile ?: return@run null

            return@run projectile.shooter as? Player
        } ?: return

        collect(damagee)
    }

}