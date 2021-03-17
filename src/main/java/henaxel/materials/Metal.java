package henaxel.materials;

import henaxel.raytracer.Ray;
import henaxel.raytracer.node.InLink;
import henaxel.raytracer.utils.*;


import java.awt.*;

public class Metal extends Material {
    private Texture roughness;
    private Texture albedo;


    public Metal(Texture albedo, Texture roughness) {
        super("Metal",
                new InLink[] {
                        new InLink("albedo", null),
                        new InLink("roughness", null)
                }
        );
        this.albedo = albedo;
        this.roughness = roughness;
    }
    public Metal(Texture albedo, double roughness) { this(albedo, new ConstantTexture(roughness, roughness, roughness)); }
    public Metal(double r, double g, double b, double roughness) {
        this(new ConstantTexture(r, g, b), new ConstantTexture(roughness, roughness, roughness));
    }
    public Metal() { this(0.5, 0.5, 0.5, 0.2); }

    //Surface scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 reflected = Material.reflect(r.direction().unitVector(), n);
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness.color(0,0).getRed()/255.0)); //TODO: Implement proper uv mapping

        int tries = 0;
        while (Vec3.dot(scattered, n) <= 0 && tries < 50) {
            scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness.color(0,0).getRed()/255.0)); //TODO: Implement proper uv mapping
            tries++;
        }

        return new Ray(pos, scattered);
    }

    // Volumetric scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        Vec3 reflected = Material.reflect(r.direction().unitVector(), Vec3.randomInUnitSphere());
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness.color(0,0).getRed()/255.0)); //TODO: Implement proper uv mapping
        return new Ray(pos, scattered);
    }

    @Override
    public Color getAlbedo(double u, double v) {
        return albedo.color(u, v);
    }
}
