package com.github.purofle.nmsl.game.download

import com.github.purofle.nmsl.platforms.OperatingSystem
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.FileSystems
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries

object Downloader {
    private var httpClient = HttpClient(CIO)
    private val minecraftHome = OperatingSystem.getWorkingDirectory("minecraft")
    private val format = Json { ignoreUnknownKeys = true }

    private suspend fun getAssetsInfo(url: String): Assets {
        val response: HttpResponse = this.httpClient.get(url)
        return format.decodeFromString(response.readText())
    }
    private suspend fun downloadAsset(hash: String) {
        if (Path("$minecraftHome/assets").listDirectoryEntries().isEmpty()) {
            val f = hash.subSequence(0, 2)

            val file = File("$minecraftHome/assets/objects/$f/$hash")
            file.parentFile.mkdirs()
            httpClient.downloadFile(file, "https://resources.download.minecraft.net/$f/$hash") { success ->
                println("Downloading asset $hash.... ${if (!success) "FAIL!" else "SUCCESS"}")
            }
        }

    }
    private suspend fun downloadAssets(assets: Assets) {
        assets.objects.forEach { (key, value) ->
            println(key)
            downloadAsset(value.hash)
        }
    }
    suspend fun getReleaseInfo(url: String): VersionManifest {
        val response: HttpResponse = this.httpClient.get(url)
        return format.decodeFromString(response.readText())
    }
    private fun checkCurrentOSInAllowList(r: LibraryInfo, os: String): Boolean {
        r.rules.forEach {
            if (it.action == "allow" && it.os["name"] == os || it.os["name"] == os) {
                return true
            }
        }
        return true
    }

    private suspend fun downloadLibraries(m: VersionManifest) {
        if(FileSystems.getDefault().getPath("$minecraftHome/libraries").listDirectoryEntries().isNotEmpty()) {
            println("Libraries downloaded?")
            return
        }
        m.libraries.forEach {

            when (OperatingSystem.CURRENT_OS) {
                OperatingSystem.WINDOWS -> {
                    if (it.downloads.classifiers.natives_windows.url.isNotBlank() && checkCurrentOSInAllowList(it, "windows")) {
                        val native = File("$minecraftHome/libraries/${it.downloads.classifiers.natives_windows.path}")
                        native.parentFile.mkdirs()
                        httpClient.downloadFile(
                            native,
                            it.downloads.classifiers.natives_windows.url
                        ) { success ->
                            println("Downloading native-jar ${it.name}.... ${if (!success) "FAIL!" else "SUCCESS!"}")
                        }
                    }
                }
                OperatingSystem.LINUX -> {
                    if (it.downloads.classifiers.natives_linux.url.isNotEmpty() &&!checkCurrentOSInAllowList(it, "linux")) {
                        val native = File("$minecraftHome/libraries/${it.downloads.classifiers.natives_linux.path}")
                        native.parentFile.mkdirs()
                        httpClient.downloadFile(native, it.downloads.classifiers.natives_linux.url) { success ->
                            println("Downloading native-jar ${it.name}.... ${if (!success) "FAIL!" else "SUCCESS!"}")
                        }
                    }
                }
                OperatingSystem.OSX -> {
                    if (it.downloads.classifiers.natives_macos.url.isNotEmpty() && !checkCurrentOSInAllowList(it, "osx")) {
                        File("$minecraftHome/libraries/${it.downloads.classifiers.natives_macos.path}").parentFile.mkdirs()
                        httpClient.downloadFile(
                            File("$minecraftHome/libraries/${it.downloads.classifiers.natives_macos.path}"),
                            it.downloads.classifiers.natives_macos.url
                        ) { success ->
                            println("Downloading native-jar ${it.name}.... ${if (!success) "FAIL!" else "SUCCESS!"}")
                        }
                    }
                }
                else -> println("Unused")
            }
            File("$minecraftHome/libraries/${it.downloads.artifact.path}").parentFile.mkdirs()
            if (checkCurrentOSInAllowList(it, OperatingSystem.CURRENT_OS.checkedName)) {
                httpClient.downloadFile(
                    File("$minecraftHome/libraries/${it.downloads.artifact.path}"),
                    it.downloads.artifact.url
                )
                { success ->
                    println("Downloading ${it.name}.... ${if (!success) "FAIL!" else "SUCCESS!"}")
                }
            }
        }

        println("End of downloading libraries.")
    }

    suspend fun downloadClient(m: VersionManifest) {
        println("Downloading client ${m.id}")
        downloadLibraries(m)
        downloadAssets(getAssetsInfo(m.assetIndex.url))
        println(getAssetsInfo(m.assetIndex.url).objects["icons/icon_16x16.png"].toString())
    }
    suspend fun getReleases(): VersionsManifest {
        val releases = if (!File("$minecraftHome/version_manifest.json").exists()) {
            val response: HttpResponse =
                this.httpClient.get("https://launchermeta.mojang.com/mc/game/version_manifest.json")
            format.decodeFromString<VersionsManifest>(response.readText())
        } else {
            val file = File("$minecraftHome/version_manifest.json")
            format.decodeFromString(file.readText())
        }
        return releases

    }
    suspend fun downloadReleasesToFileSystem() {
        if(File("$minecraftHome/version_manifest.json").exists()) { return }
        val response: HttpResponse =
            this.httpClient.get("https://launchermeta.mojang.com/mc/game/version_manifest.json")
        File("$minecraftHome/version_manifest.json").printWriter().use { out ->
            out.println(response.readText())
        }
    }


    private suspend fun HttpClient.downloadFile(file: File, url: String, callback: suspend (boolean: Boolean) -> Unit) {
        val call = request<HttpResponse> {
            url(url)
            method = HttpMethod.Get
        }
        if (!call.status.isSuccess()) {
            callback(false)
        }
        call.content.copyAndClose(file.writeChannel())
        return callback(true)
    }
}