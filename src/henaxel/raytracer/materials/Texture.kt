package henaxel.raytracer.materials

import java.awt.Color

abstract class Texture {
    abstract fun color(u: Double, v: Double): Color
}