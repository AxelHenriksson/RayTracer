public abstract class Surface {
    Vec3 pos;
    Vec3 up;
    Vec3 lookAt;
    Material mat;

    Surface(Vec3 pos, Vec3 up, Vec3 lookAt, Material material) {
        this.pos = pos;
        this.up = up;
        this.lookAt = lookAt;
        this.mat = material;
    }
    Surface(Vec3 pos, Vec3 lookAt, Material material) {
        this(pos, new Vec3(0, 1, 0), lookAt, material);
    }
    Surface(Vec3 pos, Material material) {
        this(pos, new Vec3(0, 1, 0), new Vec3(0, 0, -1), material);
    }

    abstract HitResult hit(Ray r, double t_min, double t_max);

}
