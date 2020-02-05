package henaxel.raytracer.materials;

import henaxel.raytracer.utils.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTexture extends Texture {
    private BufferedImage image;
    
    public ImageTexture(BufferedImage image) {
        this.image = image;
    }
    @Override
    Color color(double u, double v, Vec3 hitPos) {
        return new Color(image.getRGB((int)(u*image.getWidth()), (int)(v*image.getHeight())));
    }
}
