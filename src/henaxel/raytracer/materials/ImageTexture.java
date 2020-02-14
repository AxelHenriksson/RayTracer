package henaxel.raytracer.materials;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTexture extends Texture {
    private BufferedImage image;
    
    public ImageTexture(BufferedImage image) {
        super("Image Texture", null, null, null, null);
        this.image = image;
    }
    public ImageTexture() { this(null); }

    @Override
    public Color color(double u, double v) {
        return new Color(image.getRGB((int)(u*image.getWidth()), (int)(v*image.getHeight())));
    }
}
