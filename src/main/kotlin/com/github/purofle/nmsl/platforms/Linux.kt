package com.github.purofle.nmsl.platforms

import com.github.purofle.nmsl.utils.check
import java.io.File

object Linux {
    val home: String
    get() {
         return System.getenv("HOME")
     }
    val data = File(home, ".local/share/NMSL-Launcher")
}