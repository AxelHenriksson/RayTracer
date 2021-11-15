package henaxel.raytracer

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3
import java.awt.Color

data class HitResult(
    val pos: Vec3,
    val n: Vec3,
    val t: Double,
    val scattered: Ray?,
    val attenuation: Color
)