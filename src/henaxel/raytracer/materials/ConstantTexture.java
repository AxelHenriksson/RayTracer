package henaxel.raytracer.materials;

import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class ConstantTexture extends Texture {
    private Color color;
    
    public ConstantTexture(Color color) {
        this.color = color;
    }
    public ConstantTexture(double r, double g, double b) {
        this.color = new Color((float)r, (float)g, (float)b);
    }
    
    @Override
    Color color(double u, double v, Vec3 hitPos) {
        return color;
    }
}
