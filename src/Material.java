import java.awt.*;

public abstract class Material {
    private Color albedo;

    Material(Color albedo) {
        this.albedo = albedo;
    }

    public abstract Ray scatter(Ray r, Vec3 pos, Vec3 n);
    public abstract Ray scatter(Ray r, Vec3 pos);

    Color getAlbedo() { return albedo; }

    static Vec3 reflect(Vec3 v, Vec3 n) {
        return Vec3.subtract(v, Vec3.multiply(Vec3.multiply(n, Vec3.dot(v, n)), 2));
    }

    static Vec3 refract(Vec3 v, Vec3 n, double refIndex, boolean entering) { return Vec3.subtract(v, Vec3.multiply(Vec3.multiply(n, Vec3.dot(v, n)), 2)); }
}
