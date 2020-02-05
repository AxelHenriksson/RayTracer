package henaxel.raytracer.materials;

import java.awt.*;
import henaxel.raytracer.utils.Vec3;
import henaxel.raytracer.Ray;

public abstract class Material {
    protected Texture albedo;

    protected Material(Texture albedo) { this.albedo = albedo; }
    protected Material(Color albedo) { this.albedo = new ConstantTexture(albedo); }
    protected Material() {}

    // Surface scattering
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) { return null; }
    // Volumetric scattering
    public Ray scatter(Ray r, Vec3 pos) { return null; }

    protected static Vec3 reflect(Vec3 v, Vec3 n) {
        Vec3 uv = v.unitVector();
        return Vec3.subtract(uv, Vec3.multiply(Vec3.multiply(n, Vec3.dot(uv, n)), 2)).unitVector();
    }
    
    protected static double schlick(double cosine, double refIndex) {
        double r0 = (1-refIndex) / (1+refIndex);
        r0 = r0*r0;
        return r0 + (1-r0)*Math.pow(1-cosine, 5);
    }
    
    protected static Vec3 refract(Vec3 v, Vec3 n, double rRatio) {
        Vec3 uv = v.unitVector();
        double dt = Vec3.dot(uv, n);
        double discriminant = 1.0 - ((rRatio*rRatio)*(1-(dt*dt)));
        if(discriminant > 0) {
            return Vec3.subtract(Vec3.multiply(Vec3.subtract(uv, Vec3.multiply(n, dt)), rRatio), Vec3.multiply(n, Math.sqrt(discriminant)));
        } else {
            return null;
        }
    }
    
    public Color getAlbedo(double u, double v, Vec3 hitPos) {
        return albedo.color(u, v, hitPos);
    }
}
