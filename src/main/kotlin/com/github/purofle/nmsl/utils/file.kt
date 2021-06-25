package com.github.purofle.nmsl.utils

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