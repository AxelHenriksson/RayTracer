import java.awt.*;
import java.awt.image.BufferedImage;

public class Lambertian extends Material {

    Lambertian(Color albedo) {
        super(albedo);
    }
    Lambertian(BufferedImage texture) {
        super(texture);
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(n, Utils.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Utils.randomInUnitSphere());
    }

}
