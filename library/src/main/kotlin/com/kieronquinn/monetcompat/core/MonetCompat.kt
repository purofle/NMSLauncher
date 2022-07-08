package com.kieronquinn.monetcompat.core

import androidx.compose.ui.graphics.Color
import dev.kdrag0n.monet.colors.Srgb
import dev.kdrag0n.monet.factory.ColorSchemeFactory
import dev.kdrag0n.monet.theme.ColorScheme
import dev.kdrag0n.monet.theme.DynamicColorScheme
import dev.kdrag0n.monet.theme.MaterialYouTargets


class MonetCompat(color: Color) {

    /**
         *  Set the multiplier of the chroma of the generated colors, defaults to `1.0`
         */
        var chromaMultiplier = 1.0

    /**
         *  Use a custom [ColorSchemeFactory] to create colors. Use [ColorSchemeFactory.getFactory]
         *  to create a factory, or extend it however you like.
         *
         *  You an use [ColorSchemeFactory] to use the ZCam gamut rather than OkLab, see
         *  kdrag0n's [GLColorTest](https://github.com/kdrag0n/glcolortest) for more info.
         */
        private var colorSchemeFactory: ColorSchemeFactory? = null

        /**
         *  Prefer accurate shades over chroma. Set to false to prefer chroma.
         *  See [DynamicColorScheme.transformColor]
         *
         */
        var accurateShades = true

        private var monetColors: ColorScheme = generateColorScheme(color)

    /**
     *  Returns the full set of Monet colors produced, or the default if they've not been
     *  generated / can't be generated
     */
    fun getMonetColors(): ColorScheme {
        return monetColors
    }

    private fun generateColorScheme(color: Color): ColorScheme {
        return colorSchemeFactory?.getColor(Srgb(color)) ?: run {
                DynamicColorScheme(
                    MaterialYouTargets(chromaMultiplier),
                    Srgb(color),
                    chromaMultiplier,
                    accurateShades
                )
            }
        }
    }