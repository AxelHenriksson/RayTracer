package henaxel.raytracer.materials;

import henaxel.raytracer.Ray;
import henaxel.utils.*;
import java.awt.*;

public class Lambertian extends Material {

    public Lambertian(Color albedo) {
        super(albedo);
    }
    public Lambertian(float r, float g, float b) { super(new Color(r, g, b)); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(n, Vec3.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Vec3.randomInUnitSphere());
    }

}
