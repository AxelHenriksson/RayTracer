public class Vec3D {
    public double x, y, z;

    public Vec3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3D add(Vec3D vec) { return new Vec3D(x+vec.x, y+vec.y, z+vec.z); }
    public Vec3D subtract(Vec3D vec) { return new Vec3D(x-vec.x, y-vec.y, z-vec.z); }
    public Vec3D multiply(double fac) { return new Vec3D(x*fac, y*fac, z*fac); }
    public double dotProduct(Vec3D vec) { return x*vec.x + y*vec.y + z*vec.z; }
    public Vec3D crossProduct(Vec3D vec) { return new Vec3D(y*vec.z - z*vec.y, z*vec.x-x*vec.z, x*vec.y-y*vec.x); }
    public double length() { return Math.sqrt(x*x + y*y + z*z); }
    public Vec3D unitVector() { return new Vec3D(x/length(), y/length(), z/length()); }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
}
