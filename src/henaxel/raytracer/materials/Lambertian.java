package henaxel.raytracer.materials;


import henaxel.raytracer.Ray;
import henaxel.raytracer.utils.*;

import java.awt.*;

public class Lambertian extends Material {
    private Texture albedo;
    
    public Lambertian(Texture texture) {
        super("Lambertian", null, null, null, null);
        this.albedo = texture;
    }
    public Lambertian(Color albedo) { this(new ConstantTexture(albedo)); }
    public Lambertian(float r, float g, float b) { this(new Color(r, g, b)); }
    public Lambertian(double r, double g, double b) { this(new Color((float)r, (float)g, (float)b)); }
    public Lambertian() { this(0.5,0.5,0.5); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(n, Vec3.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Vec3.randomInUnitSphere());
    }

    @Override
    public Color getAlbedo(double u, double v) {
        return albedo.color(u, v);
    }
}
