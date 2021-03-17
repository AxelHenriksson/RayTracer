package henaxel.materials;

import java.awt.*;

public class NoiseTexture extends Texture {
    Perlin noise = new Perlin();
    double scale;
    int depth;
    
    public NoiseTexture(double scale, int depth) {
        super("Perlin Noise Texture", null, null, null, null);
        this.scale = scale;
        this.depth = depth;
    }
    
    @Override
    public Color color(double u, double v) {
        float perlin = (float)(0.5*(1 + noise.noise(u*scale, v*scale)));
        float turbulence = (float)(noise.turb(u*scale, v*scale, depth));
        float marble = (float) (0.5*(1 + Math.sin(scale + 10*noise.turb(u, v, depth))));
        return new Color(marble, marble, marble);
    }
    /*
    public Color color(double u, double v, Vec3 hitPos) {
        float perlin = (float)(0.5*(1 + noise.noise(Vec3.multiply(hitPos, scale))));
        float turbulence = (float)(noise.turb(Vec3.multiply(hitPos, scale), depth));
        float marble = (float) (0.5*(1 + Math.sin(scale*hitPos.z + 10*noise.turb(hitPos, depth))));
        return new Color(marble, marble, marble);
    }
     */
}
