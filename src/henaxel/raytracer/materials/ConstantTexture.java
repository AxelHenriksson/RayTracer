package henaxel.raytracer.materials;


import java.awt.*;

public class ConstantTexture extends Texture {
    private Color color;
    
    public ConstantTexture(Color color) {
        super("Constant Texture", null, null, null, null);
        this.color = color;
    }
    public ConstantTexture(double r, double g, double b) {
        this(new Color((float)r, (float)g, (float)b));
    }
    
    @Override
    public Color color(double u, double v) {
        return color;
    }
}
