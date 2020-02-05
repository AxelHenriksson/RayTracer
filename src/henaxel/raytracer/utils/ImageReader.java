package henaxel.raytracer.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {

    public ImageReader() {}

    public static BufferedImage loadBufferedImage(String path) {
        BufferedImage loaded = null;
        try {
            File file = new File(path);
            loaded = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("henaxel.raytracer.utils.ImageReader: IOException loading image!");
            e.printStackTrace();
        }
        return loaded;
    }
}
