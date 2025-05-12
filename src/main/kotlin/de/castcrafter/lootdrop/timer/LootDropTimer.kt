package de.castcrafter.lootdrop.timer

import de.castcrafter.lootdrop.Main
import de.castcrafter.lootdrop.config.LootDropConfig
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration
import java.time.ZonedDateTime

class LootDropTimer : BukkitRunnable() {

    override fun run() {
        val currentTime = ZonedDateTime.now()
        val startTime = LootDropConfig.INSTANCE.startTimestamp

        if (currentTime.isBefore(startTime)) {
            return
        }

        val currentOffset = Duration.between(startTime, currentTime).toSeconds()
        val trades = LootDropConfig.INSTANCE.getTrades(currentOffset)

        if (!trades.isEmpty()) {
            val count = trades.size

            for (player in Bukkit.getOnlinePlayers()) {
//                Chat.sendMessage(
//                    player,
//                    Component.text("Es wurden ", NamedTextColor.GOLD)
//                        .append(Component.text(count, NamedTextColor.GREEN))
//                        .append(Component.text(" neue Trades geöffnet!", NamedTextColor.GOLD))
//                )
//
//                SoundUtils.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1)
            }
        }
    }

    fun stop() {
        try {
            if (!this.isCancelled) {
                this.cancel()
            } else {
                LOGGER.error("Could not stop timer, already cancelled")
            }
        } catch (ignored: Exception) {
            LOGGER.error("Could not stop timer, already cancelled")
        }
    }

    fun start() {
        if (this.isRunning) {
            LOGGER.error("Could not start timer, already running")
            return
        }

        this.runTaskTimerAsynchronously(Main.instance, 0, 20L)
    }

    val isRunning: Boolean
        get() {
            return try {
                !this.isCancelled
            } catch (ignored: Exception) {
                false
            }
        }

    companion object {
        private val LOGGER = ComponentLogger.logger()
    }
}
