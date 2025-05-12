package de.castcrafter.lootdrop.placeholder

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

object LootDropPlaceholderExpansion : PlaceholderExpansion() {
    override fun getAuthor() = "Ammo"
    override fun getIdentifier() = "lootdrop"
    override fun getVersion() = "2.0.0"
    override fun persist() = true

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        if (player == null) {
            return null
        }

        val paramsArray = params.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (paramsArray.isEmpty()) {
            return null
        }

        when (params) {
            "unique_joins" -> {
                return String.format("%d", Bukkit.getOfflinePlayers().size)
            }

            "current_players" -> {
                return String.format("%d", Bukkit.getOnlinePlayers().size)
            }

            "name" -> {
                return Arrays.stream(Arrays.copyOfRange(paramsArray, 1, paramsArray.size))
                    .reduce { a: String, b: String -> a + "_" + b }
                    .orElse("")
            }
        }

        return null
    }
}
