package com.github.purofle.nmsl.utils

import java.text.SimpleDateFormat

fun String.date(): String? {
    return if (this != "") {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
        val parse = simpleDateFormat.parse(this)
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse)
    } else {
        ""
    }
}