package com.github.purofle.nmsl.game

object Argument {

//    data class GameArgument(
//        val username: String,
//        val version: String = LAUNCHER_NAME,
//        val assetIndex: String,
//        val uuid: String,
//        val accessToken: String,
//        val clientId: String,
//        val xuid: String,
//        val userType: String = "msa",
//        val versionType: String = LAUNCHER_NAME,
//        val gameDir: Path = OperatingSystem.getMinecraftWorkingDirectory()
//    ) {
//        fun parseGameArgument(): String {
//            val args = mutableListOf<String>()
//            args.add("--username $username")
//            args.add("--version $version")
//            args.add("--gameDir '$gameDir'")
//            args.add("--assetsDir '${OperatingSystem.getMinecraftWorkingDirectory("assets")}'")
//            args.add("--assetIndex $assetIndex")
//            args.add("--uuid $uuid")
//            args.add("--accessToken $accessToken")
//            args.add("--clientId $clientId")
//            args.add("--xuid $xuid")
//            args.add("--userType $userType")
//            args.add("--versionType $versionType")
//
//            return args.joinToString(" ")
//        }
//    }

//    fun parseJvmArgument(
//        nativesPath: Path,
//        launcherName: String = LAUNCHER_NAME,
//        launcherVersion: String = CORE_VERSION,
//        artifacts: List<Artifact>,
//        clientPath: Path
//    ): String {
//        val cpPaths = artifacts.map {
//            val lib = OperatingSystem.getMinecraftWorkingDirectory("libraries", it.path)
//            "'${lib.pathString}'"
//        } + "'${clientPath.pathString}'"
//        val args = mutableListOf<String>()
//        args.add("-Djava.library.path='${nativesPath.pathString}'")
//        args.add("-Dminecraft.launcher.brand=${launcherName}")
//        args.add("-Dminecraft.launcher.version=${launcherVersion}")
//        if (OperatingSystem.CURRENT_OS == OperatingSystem.OSX) {
//            args.add("-XstartOnFirstThread")
//        }
//        args.add("-cp ${cpPaths.joinToString(":")}")
//
//        return args.joinToString(" ")
//    }
}