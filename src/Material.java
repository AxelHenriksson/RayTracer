import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Material {
    private Color albedo;
    BufferedImage texture;

    Material(Color albedo) {
        this.albedo = albedo;
    }
    Material(BufferedImage texture) { this.texture = texture; }

    // Surface scattering
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) { return null; }
    // Volumetric scattering
    public Ray scatter(Ray r, Vec3 pos) { return null; }

    Color getAlbedo(double u, double v) {
        if(texture == null) {
            return albedo;
        } else {
            int x = (int) (u * texture.getWidth());
            int y = (int) (v * texture.getHeight());
            return new Color(texture.getRGB(x, y));
        }
    }

    static Vec3 reflect(Vec3 v, Vec3 n) {
        Vec3 uv = v.unitVector();
        return Vec3.subtract(uv, Vec3.multiply(Vec3.multiply(n, Vec3.dot(uv, n)), 2)).unitVector();
    }

    static double schlick(double cosine, double refIndex) {
        double r0 = (1-refIndex) / (1+refIndex);
        r0 = r0*r0;
        return r0 + (1-r0)*Math.pow(1-cosine, 5);
    }

    static Vec3 refract(Vec3 v, Vec3 n, double rRatio) {
        Vec3 uv = v.unitVector();
        double dt = Vec3.dot(uv, n);
        double discriminant = 1.0 - rRatio*rRatio*(1-dt*dt);
        if(discriminant > 0) {
            return Vec3.subtract(Vec3.multiply(Vec3.subtract(uv, Vec3.multiply(n, dt)), rRatio), Vec3.multiply(n, Math.sqrt(discriminant)));
        } else {
            return null;
        }
    }
}
