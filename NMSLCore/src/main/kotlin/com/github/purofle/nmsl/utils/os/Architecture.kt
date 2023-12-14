package com.github.purofle.nmsl.utils.os

enum class Architecture(val checkedName: String) {
    X86_64("x86_64"),
    AARCH64("aarch_64"),
    ARM64("arm64"),
    X86("x86");

    companion object {
        val CURRENT: Architecture
        private fun getCurrent(): Architecture {
            return when (System.getProperty("os.arch")) {
                "amd64" -> X86_64
                "x86_64" -> X86_64
                "aarch64" -> if (OperatingSystem.CURRENT_OS == OperatingSystem.LINUX) AARCH64 else ARM64 // macOS 的 aarch64 依赖叫 arm64
                "i386" -> X86
                else -> throw IllegalStateException("Unknown architecture: ${System.getProperty("os.arch")}")
            }
        }

        init {
            CURRENT = getCurrent()
        }
    }
}