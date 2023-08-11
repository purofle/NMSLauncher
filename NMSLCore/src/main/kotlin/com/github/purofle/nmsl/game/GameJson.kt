package com.github.purofle.nmsl.game


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
data class GameJson(
    val arguments: Arguments? = null,
    val assetIndex: AssetIndex,
    val assets: String = "", // 1.12
    val complianceLevel: Int = 0, // 0
    val downloads: Downloads,
    val id: String = "", // 1.12.2
    val javaVersion: JavaVersion,
    val libraries: List<Library>,
    val logging: Logging,
    val mainClass: String, // net.minecraft.client.main.Main
    // 新版本的 json 没有 minecraftArguments, 被 arguments 取代
    val minecraftArguments: String? = null, // --username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}
    val minimumLauncherVersion: Int, // 18
    val releaseTime: String, // 2017-09-18T08:39:46+00:00
    val time: String, // 2017-09-18T08:39:46+00:00
    val type: String // release
)

@Serializable
data class Arguments(
    val game: JsonArray,
    val jvm: JsonArray,
)