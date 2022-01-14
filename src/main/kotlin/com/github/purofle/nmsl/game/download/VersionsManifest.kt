package com.github.purofle.nmsl.game.download

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Latest(val release: String, val snapshot: String)

@Serializable
data class Version(val id: String, val type: String, val url: String, val time: String, val releaseTime: String)

@Serializable
data class VersionsManifest(val latest: Latest, val versions: ArrayList<Version>)

//@Serializable
//data class VersionArguments(val game: @Contextual ArrayList<@Polymorphic Any>, val jvm: @Contextual ArrayList<@Polymorphic Any>)
@Serializable
data class VersionAssetIndex(val id: String, val sha1: String, val size: Int, val totalSize: Int, val url: String)
@Serializable
data class VersionManifest(val assetIndex: VersionAssetIndex, val assets: String, val complianceLevel: Int,
                           val downloads: HashMap<String, DownloadInfo>, val id: String, val javaVersion: JavaVersion, val libraries: ArrayList<LibraryInfo>,
                           val mainClass: String, val minimumLauncherVersion: Int, val releaseTime: String, val time: String, val type: String
                            )
@Serializable
data class JavaVersion(val component: String, val majorVersion: Int)

@Serializable
data class DownloadInfo(val sha1: String, val size: Int, val url: String)

@Serializable
data class LibraryInfo(val downloads: LibraryDownloadsInfo, val name: String, val rules: @Contextual ArrayList<LibraryRule> = ArrayList(),
                       val natives: HashMap<String, String> = HashMap())

@Serializable
data class LibraryRule(val action: String, val os: HashMap<String, String> = HashMap())


@Serializable
data class LibraryDownloadsInfo(val artifact: ArtifactInfo,
                                val classifiers: ClassifiersInfo = ClassifiersInfo(NativeInfo("","",0,""),
                                    NativeInfo("","",0,""),NativeInfo("","",0,"")),
                                val name: String = "")
@Serializable
data class ArtifactInfo(val path: String, val sha1: String, val size: Int, val url: String)

@Serializable
data class ClassifiersInfo(@SerialName("natives-linux") val natives_linux: NativeInfo = NativeInfo("","",0,""),
                           @SerialName("natives-windows") val natives_windows: NativeInfo = NativeInfo("","",0,""),
                           @SerialName("natives-macos") val natives_macos: NativeInfo = NativeInfo("","",0,""))

@Serializable
data class NativeInfo(val path: String, val sha1: String, val size: Int, var url: String)