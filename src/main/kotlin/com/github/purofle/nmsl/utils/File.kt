package com.github.purofle.nmsl.utils

import java.io.File

/**
 * 在 `File` 目录下创建指定文件夹
 * @param list 要创建的文件夹
 */
fun File.mkdirs(vararg list: String) {
    list.forEach {
        File(this, it).mkdirs()
    }
}