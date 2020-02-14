package henaxel.raytracer.materials;

import henaxel.node.BaseNode;
import henaxel.node.OutLink;
import henaxel.raytracer.node.Node;
import henaxel.raytracer.utils.Vec3;

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
        return new Color(image.getRGB((int)(u*image.getWidth()), (int)(v*image.getHeight())));
    }
}
