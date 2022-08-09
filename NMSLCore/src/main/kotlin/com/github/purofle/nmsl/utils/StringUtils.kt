package com.github.purofle.nmsl.utils

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {
    fun String.formatTime(): Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(this)
}