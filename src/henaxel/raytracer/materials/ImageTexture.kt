package henaxel.raytracer.materials

import java.awt.Color
import java.awt.image.BufferedImage

class ImageTexture(var image: BufferedImage) : Texture() {

    override fun color(u: Double, v: Double): Color = Color(image.getRGB((u*image.width).toInt(), (v*image.height).toInt()))

}