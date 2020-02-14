package henaxel.raytracer.materials;

import henaxel.node.BaseNode;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class ConstantTexture implements Texture {
    private Color color;
    
    public ConstantTexture(Color color) {
        this.color = color;
    }
    public ConstantTexture(double r, double g, double b) {
        this.color = new Color((float)r, (float)g, (float)b);
    }
    
    @Override
    public Color color(double u, double v, Vec3 hitPos) {
        return color;
    }

    @Override
    public BaseNode getNode() {
        return null;    //TODO: Implement
    }
}
