package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import kotlin.io.path.createDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.reflect.KProperty

object Config {
    private val configPath by lazy {
        OS.getConfigPath().also { it.parent.createDirectories() }
    }
    val config by lazy { readConfig() }

    fun createConfig(config: NmslConfig) {
        configPath.writeText(config.toJsonString())
    }

    fun readConfig(): NmslConfig = configPath.readText().toJsonObject()

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): NmslConfig {
        return readConfig()
    }

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, nmslConfig: NmslConfig) {
        createConfig(nmslConfig)
    }
}