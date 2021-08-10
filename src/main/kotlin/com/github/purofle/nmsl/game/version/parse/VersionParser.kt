package com.github.purofle.nmsl.game.version.parse

import com.github.purofle.nmsl.game.version.Library
import com.github.purofle.nmsl.game.version.Version

class VersionParser(val version: Version) {
    fun libraries(): List<Library> {
        val libraries = version.libraries
        libraries.filter {
            it.natives == null
        }
        return libraries
    }
}