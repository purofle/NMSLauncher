package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import kotlin.io.path.readText
import kotlin.io.path.writeText

object Config {
    private val configPath by lazy { OS.getConfigPath() }
    val config by lazy { readConfig() }

    fun createConfig(config: NmslConfig) {
        configPath.writeText(config.toJsonString())
    }

    private fun readConfig(): NmslConfig = configPath.readText().toJsonObject()
}