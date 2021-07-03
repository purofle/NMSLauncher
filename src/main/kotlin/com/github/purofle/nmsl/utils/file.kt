package com.github.purofle.nmsl.utils

import com.github.purofle.nmsl.game.McVersionManifest
import com.github.purofle.nmsl.game.Version
import java.io.File

/**
 * 检查文件/文件夹是否存在。
 * - 对于文件：
 * 存在则返回 true,不存在返回 false。
 * - 对于文件夹：
 * 存在则返回 true，不存在则创建文件夹并返回 true。
 * @param isDirectory 是否是文件夹
 */
fun File.check(isDirectory: Boolean): Boolean {
    return if (!isDirectory) {
        this.exists()
    } else {
        this.mkdirs()
        true
    }
}

fun McVersionManifest.toMap(): MutableMap<String, Version> {
    val map = mutableMapOf<String, Version>()
    this.versions.forEach { version ->
        map[version.id] = version
    }
    return map
}