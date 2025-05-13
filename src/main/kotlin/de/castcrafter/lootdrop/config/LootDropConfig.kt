package de.castcrafter.lootdrop.config

import de.castcrafter.lootdrop.plugin
import dev.slne.surf.surfapi.core.api.config.createSpongeYmlConfig
import dev.slne.surf.surfapi.core.api.config.surfConfigApi
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

val lootDropConfig by lazy {
    surfConfigApi.createSpongeYmlConfig<LootDropConfig>(plugin.dataPath, "config.yml")
}

@ConfigSerializable
data class LootDropConfig(
    private var startTimestampSeconds: Long = 0,
) {
    var startTimestamp: ZonedDateTime
        get() = ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(startTimestampSeconds),
            ZoneId.systemDefault()
        )
        set(startTimestamp) {
            this.startTimestampSeconds = startTimestamp.toEpochSecond()
        }
}
