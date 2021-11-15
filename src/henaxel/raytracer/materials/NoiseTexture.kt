package henaxel.raytracer.materials

import java.awt.Color
import kotlin.math.sin

class NoiseTexture(var scale: Double, var depth: Int) : Texture() {
    var noise = Perlin()

    override fun color(u: Double, v: Double): Color {
        val perlin = (0.5 * (1 + noise.noise(u * scale, v * scale))).toFloat()
        val turbulence = noise.turb(u * scale, v * scale, depth).toFloat()
        val marble = (0.5 * (1 + sin(scale + 10 * noise.turb(u, v, depth)))).toFloat()
        return Color(marble, marble, marble)
    }
}