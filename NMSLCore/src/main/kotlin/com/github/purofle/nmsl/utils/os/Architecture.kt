package com.github.purofle.nmsl.utils.os

enum class Architecture(name: String) {
    X86("x86"),
    X86_64("x86_64");

    companion object {
        fun getCurrent(): Architecture {
            return when (System.getProperty("os.arch")) {
                "x86" -> X86
                "amd64" -> X86_64
                else -> throw IllegalStateException("Unknown architecture: ${System.getProperty("os.arch")}")
            }
        }
    }
}