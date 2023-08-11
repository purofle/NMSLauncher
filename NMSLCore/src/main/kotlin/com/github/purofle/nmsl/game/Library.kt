package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.os.OperatingSystem
import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val downloads: DownloadsX,
    val extract: Extract? = null,
    val name: String, // com.mojang:patchy:1.3.9
    val natives: Natives? = null,
    val rules: List<Rule>? = null
) {
    fun isNative() = natives != null
    fun checkRules(): Boolean {
        if (rules == null) {
            return true
        }

        val allowRules = rules.filter { it.action == "allow" }
        val disallowRules = rules.filter { it.action == "disallow" }

        val os = OperatingSystem.CURRENT_OS.checkedName

        if (allowRules.isEmpty() and disallowRules.isEmpty()) {
            return false
        }

        if (allowRules.isNotEmpty()) {
            val allow = allowRules.any { it.os == null || it.os.name == os }
            if (!allow) {
                return false
            }
        }

        if (disallowRules.isNotEmpty()) {
            val disallow = allowRules.any { it.os == null || it.os.name == os }
            if (disallow) {
                return true
            }
        }

        return allowRules.isNotEmpty()
    }
}

@Serializable
data class Extract(
    val exclude: List<String>
)

@Serializable
data class Natives(
    val linux: String? = null, // natives-linux
    val osx: String? = null, // natives-osx
    val windows: String? = null // natives-windows
)


/**
 * 两种可能, 一种只有 allow, 一种有 allow 跟 disallow
 *
 * 有 allow 无 os 说明仅针对 disallow 做黑名单
 *
 * 有 allow 无 disallow 说明针对 allow 做白名单
 */
@Serializable
data class Rule(
    val action: String, // allow
    val os: Os? = null // 傻逼 Mojang
)

@Serializable
data class Os(
    val name: String // osx
)