package henaxel.raytracer.materials

import java.awt.Color
import java.lang.Math.round
import kotlin.math.roundToInt

class CheckerTexture(var odd: Texture, var even: Texture) : Texture() {

    override fun color(u: Double, v: Double): Color {
        return if (u.roundToInt() + v.roundToInt() - 2 * (u.roundToInt() * v.roundToInt()) < 0.5) {
            odd.color(u, v)
        } else {
            even.color(u, v)
        }
    }
}