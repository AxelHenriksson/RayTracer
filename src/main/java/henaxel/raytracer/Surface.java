package henaxel.raytracer;

import henaxel.materials.Material;
import henaxel.raytracer.utils.Vec3;

public abstract class Surface extends Hitable {
    Vec3 pos;
    Material mat;

    Surface(Vec3 pos, Material material) {
        this.mat = material;
        this.pos = pos;
    }

    protected abstract double[] getUV(Vec3 pos);

    public Material getMaterial() { return mat; }

}
