package henaxel.raytracer;

import henaxel.raytracer.materials.Material;
import henaxel.utils.Vec3;

public abstract class Surface {
    Vec3 pos;
    Material mat;

    Surface(Vec3 pos, Material material) {
        this.pos = pos;
        this.mat = material;
    }

    abstract HitResult hit(Ray r, double t_min, double t_max);
}
