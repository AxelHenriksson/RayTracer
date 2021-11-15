package henaxel.raytracer.materials

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3
import jdk.jshell.spi.ExecutionControl
import java.awt.Color
import kotlin.random.Random

class Dielectric(var albedo: Texture, var refIndex: Texture) : Material() {

    override fun scatter(ray: Ray, pos: Vec3, normal: Vec3): Ray {

        var outN = Vec3(0,0,0)
        var rRatio = 0.0
        var reflectProb = 0.0
        var cosine = 0.0
        val rIndex = refIndex.color(0.0,0.0).red/255.0

        if ((ray.dir * normal) > 0) {
            outN = -normal
            rRatio = rIndex
            cosine = rIndex * (ray.dir * normal) / ray.dir.length()
        } else {
            outN = normal
            rRatio = 1/rIndex
            cosine = -(ray.dir * normal / ray.dir.length())
        }

        val refracted = refract(ray.dir, outN, rRatio)
        reflectProb =
            if(refracted != null)
                schlick(cosine, rIndex)
            else
                1.0

        return if (Random.nextDouble(0.0, 1.0) <= reflectProb)
            Ray(pos, reflect(ray.dir, normal))
        else
            Ray(pos, refracted!!)
    }

    // Volumetric scatter
    override fun scatter(ray: Ray, pos: Vec3): Ray { throw NotImplementedError("Volumetric scattering for dielectric material not yet implemented") }

    override fun getAlbedo(u: Double, v: Double): Color { return albedo.color(u,v) }
}