package de.castcrafter.lootdrop

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import de.castcrafter.lootdrop.command.CommandManager
import de.castcrafter.lootdrop.listener.ListenerManager
import de.castcrafter.lootdrop.placeholder.LootDropPlaceholderExpansion
import dev.slne.surf.surfapi.bukkit.api.hook.papi.papiHook
import org.bukkit.plugin.java.JavaPlugin

val plugin: Main get() = JavaPlugin.getPlugin(Main::class.java)

class Main : SuspendingJavaPlugin() {

    override fun onLoad() {
//        LootDropConfig.INSTANCE.loadConfig()
//        PlayerUseConfig.INSTANCE.loadConfig()
    }

    override fun onEnable() {
        CommandManager.registerCommands()
        ListenerManager.registerListeners()

//        LootDropConfig.INSTANCE.loadAndStartTimerIfExistsInConfig()

        papiHook.register(LootDropPlaceholderExpansion)
    }

    override fun onDisable() {
    }
}
