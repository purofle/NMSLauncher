package com.github.purofle.nmsl.utils

import java.io.File
import java.security.MessageDigest

object StringUtils {
    fun File.sha1String(): String {
        return MessageDigest.getInstance("SHA-1").digest(this.readBytes()).joinToString("") { "%02x".format(it) }
    }
}