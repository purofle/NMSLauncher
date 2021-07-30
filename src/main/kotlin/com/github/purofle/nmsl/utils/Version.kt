package com.github.purofle.nmsl.utils

import com.github.purofle.nmsl.game.Version

fun List<Version>.toMap(): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()
    this.forEach {
        map[it.id] = it.url
    }
    return map
}