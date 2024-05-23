package gecko10000.texturedump

import gecko10000.geckoconfig.YamlFileManager
import gecko10000.geckolib.extensions.MM
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import redempt.redlib.commandmanager.CommandHook
import redempt.redlib.commandmanager.CommandParser

class CommandHandler : KoinComponent {

    private val plugin: TextureDump by inject()

    init {
        CommandParser(plugin.getResource("command.rdcml")).parse().register(plugin.name.lowercase(), this)
    }

    private fun tryExtractHash(item: ItemStack): String? {
        if (item.type != Material.PLAYER_HEAD) return null
        val meta = item.itemMeta as? SkullMeta ?: return null
        val profile = meta.playerProfile ?: return null
        val skinURL = profile.textures.skin ?: return null
        return skinURL.toString().substringAfterLast('/')
    }

    @CommandHook("dump")
    fun dumpCommand(player: Player, filename: String) {
        val hashes = player.inventory
            .filterNotNull()
            .mapNotNull { tryExtractHash(it) }
        val fileManager = YamlFileManager(
            plugin.dataFolder,
            filename,
            initialValue = Textures(hashes = mutableListOf()),
            serializer = Textures.serializer()
        )
        val existingHashes = fileManager.value.hashes
        val existingSize = existingHashes.size
        val newHashes = hashes.plus(existingHashes)
            .toSet()
        fileManager.value.hashes.clear()
        fileManager.value.hashes.addAll(newHashes)
        fileManager.save()
        player.sendMessage(MM.deserialize("<green>Added ${newHashes.size - existingSize} hashes (${newHashes.size} total) to $filename."))
    }

}
