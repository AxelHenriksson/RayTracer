import java.awt.*;

public class Glass extends Material {
    private double refIndex;

    Glass(Color albedo, Double refIndex) {
        super(albedo);
        this.refIndex = refIndex;
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

    @Override
    public Ray scatter(Ray r, Vec3 pos) { return null; }

}
