import java.awt.*;

public class Vec3 {
    public double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 vec) { return new Vec3(x+vec.x, y+vec.y, z+vec.z); }
    public Vec3 subt(Vec3 vec) { return new Vec3(x-vec.x, y-vec.y, z-vec.z); }
    public Vec3 mult(double fac) { return new Vec3(x*fac, y*fac, z*fac); }
    public double dot(Vec3 vec) { return x*vec.x + y*vec.y + z*vec.z; }
    public Vec3 cross(Vec3 vec) { return new Vec3(y*vec.z - z*vec.y, z*vec.x-x*vec.z, x*vec.y-y*vec.x); }
    public double length() { return Math.sqrt(x*x + y*y + z*z); }
    public Vec3 unitVector() { return new Vec3(x/length(), y/length(), z/length()); }

    public Color toColor() { return new Color((float) x, (float) y, (float) z); }
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
}
