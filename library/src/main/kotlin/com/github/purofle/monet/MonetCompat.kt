package com.github.purofle.monet

import de.androidpit.colorthief.ColorThief
import dev.kdrag0n.monet.colors.Srgb
import dev.kdrag0n.monet.factory.ColorSchemeFactory
import dev.kdrag0n.monet.theme.ColorScheme
import dev.kdrag0n.monet.theme.DynamicColorScheme
import dev.kdrag0n.monet.theme.MaterialYouTargets
import java.io.File
import java.net.URI
import javax.imageio.ImageIO

object MonetCompat {
    @JvmStatic
    var colorSchemeFactory: ColorSchemeFactory? = null

    @JvmStatic
    val chromaMultiplier = 1.0
    @JvmStatic
    var accurateShades = true

    @JvmStatic
    val colorScheme: ColorScheme
    get() {
        return generateColorScheme(getBackgroundColor())
    }

    private fun getBackgroundColor(): IntArray {
        val wallpaper = "bash ${this.javaClass.classLoader.getResource("getwallpaper.sh")!!.path}".run()
        debug(this.javaClass.classLoader.getResource("getwallpaper.sh")!!.toString())
        debug("get wallpaper: $wallpaper")
        var file = if (wallpaper.startsWith("file:///")) {
            File(URI(wallpaper.trimIndent()))
        } else {
            File(wallpaper.trimIndent())
        }
        if (!file.isFile) {
            debug("获取壁纸失败，使用默认图片")
            file = File("/usr/share/wallpapers/Cascade/contents/screenshot.png")
        }
        return ColorThief.getColor(ImageIO.read(file))!!
    }

    private fun generateColorScheme(primaryColor: IntArray): ColorScheme {
        val d = DynamicColorScheme(
            MaterialYouTargets(chromaMultiplier),
            Srgb(primaryColor[0], primaryColor[1], primaryColor[2]),
            chromaMultiplier,
            accurateShades
        )
        return colorSchemeFactory?.getColor(Srgb(primaryColor[0], primaryColor[1], primaryColor[2])) ?: d
    }

    fun getMonetColors(): ColorScheme {
        return colorScheme
    }
}