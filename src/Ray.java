public class Ray {
    Vec3 pos;
    Vec3 dir;

    public Ray(Vec3 pos, Vec3 dir) {
        this.pos = pos;
        this.dir = dir;
    }

    public Vec3 pointAtParameter(double t) { return pos.add(dir.mult(t)); }

    public Vec3 origin() { return pos; }
    public Vec3 direction() { return dir; }

    @Override
    public String toString() { return String.format("Vec3{pos=%s,dir=%s}", pos, dir); }
}
