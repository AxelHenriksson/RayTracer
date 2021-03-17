package henaxel.materials;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTexture extends Texture {
    private BufferedImage image;
    
    public ImageTexture(BufferedImage image) {
        super("Image Texture", null, null, null, null);
        this.image = image;
    }

    @Override
    public Color color(double u, double v) {
        if (image == null) {
            return new Color(255,0,255);

        }

        return new Color(image.getRGB((int) (u * image.getWidth()), (int) (v * image.getHeight())));
    }
}
