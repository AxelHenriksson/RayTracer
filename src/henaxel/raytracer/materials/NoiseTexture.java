package henaxel.raytracer.materials;

import henaxel.utils.Vec3;

import java.awt.*;

public class NoiseTexture extends Texture {
    Perlin noise = new Perlin();
    double scale;
    int depth;
    
    public NoiseTexture(double scale, int depth) {
        this.scale = scale;
        this.depth = depth;
    }
    
    @Override
    Color color(double u, double v, Vec3 hitPos) {
        float perlin = (float)(0.5*(1 + noise.noise(Vec3.multiply(hitPos, scale))));
        float turbulence = (float)(noise.turb(Vec3.multiply(hitPos, scale), depth));
        float marble = (float) (0.5*(1 + Math.sin(scale*hitPos.z + 10*noise.turb(hitPos, depth))));
        return new Color(marble, marble, marble);
    }
}
