package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.os.OperatingSystem
import kotlin.io.path.isReadable
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.readText

object GameManager {
    private val versionDir = OperatingSystem.getMinecraftWorkingDirectory("versions")
    fun getAllGame(): List<GameJson> {
        // 获取 versionDir 下的所有目录
        return versionDir.listDirectoryEntries()
            .filter { it.resolve("${it.name}.json").isReadable() }
            .map { it.resolve("${it.name}.json").readText().toJsonObject() }
    }
}