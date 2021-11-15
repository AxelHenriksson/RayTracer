package henaxel.raytracer

import henaxel.raytracer.materials.Material
import henaxel.raytracer.utils.Vec3
import kotlin.math.sqrt
import kotlin.random.Random

//TODO: Volumetricsphere probably shouldn't extend Sphere, not much is common between them
class VolumetricSphere(pos: Vec3, radius: Double, material: Material, var density: Double) : Sphere(pos, radius, material) {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult? {
        val oc = ray.pos - pos
        val a = ray.dir * ray.dir
        val b = oc * ray.dir
        val c = (oc * oc) - super.radius*super.radius
        val discriminant = b*b - a*c

        if (discriminant > 0) {
            val tClosest =  ((-b - sqrt(discriminant)) / a).coerceIn(tMin, tMax)
            val tFarthest = ((-b + sqrt(discriminant)) / a).coerceIn(tMin, tMax)
            if (tClosest == tFarthest) return null

            if ((Random.nextDouble(0.0,1.0)*(tFarthest-tClosest)*density) < 1.0) return null

            val t = Random.nextDouble(tClosest, tFarthest)  //TODO: Implement the ray scatteirng closer to the entry with higher density
            val hitPos = ray.pointAtParameter(t)
            val normal = (hitPos-pos)/super.radius
            val scatter = mat.scatter(ray, hitPos, normal)
            val uv = super.getUV(hitPos)

            return HitResult(hitPos, normal, t, scatter, mat.getAlbedo(uv[0], uv[1]))
        }
        return null
    }
}