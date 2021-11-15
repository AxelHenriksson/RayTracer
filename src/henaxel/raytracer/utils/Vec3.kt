package henaxel.raytracer.utils

data class Vec3(var x:Double, var y:Double, var z: Double) {

    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    operator fun get(index: Int): Double {
        return when(index) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw ArrayIndexOutOfBoundsException("Attempted to access index ${index} of 3 dimensional vector!")
        }
    }

    operator fun plus(other: Vec3): Vec3 {
        return Vec3(this.x+other.x,this.y+other.y, this.z+other.z)
    }
    operator fun minus(other: Vec3): Vec3 {
        return Vec3(this.x-other.x, this.y-other.y, this.z-other.z)
    }
    operator fun unaryMinus(): Vec3 {
        return Vec3(-this.x, -this.y, -this.z)
    }
    operator fun div(denominator: Double): Vec3 {
        return Vec3(this.x/denominator, this.y/denominator, this.z/denominator)
    }
    operator fun div(denominator: Int): Vec3 {
        return Vec3(this.x/denominator, this.y/denominator, this.z/denominator)
    }
    operator fun times(factor: Double): Vec3 {
        return Vec3(this.x*factor, this.y*factor, this.z*factor)
    }
    operator fun times(factor: Int): Vec3 {
        return Vec3(this.x*factor, this.y*factor, this.z*factor)
    }
    operator fun times(other: Vec3): Double {
        return this.x*other.x + this.y*other.y + this.z*other.z
    }
    infix fun x(other: Vec3): Vec3 {
        return Vec3(
            this.y*other.z-this.z*other.y,
            this.x*other.z-this.z*other.x,
            this.x*other.y-this.y*other.x)
    }

    fun length(): Double {
        return Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z)
    }
    fun norm(): Vec3 {
        return this/this.length()
    }


    companion object {
        fun randomInUnitSphere(): Vec3 {
            var p: Vec3
            do {
                p = (Vec3(Math.random(), Math.random(), Math.random()) * 2.0) - Vec3(1, 1, 1)
            } while (p.length() * p.length() >= 1.0)
            return p
        }

        fun randomInUnitDisk(): Vec3 {
            var p = (Vec3(Math.random(), Math.random(), 0.0) * 2.0) - Vec3(1, 1, 0)
            while ((p * p) >= 1.0) {
                p = (Vec3(Math.random(), Math.random(), 0.0) * 2.0) - Vec3(1, 1, 0)
            }
            return p
        }
    }
}