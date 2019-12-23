import java.awt.*;

public class Emissive extends Material {

    Emissive(Color color) {
        super(color);
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return null;
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return null;
    }

}
