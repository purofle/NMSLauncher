package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OperatingSystem
import kotlin.io.path.createFile
import kotlin.io.path.writeText

object LauncherConfig {
    private val configPath by lazy { OperatingSystem.getConfigPath() }

    fun createConfig(config: NmslConfig) {
        configPath.createFile().writeText(config.toJsonString())
    }
}