import java.awt.*;
import java.awt.image.BufferedImage;

public class Emissive extends Material {

    Emissive(Color color) {
        super(color);
    }
    Emissive(BufferedImage texture) {
        super(texture);
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