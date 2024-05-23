package gecko10000.texturedump

import org.koin.dsl.module

fun pluginModules(plugin: TextureDump) = module {
    single { plugin }
}
