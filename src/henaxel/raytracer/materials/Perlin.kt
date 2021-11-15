package henaxel.raytracer.materials

import henaxel.raytracer.utils.Vec3
import kotlin.math.abs
import kotlin.math.floor
import kotlin.random.Random


class Perlin {
    private val ranVec = perlinGenerate()
    private val permX = perlinGeneratePerm()
    private val permY = perlinGeneratePerm()
    private val permZ = perlinGeneratePerm()

    fun noise(pos: Vec3): Double {
        val u = pos.x - floor(pos.x)
        val v = pos.y - floor(pos.y)
        val w = pos.z - floor(pos.z)
        val i = floor(pos.x).toInt()
        val j = floor(pos.y).toInt()
        val k = floor(pos.z).toInt()
        val c: Array<Array<Array<Vec3>>> = Array<Array<Array<Vec3>>>(2) {
            Array<Array<Vec3>>(2) {
                arrayOf(
                    Vec3(0,0,0),
                    Vec3(0,0,0)
                )
            }
        }
        for (di in 0..1) {
            for (dj in 0..1) {
                for (dk in 0..1) {
                    c[di][dj][dk] =
                        ranVec.get(permX[i + di and 255] xor permY[j + dj and 255] xor permZ[k + dk and 255])
                }
            }
        }
        return Perlin.perlinInterp(c, u, v, w)
    }

    fun noise(u: Double, v: Double): Double {
        val i = floor(u).toInt()
        val j = floor(v).toInt()
        val k = 0
        val c = Array(2) {
            Array(2) {
                arrayOf(
                    Vec3(0,0,0),
                    Vec3(0,0,0)
                )
            }
        }
        for (di in 0..1) {
            for (dj in 0..1) {
                for (dk in 0..1) {
                    c[di][dj][dk] =
                        ranVec.get(permX[i + di and 255] xor permY[j + dj and 255] xor permZ[k + dk and 255])
                }
            }
        }
        return perlinInterp(c, u, v, 0.0)
    }

    fun turb(p: Vec3, depth: Int): Double {
        var accum = 0.0
        var tmp: Vec3 = p
        var weight = 1.0
        for (i in 0 until depth) {
            accum += weight * noise(tmp)
            weight *= 0.5
            tmp *= 2
        }
        return abs(accum)
    }

    fun turb(u: Double, v: Double, depth: Int): Double {
        var accum = 0.0
        var i = u
        var j = v
        var weight = 1.0
        for (k in 0 until depth) {
            accum += weight * noise(i, j)
            weight *= 0.5
            i *= 2.0
            j *= 2.0
        }
        return abs(accum)
    }

    companion object {

        fun permute(p: Array<Int>) {
            for (i in p.size - 1 downTo 1) {
                val target = (Random.nextDouble() * (i + 1)).toInt()
                val tmp = p[i]
                p[i] = p[target]
                p[target] = tmp
            }
        }

        fun perlinGenerate(): Array<Vec3> {
            val p = Array(256) { Vec3(0,0,0) }
            for (i in 0..255) {
                p[i] = Vec3(-1 + 2 * Random.nextDouble(), -1 + 2 * Random.nextDouble(), -1 + 2 * Random.nextDouble()).norm()
            }
            return p
        }

        fun perlinGeneratePerm(): Array<Int> {
            val p = Array(256) {0}
            for (i in 0..255) {
                p[i] = i
            }
            permute(p)
            return p
        }

        fun perlinInterp(c: Array<Array<Array<Vec3>>>, u: Double, v: Double, w: Double): Double {
            val uu = u * u * (3 - 2 * u)
            val vv = v * v * (3 - 2 * v)
            val ww = w * w * (3 - 2 * w)
            var accum = 0.0
            for (i in 0..1) for (j in 0..1) for (k in 0..1) {
                val weightV = Vec3(u - i, v - j, w - k)
                accum += (i * uu + (1 - i) * (1 - uu)) *
                        (j * vv + (1 - j) * (1 - vv)) *
                        (k * ww + (1 - k) * (1 - ww)) * (c[i][j][k] * weightV)
            }
            return accum
        }

    }
}