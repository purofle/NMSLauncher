package com.github.purofle.nmsl.utils.os

import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * @author huanghongxun
 */
enum class OS(val checkedName: String) {
    /**
     * Microsoft Windows.
     */
    WINDOWS("windows"),

    /**
     * Linux and Unix like OS, including Solaris.
     */
    LINUX("linux"),

    /**
     * Mac OS X.
     */
    OSX("osx"),

    /**
     * Unknown operating system.
     */
    UNKNOWN("universal");

    companion object {

        /**
         * The current operating system.
         */
        var CURRENT_OS: OS

        private fun getWorkingDirectory(vararg folder: String): Path {
            val home = System.getProperty("user.home", ".")
            return when (CURRENT_OS) {
                LINUX -> Paths.get(home, ".local", "share", "NMSLauncher", *folder)
                WINDOWS -> {
                    val appdata = System.getenv("APPDATA")
                    Paths.get(appdata ?: home, "NMSLauncher", *folder)
                }

                OSX -> Paths.get(home, "Library", "Application Support", "NMSLauncher", *folder)
                else -> Paths.get(home, *folder)
            }
        }

        fun minecraftWorkingDirectory(vararg folder: String): Path = getWorkingDirectory(".minecraft", *folder)
        fun getConfigPath(): Path = getWorkingDirectory("nmsl.json")

        init {
            val name = System.getProperty("os.name").lowercase(Locale.US)
            CURRENT_OS =
                when {
                    name.contains("win") -> WINDOWS
                    name.contains("mac") -> OSX
                    name.contains("linux") -> LINUX
                    else -> UNKNOWN
                }
        }
    }
}