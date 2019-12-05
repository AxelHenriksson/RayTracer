public abstract class Hitable {
    Vec3 pos;

    Hitable(Vec3 pos) {
        this.pos = pos;
    }

    public Vec3 getPos() { return pos; }

    abstract HitResult hit(Ray r, double t_min, double t_max);

}
