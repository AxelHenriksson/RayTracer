package henaxel.materials;


import java.awt.*;

class ConstantTexture(private var color: Color): Texture("Constant Texture", null, null, null, null) {

    constructor(r: Double, g: Double, b: Double) : this(Color(r.toFloat(), g.toFloat(), b.toFloat()))
    

    override fun color(u: Double, v: Double): Color {
        return color;
    }
}
