public abstract class Hitable {
    Vec3 pos;
    Vec3 rot;
    Material mat;

    Hitable(Vec3 pos, Vec3 rot, Material material) {
        this.pos = pos;
        this.rot = rot;
        this.mat = material;
    }

    public Vec3 getPos() { return pos; }
    public Vec3 getRot() { return rot; }
    public Material getMat() { return mat; }

    abstract HitResult hit(Ray r, double t_min, double t_max);

}
