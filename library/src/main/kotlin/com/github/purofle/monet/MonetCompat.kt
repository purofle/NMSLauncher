package com.github.purofle.monet

import de.androidpit.colorthief.ColorThief
import dev.kdrag0n.monet.colors.Srgb
import dev.kdrag0n.monet.factory.ColorSchemeFactory
import dev.kdrag0n.monet.theme.ColorScheme
import dev.kdrag0n.monet.theme.DynamicColorScheme
import dev.kdrag0n.monet.theme.MaterialYouTargets
import javax.imageio.ImageIO

object MonetCompat {
    @JvmStatic
    var colorSchemeFactory: ColorSchemeFactory? = null

    @JvmStatic
    val chromaMultiplier = 1.0
    @JvmStatic
    var accurateShades = true

    lateinit var colorThief: IntArray

    @JvmStatic
    val colorScheme: ColorScheme
    get() {
        return generateColorScheme(getBackgroundColor())
    }

    private fun getBackgroundColor(): IntArray {
        return if (!this::colorThief.isInitialized) {
            val wallpaper = this::class.java.getResource("/3840x2400.jpg")
            debug("get wallpaper: $wallpaper")
            colorThief = ColorThief.getColor(ImageIO.read(wallpaper))
            colorThief
        } else {
            colorThief
        }
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