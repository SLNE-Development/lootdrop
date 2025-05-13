package de.castcrafter.lootdrop.placeholder

import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

object CurrentPlayersPlaceholder : PapiPlaceholder("current-players") {
    override fun parse(player: OfflinePlayer, args: List<String>) =
        String.format("%d", Bukkit.getOnlinePlayers().size)
}