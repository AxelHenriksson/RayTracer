package henaxel.raytracer.materials;

import henaxel.node.InLink;
import henaxel.node.OutLink;
import henaxel.raytracer.Ray;
import henaxel.raytracer.node.Node;
import henaxel.raytracer.utils.*;


import java.awt.*;

public class Metal extends Material {
    private double roughness;
    private Color albedo;


    public Metal() { this(new Color(255, 255, 255), 0); }
    public Metal(Color albedo, double roughness) {
        this.albedo = albedo;
        this.roughness = Utils.clamp(roughness, 0, 1);
    }
    public Metal(double r, double g, double b, double roughness) {
        this.albedo = new Color((float) r, (float) g, (float) b);
        this.roughness = Utils.clamp(roughness, 0, 1);
    }

    //Surface scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 reflected = Material.reflect(r.direction().unitVector(), n);
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness));

        int tries = 0;
        while (Vec3.dot(scattered, n) <= 0 && tries < 50) {
            scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness));
            tries++;
        }

        return new Ray(pos, scattered);
    }

    // Volumetric scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        Vec3 reflected = Material.reflect(r.direction().unitVector(), Vec3.randomInUnitSphere());
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness));
        return new Ray(pos, scattered);
    }

    @Override
    public Color getAlbedo(double u, double v, Vec3 hitPos) {
        return albedo;
    }

    @Override
    public Node getNode() {
        return new Node("Metal", Color.red,
                new InLink[] {
                     new InLink("Albedo", null),
                     new InLink("Roughness", null)
                },
                new OutLink[] {
                        new OutLink("Test", null)
                })
        {

        };
    }
}
