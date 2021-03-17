package henaxel.materials;

import henaxel.raytracer.Ray;
import henaxel.raytracer.node.InLink;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

class Emissive(private val albedo: Texture) : Material("Emissive", arrayOf(InLink("Albedo", null))) {

    constructor() : this(ConstantTexture(1.0, 0.0, 1.0))

    override fun scatter(r: Ray, pos: Vec3, n: Vec3): Ray? {
        TODO();
    }

    override fun scatter(r: Ray, pos: Vec3): Ray? {
        TODO();
    }

    override fun getAlbedo(u: Double, v: Double): Color? {
        TODO();
    }
}
