package gecko10000.texturedump

import kotlinx.serialization.Serializable

@Serializable
data class Textures(
    val hashes: MutableList<String>,
)
