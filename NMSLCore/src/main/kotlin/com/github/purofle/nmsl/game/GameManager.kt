package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.os.OS
import kotlin.io.path.div
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readText

object GameManager {
    private val versionDir = OS.minecraftWorkingDirectory() / "versions"
    val versions: List<String> by lazy {
        versionDir.listDirectoryEntries().map {
            it.fileName.toString()
        }
    }

    fun getVersionJson(version: String): GameJson = (versionDir / version / "$version.json").readText().toJsonObject()
}