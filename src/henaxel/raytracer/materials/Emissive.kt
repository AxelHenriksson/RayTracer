package henaxel.raytracer.materials

import henaxel.raytracer.Ray
import henaxel.raytracer.utils.Vec3
import java.awt.Color

class Emissive(var texture: Texture): Material() {

    // Surface scattering
    override fun scatter(ray: Ray, pos: Vec3, normal: Vec3): Ray? = null
    // Volumetric scattering
    override fun scatter(ray: Ray, pos: Vec3): Ray? = null

    override fun getAlbedo(u: Double, v: Double): Color {
        return texture.color(u,v)
    }
}