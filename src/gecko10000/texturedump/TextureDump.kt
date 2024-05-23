package gecko10000.texturedump

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

class TextureDump : JavaPlugin() {

    override fun onEnable() {
        startKoin {
            modules(pluginModules(this@TextureDump))
        }
        CommandHandler()
    }

}
