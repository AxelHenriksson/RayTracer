package henaxel.raytracer;

import henaxel.raytracer.materials.Material;
import henaxel.raytracer.utils.Vec3;

public abstract class Surface extends Hitable {
    Vec3 pos;
    Material mat;

    Surface(Vec3 pos, Material material) {
        this.pos = pos;
        this.mat = material;
    }

    protected abstract double[] getUV(Vec3 pos);

}
