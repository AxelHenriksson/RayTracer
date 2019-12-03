public class Object {
    Vec3 pos;

    Object(Vec3 pos) {
        this.pos = pos;
    }

    public Vec3 getPos() { return pos; }

    boolean hit(Ray r) {
        return false;
    }
}
