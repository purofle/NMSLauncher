package com.github.purofle.nmsl.download.fabric

import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.serialization.Serializable

//TODO
object FabricVersion {
    private const val GAME_META_URL = "https://meta.fabricmc.net/v2/versions/game"
    private const val LOADER_META_URL = "https://meta.fabricmc.net/v2/versions/loader"
    private suspend fun getGameVersions(url: String): List<String> {
        return HttpRequest.getJson<List<GameVersion>>(url).map { it.version }
    }

    suspend fun getFabricVersion(): List<RemoteFabricVersion> {
        val gameVersions = getGameVersions(GAME_META_URL)
        val loaderVersions = getGameVersions(LOADER_META_URL)

        return gameVersions.zip(loaderVersions)
            .map { (gameVersion, loaderVersion) ->
                RemoteFabricVersion(
                    gameVersion = gameVersion,
                    fabricVersion = loaderVersion,
                    metaUrl = "https://meta.fabricmc.net/v2/versions/loader/$gameVersion/$loaderVersion"
                )
            }
    }

    @Serializable
    data class GameVersion(
        val version: String,
        val stable: Boolean,
        val maven: String? = null,
    )

    @Serializable
    data class RemoteFabricVersion(
        val gameVersion: String,
        val fabricVersion: String,
        val metaUrl: String
    )
}