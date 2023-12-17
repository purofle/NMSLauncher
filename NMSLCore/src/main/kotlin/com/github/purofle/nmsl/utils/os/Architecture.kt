package com.github.purofle.nmsl.utils.os

enum class Architecture(val checkedName: String) {
    X86_64("x86_64"),
    ARM64("arm64"),
    X86("x86");

    companion object {
        val CURRENT: Architecture
        private fun getCurrent(): Architecture {
            return when (System.getProperty("os.arch")) {
                "amd64", "x86_64" -> X86_64
                "aarch64" -> ARM64
                "i386" -> X86
                else -> throw IllegalStateException("Unknown architecture: ${System.getProperty("os.arch")}")
            }
        }

        init {
            CURRENT = getCurrent()
        }
    }
}