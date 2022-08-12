package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class GameJson(
    @SerializedName("assetIndex")
    val assetIndex: AssetIndex = AssetIndex(),
    @SerializedName("assets")
    val assets: String = "", // 1.12
    @SerializedName("complianceLevel")
    val complianceLevel: Int = 0, // 0
    @SerializedName("downloads")
    val downloads: Downloads = Downloads(),
    @SerializedName("id")
    val id: String = "", // 1.12.2
    @SerializedName("javaVersion")
    val javaVersion: JavaVersion = JavaVersion(),
    @SerializedName("libraries")
    val libraries: List<Library> = listOf(),
    @SerializedName("logging")
    val logging: Logging = Logging(),
    @SerializedName("mainClass")
    val mainClass: String = "", // net.minecraft.client.main.Main
    @SerializedName("minecraftArguments")
    val minecraftArguments: String = "", // --username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}
    @SerializedName("minimumLauncherVersion")
    val minimumLauncherVersion: Int = 0, // 18
    @SerializedName("releaseTime")
    val releaseTime: String = "", // 2017-09-18T08:39:46+00:00
    @SerializedName("time")
    val time: String = "", // 2017-09-18T08:39:46+00:00
    @SerializedName("type")
    val type: String = "" // release
)