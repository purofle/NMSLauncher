package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.Constants.CORE_VERSION
import com.github.purofle.nmsl.utils.Constants.LAUNCHER_NAME
import com.github.purofle.nmsl.utils.os.OS
import java.nio.file.Path
import kotlin.io.path.pathString

object Argument {

    data class GameArgument(
        val username: String,
        val version: String = LAUNCHER_NAME,
        val assetIndex: String,
        val uuid: String,
        val accessToken: String,
        val clientId: String,
        val xuid: String,
        val userType: String = "msa",
        val versionType: String = LAUNCHER_NAME,
        val gameDir: Path = OS.minecraftWorkingDirectory()
    ) {
        fun parseGameArgument(): List<String> {
            val args = mutableListOf<String>()
            args.add("--username $username")
            args.add("--version $version")
            args.add("--gameDir '$gameDir'")
            args.add("--assetsDir '${OS.minecraftWorkingDirectory("assets")}'")
            args.add("--assetIndex $assetIndex")
            args.add("--uuid $uuid")
            args.add("--accessToken $accessToken")
            args.add("--clientId $clientId")
            args.add("--xuid $xuid")
            args.add("--userType $userType")
            args.add("--versionType $versionType")

            return args.toList()
        }
    }

    fun parseJvmArgument(
        nativesPath: Path,
        launcherName: String = LAUNCHER_NAME,
        launcherVersion: String = CORE_VERSION,
        artifacts: List<Artifact>,
        clientPath: Path
    ): List<String> {
        val cpPaths = artifacts.map {
            val lib = OS.minecraftWorkingDirectory("libraries", it.path)
            "'${lib.pathString}'"
        } + "'${clientPath.pathString}'"
        val args = mutableListOf<String>()
        args.add("-Djava.library.path='${nativesPath.pathString}'")
        args.add("-Dminecraft.launcher.brand=${launcherName}")
        args.add("-Dminecraft.launcher.version=${launcherVersion}")
        if (OS.CURRENT_OS == OS.OSX) {
            args.add("-XstartOnFirstThread")
        }
        args.add("-cp")
        args.add(cpPaths.joinToString(":"))

        return args.toList()
    }
}