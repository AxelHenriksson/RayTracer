package henaxel.raytracer.materials

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3
import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Material {

    // Surface scattering
    abstract fun scatter(ray: Ray, pos: Vec3, normal: Vec3): Ray?
    // Volumetric scattering
    abstract fun scatter(ray: Ray, pos: Vec3): Ray?

    abstract fun getAlbedo(u: Double, v: Double): Color


    companion object {
        fun reflect(incident: Vec3, normal: Vec3): Vec3 {
            return incident.norm() - ((normal * (incident.norm() * normal) * 2).norm())
        }

        fun schlick(cosine: Double, refIndex: Double): Double {
            val r0 = ((1-refIndex)/(1+refIndex))*((1-refIndex)/(1+refIndex))
            return r0 + (1-r0)*((1-cosine).pow(5))
        }

        fun refract(incident: Vec3, normal: Vec3, rRatio: Double): Vec3? {
            val uv = incident.norm()
            val dt = uv * normal
            val discriminant = 1.0 - ((rRatio * rRatio) * (1 - (dt * dt)))
            return if (discriminant > 0) {
                ((uv - (normal * dt)) * rRatio) - (normal * sqrt(discriminant))
            } else {
                null
            }
        }
    }
}