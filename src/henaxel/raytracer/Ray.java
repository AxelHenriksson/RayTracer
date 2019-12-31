package henaxel.raytracer;

import henaxel.utils.Vec3;

public class Ray {
    private Vec3 pos;
    private Vec3 dir;

    public Ray(Vec3 pos, Vec3 dir) {
        this.pos = pos;
        this.dir = dir;
    }

    public Vec3 pointAtParameter(double t) { return Vec3.add(pos, Vec3.multiply(dir, t)); }

    public Vec3 origin() { return pos; }
    public Vec3 direction() { return dir; }

    @Override
    public String toString() { return String.format("Vec3{pos=%s,dir=%s}", pos, dir); }
}
