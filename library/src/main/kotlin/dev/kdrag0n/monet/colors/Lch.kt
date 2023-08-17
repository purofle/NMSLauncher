package dev.kdrag0n.monet.colors

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

interface Lch : Color {
    val l: Double
    val c: Double
    val h: Double

    companion object {
        internal fun Lab.calcLchC() = sqrt(a*a + b*b)
        internal fun Lab.calcLchH(): Double {
            val hDeg = Math.toDegrees(atan2(b, a))
            return if (hDeg < 0) hDeg + 360 else hDeg
        }

        internal fun Lch.calcLabA() = c * cos(Math.toRadians(h))
        internal fun Lch.calcLabB() = c * sin(Math.toRadians(h))
    }
}
