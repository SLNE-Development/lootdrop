package de.castcrafter.lootdrop

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import de.castcrafter.lootdrop.command.CommandManager
import de.castcrafter.lootdrop.listener.ListenerManager
import de.castcrafter.lootdrop.placeholder.LootDropPlaceholderExpansion
import org.bukkit.plugin.java.JavaPlugin
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

val plugin: Main get() = JavaPlugin.getPlugin(Main::class.java)

class Main : SuspendingJavaPlugin() {

    lateinit var random: SecureRandom
        private set

    override fun onLoad() {
        random = try {
            SecureRandom.getInstanceStrong()
        } catch (e: NoSuchAlgorithmException) {
            SecureRandom()
        }

//        LootDropConfig.INSTANCE.loadConfig()
//        PlayerUseConfig.INSTANCE.loadConfig()
    }

    override fun onEnable() {
        CommandManager.registerCommands()
        ListenerManager.registerListeners()

//        LootDropConfig.INSTANCE.loadAndStartTimerIfExistsInConfig()

        LootDropPlaceholderExpansion.register()
    }

    override fun onDisable() {
    }

    companion object {
        @JvmStatic
        val instance get() = plugin
    }
}
