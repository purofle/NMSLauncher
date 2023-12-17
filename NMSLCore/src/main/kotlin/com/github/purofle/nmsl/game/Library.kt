package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.os.Architecture
import com.github.purofle.nmsl.utils.os.OS
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Library(
    val name: String,
    val downloads: LibraryDownload,
    val rules: List<Rule>? = null,
    val natives: Natives? = null,
    val extract: JsonElement? = null,
) {
    fun serializerNativeLibrary(): Artifact? {
        if (natives == null) throw Exception("natives is null")
        val nativeName = when (OS.CURRENT_OS) {
            OS.WINDOWS -> natives.windows
            OS.LINUX -> natives.linux
            OS.OSX -> natives.osx
            else -> throw Exception("unknown os")
        }
        return downloads.classifiers?.get(nativeName)
    }

    fun checkRule(): Boolean {
        if (rules.isNullOrEmpty()) return true
        if (rules.size == 1) {
            when (rules.first().action) {
                "allow" -> return rules.first().checkRule()
                "disallow" -> return !rules.first().checkRule()
            }
        }
        return rules.all { it.check() }
    }

    fun checkArchitecture(): Boolean {
        if (!name.contains("natives-")) return true
        val systemAndArch = name.split(":") // ["org.lwjgl.lwjgl", "lwjgl-openal", "3.3.2", "natives-windows-arm64"]
            .last() // "natives-windows-arm64"
            .split("-") // ["natives", "windows", "arm64"]
            .drop(1) // ["windows", "arm64"]
        if (systemAndArch.size == 2) {
            return systemAndArch.last() == Architecture.CURRENT.checkedName
        }
        return true
    }
}

@Serializable
data class LibraryDownload(
    val artifact: Artifact? = null,
    val classifiers: Map<String, Artifact>? = null,
)

@Serializable
data class Natives(
    val linux: String? = null,
    val osx: String? = null,
    val windows: String? = null,
)

@Serializable
data class Artifact(
    val path: String,
    val sha1: String,
    val size: Int,
    val url: String,
)

@Serializable
data class Rule(
    val action: String,
    val os: RuleOs? = null,
) {
    fun check(): Boolean {
        return when (action) {
            "allow" -> checkRule()
            "disallow" -> !checkRule()
            else -> throw Exception("unknown action")
        }
    }

    fun checkRule(): Boolean {
        if (os == null) return true
        if (os.name == OS.CURRENT_OS.checkedName) return true
        return false
    }
}

@Serializable
data class RuleOs(
    val name: String,
    val version: String? = null, // 上古时代的遗迹
)