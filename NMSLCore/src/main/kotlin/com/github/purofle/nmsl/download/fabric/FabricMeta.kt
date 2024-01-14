package com.github.purofle.nmsl.download.fabric

import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.serialization.Serializable

suspend fun getFabricMeta(url: String): FabricMeta {
    return HttpRequest.getJson(url)
}

@Serializable
data class FabricMeta(
    val loader: Maven,
    val intermediary: Maven,
    val launcherMeta: LauncherMeta
)

@Serializable
data class Maven(
    val maven: String,
    val version: String
)

@Serializable
data class LauncherMeta(
    val libraries: Libraries,
    val mainClass: Map<String, String>
)

@Serializable
data class Libraries(
    val common: List<FabricLibrary>,
)

@Serializable
data class FabricLibrary(
    val name: String,
    val url: String,
    val sha1: String,
    val size: Int,
)

fun getLibraryUrl(url: String = "https://maven.fabricmc.net/", maven: String): String = "${url}${getPath(maven)}"

fun getPath(maven: String): String {
    val (group, artifact, version) = maven.split(":")
    return "${group.replace(".", "/")}/${artifact}/$version/${artifact}-$version.jar"
}