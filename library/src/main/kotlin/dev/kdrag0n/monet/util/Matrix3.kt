package dev.kdrag0n.monet.util

@JvmInline
value class Matrix3(
    private val values: DoubleArray,
) {
    constructor(
        n1: Double, n2: Double, n3: Double,
        n4: Double, n5: Double, n6: Double,
        n7: Double, n8: Double, n9: Double,
    ) : this(doubleArrayOf(
        n1, n2, n3,
        n4, n5, n6,
        n7, n8, n9,
    ))

    operator fun times(vec: Vector3) = values.let { m ->
        val (r1, r2, r3) = vec

        Vector3(
            m[0]*r1 + m[1]*r2 + m[2]*r3,
            m[3]*r1 + m[4]*r2 + m[5]*r3,
            m[6]*r1 + m[7]*r2 + m[8]*r3,
        )
    }

    operator fun times(matrix: Matrix3) = values.let { lhs ->
        matrix.values.let { rhs ->
            Matrix3(
                lhs[0] * rhs[0] + lhs[3] * rhs[1] + lhs[6] * rhs[2],
                lhs[1] * rhs[0] + lhs[4] * rhs[1] + lhs[7] * rhs[2],
                lhs[2] * rhs[0] + lhs[5] * rhs[1] + lhs[8] * rhs[2],
                lhs[0] * rhs[3] + lhs[3] * rhs[4] + lhs[6] * rhs[5],
                lhs[1] * rhs[3] + lhs[4] * rhs[4] + lhs[7] * rhs[5],
                lhs[2] * rhs[3] + lhs[5] * rhs[4] + lhs[8] * rhs[5],
                lhs[0] * rhs[6] + lhs[3] * rhs[7] + lhs[6] * rhs[8],
                lhs[1] * rhs[6] + lhs[4] * rhs[7] + lhs[7] * rhs[8],
                lhs[2] * rhs[6] + lhs[5] * rhs[7] + lhs[8] * rhs[8],
            )
        }
    }

    operator fun component1() = values[0]
    operator fun component2() = values[1]
    operator fun component3() = values[2]
    operator fun component4() = values[3]
    operator fun component5() = values[4]
    operator fun component6() = values[5]
    operator fun component7() = values[6]
    operator fun component8() = values[7]
    operator fun component9() = values[8]
}
