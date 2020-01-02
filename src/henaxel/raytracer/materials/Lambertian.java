package henaxel.raytracer.materials;

import henaxel.raytracer.Ray;
import henaxel.utils.*;
import java.awt.*;

public class Lambertian extends Material {
    
    public Lambertian(Texture texture) { super(texture); }
    public Lambertian(Color albedo) {
        super(new ConstantTexture(albedo));
    }
    public Lambertian(float r, float g, float b) { this(new Color(r, g, b)); }
    public Lambertian(double r, double g, double b) { this(new Color((float)r, (float)g, (float)b)); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(n, Vec3.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Vec3.randomInUnitSphere());
    }

}
