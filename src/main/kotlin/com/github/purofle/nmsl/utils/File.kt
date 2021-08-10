package com.github.purofle.nmsl.utils

import java.io.File

fun File.mkdirs(list: List<String>) {
    list.forEach {
        File(this, it).mkdirs()
    }
}