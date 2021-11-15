package henaxel.raytracer

import henaxel.raytracer.materials.Material
import henaxel.raytracer.utils.Vec3
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sqrt

open class Sphere(pos: Vec3, var radius: Double, material: Material) : Surface(pos, material) {
    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult? {
        val oc = ray.pos - pos
        val a = ray.dir * ray.dir
        val b = oc * ray.dir
        val c = (oc * oc) - radius*radius
        val discriminant = b*b - a*c

        if (discriminant > 0) {
            var t = (-b - sqrt(discriminant)) / a
            if (!(t < tMax && t > tMin)) {
                t = (-b + sqrt(discriminant)) / a
                if (!(t < tMax && t > tMin)) return null
            }
            val hitPos = ray.pointAtParameter(t)
            val normal = (hitPos-pos)/radius
            val scatter = mat.scatter(ray, hitPos, normal)
            val uv = getUV(hitPos)

            return HitResult(hitPos, normal, t, scatter, mat.getAlbedo(uv[0], uv[1]))
        }
        return null
    }

    override fun getUV(p: Vec3): Array<Double> {
        val p = (p - pos)/radius
        val phi = atan2(p.z, p.x)
        val theta = -asin(p.y)
        return arrayOf(
            1-(phi + PI) / (2*PI),
            (theta + PI/2) / PI
        )
    }

    override fun boundingBox(): AABB {
        return AABB(pos - Vec3(radius,radius,radius),pos + Vec3(radius,radius,radius))
    }
}