package com.github.purofle.nmsl

import androidx.compose.ui.graphics.Color
import de.androidpit.colorthief.ColorThief
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URI
import java.sql.Time
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
    val logger = LogManager.getLogger("getWallpaperPath")
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
    val logger = LogManager.getLogger("getSeedColorFromWallpaper")
//    val wallpaperFile = try {
//        getWallpaperPath()
//    } catch (e: Exception) {
//		e.printStackTrace()
//		logger.error("use default wallpaper")
//        File({}.javaClass.getResource("/3840x2400.jpg")?.file ?: throw IllegalStateException("No wallpaper found"))
//    }
	val wallpaperFile = File({}.javaClass.getResource("/img.png")?.file!!)
    val image = try {
        if (wallpaperFile.isDirectory) {
            val wallpaper = getWallpaperPath().listAllFile().first { it.name.contains(Regex(".[png][jpg]")) }
			logger.info("使用系统壁纸")
            logger.info("use wallpaper: $wallpaper")
            ImageIO.read(wallpaper)
        } else {
			logger.info("使用win11壁纸")
            ImageIO.read(wallpaperFile)
        }
    } catch (e: Exception) {
        logger.error(e.toString())
        return Color(0x66ccff)
    }
    val (r, g, b) = ColorThief.getColor(image).toList()
    logger.debug("r: $r, g: $g, b: $b")
    return Color(r, g, b)
}

fun seedColor(): Color = if (seedColorCache == null) {
	val logger = LogManager.getLogger("getSeedColorFromWallpaper")
	val t1 = System.currentTimeMillis()
    val sc = getSeedColorFromWallpaper()
	val t2 = System.currentTimeMillis()
	logger.info("时间${t2-t1}")
    seedColorCache = sc
    sc
} else {
    seedColorCache!!
}

val files: MutableList<File> = mutableListOf()
fun File.listAllFile(): List<File> {
    this.listFiles()!!.forEach {
        if (it.isDirectory) {
            it.listAllFile()
        } else {
            files.add(it)
        }
    }
    return files.toList()
}
