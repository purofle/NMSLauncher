package dev.kdrag0n.monet.colors

import androidx.compose.ui.graphics.toArgb
import kotlin.math.roundToInt
import dev.kdrag0n.monet.colors.LinearSrgb.Companion.toLinearSrgb as realToLinearSrgb

data class Srgb(
    val r: Double,
    val g: Double,
    val b: Double,
) : Color {
    // Convenient constructors for quantized values
    constructor(r: Int, g: Int, b: Int) : this(
        r.toDouble() / 255.0,
        g.toDouble() / 255.0,
        b.toDouble() / 255.0,
    )
    constructor(color: Int) : this(
        androidx.compose.ui.graphics.Color(color).red.toDouble() / 255.0,
        androidx.compose.ui.graphics.Color(color).green.toDouble() / 255.0,
        androidx.compose.ui.graphics.Color(color).blue.toDouble() / 255.0,
    )

    override fun toLinearSrgb() = realToLinearSrgb()

    fun quantize8(): Int {
        return androidx.compose.ui.graphics.Color(
            (r * 255).roundToInt(),
            (g * 255).roundToInt(),
            (b * 255).roundToInt(),
        ).toArgb()
    }
}
