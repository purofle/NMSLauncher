package com.github.purofle.nmsl.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class GameDownload(
    val sha1: String,
    val size: Int,
    val url: String,
)

@Serializable
data class AssetIndex(
    val sha1: String, val size: Int, val url: String, val totalSize: Int, val id: String
)

@Serializable
data class GameDownloads(
    val client: GameDownload,
    @SerialName("client_mappings") val clientMappings: GameDownload? = null,
    val server: GameDownload? = null, // 更是复古
    @SerialName("server_mappings") val serverMappings: GameDownload? = null,
    @SerialName("windows_server") val windowsServer: GameDownload? = null
)

@Serializable
data class GameJson(
    val arguments: JsonObject? = null,
    val minecraftArguments: String? = null,
    val assetIndex: AssetIndex,
    val assets: String,
    val complianceLevel: Int? = null,
    val downloads: GameDownloads,
    val id: String,
    val javaVersion: JsonObject? = null,
    val libraries: List<Library>,
    val logging: JsonObject? = null,
    val mainClass: String,
    val minimumLauncherVersion: Int,
    val releaseTime: String,
    val time: String,
    val type: String
) {
    fun serializer(): List<Artifact> {
        return serializerLibrary()
    }

    private fun serializerLibrary(): List<Artifact> {
        return libraries.mapNotNull { library ->
            if (!library.checkRule()) return@mapNotNull null
            if (!library.checkArchitecture()) return@mapNotNull null
            listOfNotNull(
                library.downloads.artifact,
                library.natives?.let { library.serializerNativeLibrary() }
            )
        }.flatten()
    }
}
