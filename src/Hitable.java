public class Hitable {
    Vec3 pos;

    Hitable(Vec3 pos) {
        this.pos = pos;
    }

    public Vec3 getPos() { return pos; }

    HitResult hit(Ray r, double t_min, double t_max) {
        return null;
    }
}
