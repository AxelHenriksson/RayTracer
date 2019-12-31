package henaxel.raytracer;

import henaxel.utils.*;
import java.awt.*;

public class HitResult {
    public Vec3 pos;
    public Vec3 n;
    public double t;
    public Ray scattered;
    public Color attenuation;
    
    HitResult(Vec3 pos, Vec3 normal, double t, Ray scattered, Color attenuation) {
        this.pos = pos;
        this.n = normal;
        this.t = t;
        this.scattered = scattered;
        this.attenuation = attenuation;
    }
}
