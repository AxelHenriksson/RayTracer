package henaxel.raytracer.utils

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class ImageReader {

    companion object{
        fun loadBufferedImage(path: String): BufferedImage? {
            var loaded: BufferedImage? = null
            try {
                val file = File(path)
                loaded = ImageIO.read(file)
            } catch(e: IOException) {
                println("\"henaxel.raytracer.utils.ImageReader: IOException loading image!\"")
                e.printStackTrace()
            }
            return loaded
        }
    }

}