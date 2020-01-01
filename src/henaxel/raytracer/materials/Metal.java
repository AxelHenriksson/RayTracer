package henaxel.raytracer.materials;

import henaxel.raytracer.Ray;
import henaxel.utils.*;


import java.awt.*;

public class Metal extends Material {
    private double roughness;

    public Metal(Color albedo, double roughness) {
        super(albedo);
        this.roughness = Utils.clamp(roughness, 0, 1);
    }
    public Metal(double r, double g, double b, double roughness) {
        super(new Color((float)r, (float)g, (float)b));
        this.roughness = Utils.clamp(roughness, 0, 1);
    }


    //Surface scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 reflected = reflect(r.direction().unitVector(), n);
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
        Vec3 reflected = reflect(r.direction().unitVector(), Vec3.randomInUnitSphere());
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Vec3.randomInUnitSphere(), roughness));
        return new Ray(pos, scattered);
    }

}
