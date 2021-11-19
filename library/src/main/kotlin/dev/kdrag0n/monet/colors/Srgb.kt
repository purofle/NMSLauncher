package dev.kdrag0n.monet.colors

import kotlin.math.roundToInt
import dev.kdrag0n.monet.colors.LinearSrgb.Companion.toLinearSrgb as realToLinearSrgb

data class Srgb(
    val r: Double,
    val g: Double,
    val b: Double,
) : Color {
    // Convenient constructors for quantized values
    constructor(r: Int, g: Int, b: Int) : this(
        r.toDouble(),
        g.toDouble(),
        b.toDouble(),
    )
    constructor(color: Int) : this(
        java.awt.Color.red.rgb,
        java.awt.Color.green.rgb,
        java.awt.Color.blue.rgb,
    )

    override fun toLinearSrgb() = realToLinearSrgb()

    fun quantize8(): Int {
        return java.awt.Color(
            quantize8(r),
            quantize8(g),
            quantize8(b),
        ).rgb
    }

    companion object {
        // Clamp out-of-bounds values
        private fun quantize8(n: Double) = (n * 255.0).roundToInt().coerceIn(0, 255)
    }
}
