package henaxel.raytracer.materials

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3

class Lambertian(var albedo: Texture): Material() {

    // Surface scattering
    override fun scatter(ray: Ray, pos: Vec3, normal: Vec3) = Ray(pos, normal + Vec3.randomInUnitSphere())
    // Volumetric scattering
    override fun scatter(ray: Ray, pos: Vec3) = Ray(pos, Vec3.randomInUnitSphere())

    override fun getAlbedo(u: Double, v: Double) = albedo.color(u,v)
}