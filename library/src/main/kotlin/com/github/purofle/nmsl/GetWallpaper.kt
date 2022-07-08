package com.github.purofle.nmsl

import androidx.compose.ui.graphics.Color
import de.androidpit.colorthief.ColorThief
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import javax.imageio.ImageIO
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.isReadable
import kotlin.io.path.readText

var seedColorCache: Color? = null

fun getConfig(): String {
    val plasmaConfig = Path(System.getenv("HOME")) / ".config" / "plasma-org.kde.plasma.desktop-appletsrc"
    if (!plasmaConfig.isReadable()) {
        throw IllegalStateException("You are not upstream KDE user")
    }
    return plasmaConfig.readText()
}

fun getWallpaperPath(): File {
    val plasmaConfig = getConfig()
    var nextLineIsWallpaper = false
    var path = ""
    plasmaConfig.reader().useLines { lines ->
        lines.forEach {
            if (it.contains("[Wallpaper][org.kde.image]")) {
                nextLineIsWallpaper = true // 读取到[Wallpaper]行，下一行就是图片路径
            } else if (nextLineIsWallpaper) {
                path = it // 读取到图片路径
                return@useLines // break
            }
        }
    }
    path = path.split("=")[1]
    val logger = LoggerFactory.getLogger("getWallpaperPath")
    logger.debug("path: $path")
    val c = if (path.startsWith("file://")) {
        File(URI(path))
    } else {
        File(path)
    }
    logger.debug("c: $c")
    return if (path.startsWith("file://")) {
        File(URI(path))
    } else {
        File(path)
    }
}

fun getSeedColorFromWallpaper(): Color {
    val image = ImageIO.read(getWallpaperPath())
    val logger = LoggerFactory.getLogger("getSeedColorFromWallpaper")
    val (r, g, b) = ColorThief.getColor(image).toList()
    logger.debug("r: $r, g: $g, b: $b")
    return Color(r, g, b)
}

fun seedColor(): Color = if (seedColorCache == null) {
    val sc = getSeedColorFromWallpaper()
    seedColorCache = sc
    sc
} else {
    seedColorCache!!
}