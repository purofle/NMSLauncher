package com.github.purofle.nmsl.platforms

import java.io.File

object Linux: Platforms {
    override val home: File
        get() = File(System.getProperty("user.home"))
    override val data: File
        get() {
            val f = File(home, ".local/share/NMSL-Launcher")
            if (!f.exists()) { f.mkdirs() }
            return f
        }
}