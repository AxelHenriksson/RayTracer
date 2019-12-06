import java.awt.*;

public class Lambertian extends Material {

    Lambertian(Color albedo) {
        super(albedo);
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return new Ray(pos, Vec3.add(/*pos,*/ n, Utils.randomInUnitSphere()));
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return new Ray(pos, Utils.randomInUnitSphere());
    }

}
