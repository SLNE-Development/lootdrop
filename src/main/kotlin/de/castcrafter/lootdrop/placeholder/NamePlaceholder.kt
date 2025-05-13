package de.castcrafter.lootdrop.placeholder

import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import org.bukkit.OfflinePlayer
import java.util.*

object NamePlaceholder : PapiPlaceholder("name") {
    override fun parse(player: OfflinePlayer, args: List<String>): String? =
        Arrays.stream(Arrays.copyOfRange(args.toTypedArray(), 1, args.size))
            .reduce { a: String, b: String -> a + "_" + b }
            .orElse("")
}