package henaxel.raytracer.materials

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3
import henaxel.raytracer.utils.average
import java.awt.Color

class Metal(var roughness: Texture, var albedo: Texture) : Material() {

    // Surface sattering
    override fun scatter(ray: Ray, pos: Vec3, normal: Vec3): Ray {
        val reflected = reflect(ray.dir.norm(), normal)
        var scattered = (reflected + (Vec3.randomInUnitSphere() * roughness.color(0.0,0.0).average()/255.0))  //TODO: implement proper uv mapping

        var tries = 0
        while((scattered * normal) <= 0 && tries < 50) {
            scattered = (reflected + (Vec3.randomInUnitSphere() * roughness.color(0.0,0.0).average()/255.0))  //TODO: implement proper uv mapping
            tries++
        }
        return Ray(pos, scattered)
    }

    // Volumetric scattering
    override fun scatter(ray: Ray, pos: Vec3): Ray {
        val reflected = reflect(ray.dir, Vec3.randomInUnitSphere())
        val scattered = (reflected + (Vec3.randomInUnitSphere() * roughness.color(0.0,0.0).average()/255.0))  //TODO: implement proper uv mapping
        return Ray(pos, scattered)
    }

    override fun getAlbedo(u: Double, v: Double): Color {
        return albedo.color(u, v)
    }
}