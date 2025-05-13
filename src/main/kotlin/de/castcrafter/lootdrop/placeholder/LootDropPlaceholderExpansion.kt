package de.castcrafter.lootdrop.placeholder

import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiExpansion

object LootDropPlaceholderExpansion : PapiExpansion(
    "lootdrop", listOf(
        CurrentPlayersPlaceholder,
        UniqueJoinsPlaceholder,
        NamePlaceholder
    )
)