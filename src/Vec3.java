import java.awt.*;

public class Vec3 {
    double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    static Vec3 add(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    static Vec3 add(Vec3 v1, Vec3 v2, Vec3 v3) {
        return Vec3.add(v1, Vec3.add(v2, v3));
    }

    static Vec3 subtract(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    static Vec3 multiply(Vec3 v1, double fac) {
        return new Vec3(v1.x * fac, v1.y * fac, v1.z * fac);
    }

    static Vec3 divide(Vec3 v1, double fac) {
        return new Vec3(v1.x / fac, v1.y / fac, v1.z / fac);
    }

    static double dot(Vec3 v1, Vec3 v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    static Vec3 cross(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

    double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    Vec3 unitVector() {
        return new Vec3(x / length(), y / length(), z / length());
    }

    Color toColor() {
        return new Color((float) (x / length()), (float) (y / length()), (float) (z / length()));
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
}