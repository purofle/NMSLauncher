package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import kotlin.io.path.*

object GameManager {
    private val versionDir = OS.minecraftWorkingDirectory() / "versions"
    val versions: List<String> by lazy {
        versionDir.createDirectories()
        versionDir.listDirectoryEntries()
            .filter { it.isDirectory() }
            .map { it.fileName.toString() }
    }

    fun getVersionJson(version: String): GameJson = (versionDir / version / "$version.json").readText().toJsonObject()

    suspend fun downloadManifest(provider: DownloadProvider): Manifest {
        provider.getManifest().also {
            (versionDir / "version_manifest_v2.json").writeText(it.toJsonString())
            return it
        }
    }

    fun getLocalManifest(): Manifest = (versionDir / "version_manifest_v2.json").readText().toJsonObject()
}