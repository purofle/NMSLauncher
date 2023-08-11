package com.github.purofle.nmsl.game


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadsX(
    val artifact: Artifact? = null,
    val classifiers: Classifiers? = null
)

@Serializable
data class Artifact(
    val path: String, // com/mojang/patchy/1.3.9/patchy-1.3.9.jar
    val sha1: String, // eb8bb7b66fa0e2152b1b40b3856e82f7619439ee
    val size: Int, // 23581
    val url: String // https://libraries.minecraft.net/com/mojang/patchy/1.3.9/patchy-1.3.9.jar
)

@Serializable
data class Classifiers(
    @SerialName("natives-linux")
    val nativesLinux: Artifact? = null,
    @SerialName("natives-osx")
    val nativesOsx: Artifact? = null,
    @SerialName("natives-windows")
    val nativesWindows: Artifact? = null,
)

@Serializable
data class Sources(
    val path: String, // com/mojang/text2speech/1.10.3/text2speech-1.10.3-sources.jar
    val sha1: String, // 404339fe43d1011ee046a249b0ec7ae9ce04a834
    val size: Int, // 4632
    val url: String // https://libraries.minecraft.net/com/mojang/text2speech/1.10.3/text2speech-1.10.3-sources.jar
)