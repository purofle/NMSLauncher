package com.github.purofle.nmsl.platforms

import java.io.File

/**
 * @property home 家目录
 * @property cache 临时缓存
 * @property data 游戏储存目录
 */
interface Platforms {
    val home: File
    val cache: File
    val data: File
}

fun system(): Platforms {
    return when(System.getProperty("os.name").toLowerCase()) {
        "linux" -> Linux
        "windows" -> Windows
        else -> Linux
    }
}