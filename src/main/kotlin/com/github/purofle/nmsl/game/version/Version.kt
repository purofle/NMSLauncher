package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

/**
 * @param arguments 启动参数
 * @param assetIndex 资源信息
 * @param assets 游戏版本
 * @param downloads 下载信息
 */
@Serializable
data class Version(
    val arguments: Arguments,
    val assetIndex: AssetIndex,
    val assets: String, // 1.17
    val downloads: Map<String, DownloadInfo>,
    val libraries: List<Library>,
    val mainClass: String, // net.minecraft.client.main.Main
    val type: String // release
)