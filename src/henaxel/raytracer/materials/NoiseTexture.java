package henaxel.raytracer.materials;

import henaxel.raytracer.node.Node;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class NoiseTexture implements Texture {
    Perlin noise = new Perlin();
    double scale;
    int depth;
    
    public NoiseTexture(double scale, int depth) {
        this.scale = scale;
        this.depth = depth;
    }
    
    @Override
    public Color color(double u, double v, Vec3 hitPos) {
        float perlin = (float)(0.5*(1 + noise.noise(Vec3.multiply(hitPos, scale))));
        float turbulence = (float)(noise.turb(Vec3.multiply(hitPos, scale), depth));
        float marble = (float) (0.5*(1 + Math.sin(scale*hitPos.z + 10*noise.turb(hitPos, depth))));
        return new Color(marble, marble, marble);
    }

    @Override
    public Node getNode() {
        return new Node("Noise Texture", Color.orange, null, null, null, null) {

        };
    }
}
