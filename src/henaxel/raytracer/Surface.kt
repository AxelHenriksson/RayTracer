package henaxel.raytracer

import henaxel.raytracer.materials.Material
import henaxel.raytracer.utils.Vec3

abstract class Surface(var pos: Vec3, var mat: Material) : Hitable {
    abstract fun getUV(pos: Vec3): Array<Double>
}