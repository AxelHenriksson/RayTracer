package henaxel.materials;

import java.awt.Color
import kotlin.math.roundToInt

class CheckerTexture(private var t0: Texture, private var t1: Texture) : Texture("Checker Texture",null,null,null,null) {

    

    override fun color(u: Double, v: Double): Color {
        if (u.roundToInt() + v.roundToInt() -2*(u.roundToInt() * v.roundToInt()) < 0.5) {
            return t0.color(u, v);
        }
        return t1.color(u, v);
    }
    /*
    public Color color(double u, double v, Vec3 hitPos) {
        double sines = Math.sin(10*hitPos.x)*Math.sin(10*hitPos.y)*Math.sin(10*hitPos.z);
        if (sines < 0) {
            return odd.color(u, v, hitPos);
        } else {
            return even.color(u, v, hitPos);
        }
    }
     */
}
