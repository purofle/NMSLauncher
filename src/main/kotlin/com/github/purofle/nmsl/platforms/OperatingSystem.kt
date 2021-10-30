package com.github.purofle.nmsl.platforms

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

enum class OperatingSystem(val checkedName: String) {
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
        var CURRENT_OS: OperatingSystem

        private fun getWorkingDirectory(vararg folder: String): Path {
            val home = System.getProperty("user.home", ".")
            return when (CURRENT_OS) {
                LINUX -> Paths.get(home, *folder)
                WINDOWS -> {
                    val appdata = System.getenv("APPDATA")
                    Paths.get(appdata ?: home, *folder)
                }
                OSX -> Paths.get(home, "Library", "Application Support", *folder)
                else -> Paths.get(home, *folder)
            }
        }

        fun getLauncherWorkingDirectory(): Path {
             return when (CURRENT_OS) {
                LINUX -> getWorkingDirectory(".local", "share", "NMSL-Launcher")
                else ->  getWorkingDirectory("NMSL-Launcher")
            }
        }

        fun getLauncherWorkingDirectory(vararg folder: String): Path {
            return when (CURRENT_OS) {
                LINUX -> getWorkingDirectory(".local", "share", "NMSL-Launcher", *folder)
                else ->  getWorkingDirectory("NMSL-Launcher", *folder)
            }
        }

        fun getMinecraftWorkingDirectory() = getLauncherWorkingDirectory().toFile()

        init {
            val name = System.getProperty("os.name").lowercase(Locale.US)
            CURRENT_OS = if (name.contains("win")) WINDOWS else if (name.contains("mac")) OSX else if (name.contains("solaris") || name.contains("linux") || name.contains("unix") || name.contains(
                    "sunos"
                )
            ) LINUX else UNKNOWN
        }
    }
}