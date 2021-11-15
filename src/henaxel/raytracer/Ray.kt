package henaxel.raytracer

import henaxel.raytracer.utils.Vec3

data class Ray(val pos: Vec3, val dir: Vec3) {
    fun pointAtParameter(t: Double): Vec3 { return pos + (dir*t) }
}