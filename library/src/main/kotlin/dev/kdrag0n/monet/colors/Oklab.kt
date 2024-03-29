package dev.kdrag0n.monet.colors

import dev.kdrag0n.monet.util.cube
import kotlin.math.cbrt

data class Oklab(
    override val l: Double,
    override val a: Double,
    override val b: Double,
) : Lab {
    override fun toLinearSrgb(): LinearSrgb {
        val l = oklabToL(this)
        val m = oklabToM(this)
        val s = oklabToS(this)

        return LinearSrgb(
            r = +4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s,
            g = -1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s,
            b = -0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s,
        )
    }

    companion object {
        private fun lmsToOklab(l: Double, m: Double, s: Double): Oklab {
            val lp = cbrt(l)
            val mp = cbrt(m)
            val sp = cbrt(s)

            return Oklab(
                l = 0.2104542553 * lp + 0.7936177850 * mp - 0.0040720468 * sp,
                a = 1.9779984951 * lp - 2.4285922050 * mp + 0.4505937099 * sp,
                b = 0.0259040371 * lp + 0.7827717662 * mp - 0.8086757660 * sp,
            )
        }

        // Avoid arrays to minimize garbage
        private fun oklabToL(lab: Oklab) = cube(lab.l + 0.3963377774 * lab.a + 0.2158037573 * lab.b)
        private fun oklabToM(lab: Oklab) = cube(lab.l - 0.1055613458 * lab.a - 0.0638541728 * lab.b)
        private fun oklabToS(lab: Oklab) = cube(lab.l - 0.0894841775 * lab.a - 1.2914855480 * lab.b)

        fun LinearSrgb.toOklab() = lmsToOklab(
            l = 0.4122214708 * r + 0.5363325363 * g + 0.0514459929 * b,
            m = 0.2119034982 * r + 0.6806995451 * g + 0.1073969566 * b,
            s = 0.0883024619 * r + 0.2817188376 * g + 0.6299787005 * b,
        )

        fun CieXyz.toOklab() = lmsToOklab(
            l = 0.8189330101 * x + 0.3618667424 * y - 0.1288597137 * z,
            m = 0.0329845436 * x + 0.9293118715 * y + 0.0361456387 * z,
            s = 0.0482003018 * x + 0.2643662691 * y + 0.6338517070 * z,
        )
    }
}
