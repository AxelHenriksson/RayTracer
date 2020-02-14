package henaxel.raytracer.materials;

import henaxel.node.BaseNode;
import henaxel.raytracer.Ray;
import henaxel.raytracer.node.Node;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class Emissive extends Material {
    private Texture albedo;

    public Emissive(Texture texture) {
        super("Emissive", null, null, null, null);
        albedo = texture;
    }
    public Emissive() { this(new ConstantTexture(1, 0.5, 0.5)); }

    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        return null;
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) {
        return null;
    }

    @Override
    public Color getAlbedo(double u, double v) {
        return null;
    }
}
