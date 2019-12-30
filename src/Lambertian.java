import java.awt.*;
import java.awt.image.BufferedImage;

public class Lambertian extends Material {

    Lambertian(Color albedo) {
        super(albedo);
    }
    Lambertian(float r, float g, float b) { super(new Color(r, g, b)); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(n, Utils.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Utils.randomInUnitSphere());
    }

}
