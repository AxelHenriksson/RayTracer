package henaxel.raytracer.materials;

import henaxel.node.BaseNode;
import henaxel.raytracer.utils.Vec3;
import henaxel.raytracer.Ray;
import java.awt.*;

public class Dielectric extends Material {
    private Texture albedo;
    private Texture refIndex;


    public Dielectric(Double refIndex) {
        this(new ConstantTexture(1.0, 1.0, 1.0), new ConstantTexture(refIndex, refIndex, refIndex));
    }
    
    public Dielectric(Texture albedo, Texture refIndex) {
        super("Dielectric", null, null, null, null);
        this.albedo = albedo;
        this.refIndex = refIndex;
    }



    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 outN;
        double rRatio;
        double reflectProb;
        double cosine;

        if (Vec3.dot(r.direction(), n) > 0) {
            outN = Vec3.multiply(n, -1);
            rRatio = refIndex;
            cosine = refIndex * Vec3.dot(r.direction(), n) / r.direction().length();
        } else {
            outN = n;
            rRatio = 1/refIndex;
            cosine = -Vec3.dot(r.direction(), n) / r.direction().length();
        }

        Vec3 refracted = Material.refract(r.direction(), outN, rRatio);
        if(refracted != null) {
            reflectProb = Material.schlick(cosine, refIndex);
        } else {
            reflectProb = 1.0;
        }

        if(Math.random() <= reflectProb) {
            return new Ray(pos, Material.reflect(r.direction(), n));
        } else {
            return new Ray(pos, refracted);
        }
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) { return null; }

    @Override
    public Color getAlbedo(double u, double v) {
        return albedo.color(u, v);
    }
}
