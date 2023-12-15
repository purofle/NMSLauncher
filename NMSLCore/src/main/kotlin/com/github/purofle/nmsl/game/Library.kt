package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.os.Architecture
import com.github.purofle.nmsl.utils.os.OperatingSystem
import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val downloads: DownloadsX,
    val name: String, // com.mojang:patchy:1.3.9
    val rules: List<Rule>? = null,
) {
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

    fun checkArch(): Boolean {

        val name = name.split(":").last()

        if (!name.contains("natives-") && !name.contains(OperatingSystem.CURRENT_OS.checkedName)) {
            return true
        }

        val os = OperatingSystem.CURRENT_OS

        if (os == OperatingSystem.LINUX) {
            return name.contains("natives-linux")
        }

        var natives = if (os == OperatingSystem.OSX) "macos" else os.checkedName
        if (Architecture.CURRENT != Architecture.X86_64) natives += "-${Architecture.CURRENT.checkedName}"
        natives = "natives-$natives"
        return name == natives
    }
}


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
    val os: Os? = null, // 傻逼 Mojang
)

@Serializable
data class Os(
    val name: String, // osx
)