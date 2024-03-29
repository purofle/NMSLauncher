package dev.kdrag0n.monet.colors

import kotlin.math.cbrt

data class CieLab(
    override val l: Double,
    override val a: Double,
    override val b: Double,
) : Lab {
    override fun toLinearSrgb() = toCieXyz().toLinearSrgb()

    fun toCieXyz(): CieXyz {
        val lp = (l + 16.0) / 116.0

        return CieXyz(
            x = Illuminants.D65.x * fInv(lp + (a / 500.0)),
            y = Illuminants.D65.y * fInv(lp),
            z = Illuminants.D65.z * fInv(lp - (b / 200.0)),
        )
    }

    companion object {
        private fun f(x: Double) = if (x > 216.0/24389.0) {
            cbrt(x)
        } else {
            x / (108.0/841.0) + 4.0/29.0
        }

        private fun fInv(x: Double) = if (x > 6.0/29.0) {
            x * x * x
        } else {
            (108.0/841.0) * (x - 4.0/29.0)
        }

    }
}
