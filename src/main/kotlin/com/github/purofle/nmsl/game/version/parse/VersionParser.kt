package com.github.purofle.nmsl.game.version.parse

import com.github.purofle.nmsl.game.version.Library
import com.github.purofle.nmsl.game.version.Natives
import com.github.purofle.nmsl.game.version.Version
import com.github.purofle.nmsl.platforms.OperatingSystem

class VersionParser(val version: Version) {
    fun libraries(): List<Library> {
        val libraries = version.libraries
        val os = OperatingSystem.CURRENT_OS.checkedName
        val commonLibraries = libraries.filter {
            it.natives == null
        }
        val nativeLibraries = libraries.filter {
            it.natives.toString().contains("${os}=natives-${os}")
        }
        return commonLibraries+nativeLibraries
    }
}