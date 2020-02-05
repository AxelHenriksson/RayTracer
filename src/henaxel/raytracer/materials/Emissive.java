package henaxel.raytracer.materials;

import henaxel.raytracer.Ray;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class Emissive extends Material {

    public Emissive(Color color) { super(color); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return null;
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return null;
    }

}
