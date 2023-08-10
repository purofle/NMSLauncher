package dev.kdrag0n.monet.colors

import androidx.compose.ui.graphics.toArgb
import dev.kdrag0n.monet.colors.LinearSrgb.Companion.realToLinearSrgb
import kotlin.math.roundToInt
import androidx.compose.ui.graphics.Color as ComposeColor

data class Srgb(
    val r: Double,
    val g: Double,
    val b: Double,
) : Color {
    constructor(color: Int) : this(
        ComposeColor(color).red.toDouble() / 255.0,
        ComposeColor(color).green.toDouble() / 255.0,
        ComposeColor(color).blue.toDouble() / 255.0,
    )

    constructor(color: ComposeColor) : this(
        color.red.toDouble() * 255.0,
        color.green.toDouble() * 255.0,
        color.blue.toDouble() * 255.0,
    )

    override fun toLinearSrgb(): LinearSrgb = this.realToLinearSrgb()

    fun quantize8(): Int {
        return ComposeColor(
            (r * 255).roundToInt(),
            (g * 255).roundToInt(),
            (b * 255).roundToInt(),
        ).toArgb()
    }
}
