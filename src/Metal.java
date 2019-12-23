import java.awt.*;

public class Metal extends Material {
    private double roughness;

    Metal(Color albedo, double roughness) {
        super(albedo);
        this.roughness = Utils.clamp(roughness, 0, 1);
    }


    //Surface scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 reflected = reflect(r.direction().unitVector(), n);
        Vec3 scattered = Vec3.add(reflected, Vec3.multiply(Utils.randomInUnitSphere(), roughness));

        int tries = 0;
        while (Vec3.dot(scattered, n) <= 0 && tries < 50) {
            scattered = Vec3.add(reflected, Vec3.multiply(Utils.randomInUnitSphere(), roughness));
            tries++;
        }

        return new Ray(pos, scattered);
    }

    // Volumetric scattering
    @Override
    public Ray scatter(Ray r, Vec3 pos) { return null; }

}
