import java.awt.*;

public class Metal extends Material {

    Metal(Color albedo) {
        super(albedo);
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 reflected = reflect(r.direction().unitVector(), n);

        if (Vec3.dot(reflected, n) > 0) {
            return new Ray(pos, reflected);
        } else {
            return null;
        }
    }

}
