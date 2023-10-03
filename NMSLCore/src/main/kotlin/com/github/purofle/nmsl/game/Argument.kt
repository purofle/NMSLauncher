package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.Constants.CORE_VERSION
import com.github.purofle.nmsl.utils.Constants.LAUNCHER_NAME
import com.github.purofle.nmsl.utils.os.OperatingSystem
import java.nio.file.Path
import java.util.*
import kotlin.io.path.pathString

object Argument {
    fun parseJvmArgument(
        nativesPath: Path,
        launcherName: String = LAUNCHER_NAME,
        launcherVersion: String = CORE_VERSION,
        artifacts: List<Artifact>,
        clientPath: Path
    ): String {
        val cpPaths = artifacts.map {
            val lib = OperatingSystem.getMinecraftWorkingDirectory("libraries", it.path)
            "'${lib.pathString}'"
        } + "'${clientPath.pathString}'"
        val args = mutableListOf<String>()
        args.add("-Djava.library.path='${nativesPath.pathString}'")
        args.add("-Dminecraft.launcher.brand=${launcherName}")
        args.add("-Dminecraft.launcher.version=${launcherVersion}")
        if (OperatingSystem.CURRENT_OS == OperatingSystem.OSX) {
            args.add("-XstartOnFirstThread")
        }
        args.add("-cp ${cpPaths.joinToString(":")}")

        return args.joinToString(" ")
    }

    fun parseGameArgument(
        username: String,
        version: String = LAUNCHER_NAME,
        assetIndex: String,
        uuid: UUID = UUID.randomUUID(),
        accessToken: String,
        clientId: String,
        xuid: String,
        userType: String = "msa",
        versionType: String = LAUNCHER_NAME,
        gameDir: Path = OperatingSystem.getMinecraftWorkingDirectory()
    ): String {
        val args = mutableListOf<String>()
        args.add("--username $username")
        args.add("--version $version")
        args.add("--gameDir '$gameDir'")
        args.add("--assetsDir '${OperatingSystem.getMinecraftWorkingDirectory("assets")}'")
        args.add("--assetIndex $assetIndex")
        args.add("--uuid $uuid")
        args.add("--accessToken $accessToken")
        args.add("--clientId $clientId")
        args.add("--xuid $xuid")
        args.add("--userType $userType")
        args.add("--versionType $versionType")

        return args.joinToString(" ")
    }
}