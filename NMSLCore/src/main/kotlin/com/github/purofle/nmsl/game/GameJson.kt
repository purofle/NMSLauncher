package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class GameJson(
    @SerializedName("arguments")
    val arguments: Arguments,
    @SerializedName("assetIndex")
    val assetIndex: AssetIndex,
    @SerializedName("assets")
    val assets: String, // 1.19
    @SerializedName("complianceLevel")
    val complianceLevel: Int, // 1
    @SerializedName("downloads")
    val downloads: Downloads,
    @SerializedName("id")
    val id: String, // 1.19
    @SerializedName("javaVersion")
    val javaVersion: JavaVersion,
    @SerializedName("libraries")
    val libraries: List<Library>,
    @SerializedName("logging")
    val logging: Logging,
    @SerializedName("mainClass")
    val mainClass: String, // net.minecraft.client.main.Main
    @SerializedName("minimumLauncherVersion")
    val minimumLauncherVersion: Int, // 21
    @SerializedName("releaseTime")
    val releaseTime: String, // 2022-06-07T09:42:18+00:00
    @SerializedName("time")
    val time: String, // 2022-06-07T09:42:18+00:00
    @SerializedName("type")
    val type: String // release
)