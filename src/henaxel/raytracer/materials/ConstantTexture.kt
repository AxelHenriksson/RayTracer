package henaxel.raytracer.materials

import java.awt.Color

class ConstantTexture(var color: Color) : Texture() {
    override fun color(u: Double, v: Double): Color { return color }
}