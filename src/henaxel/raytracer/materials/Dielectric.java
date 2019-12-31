package henaxel.raytracer.materials;

import henaxel.utils.Vec3;
import henaxel.raytracer.Ray;
import java.awt.*;

public class Dielectric extends Material {
    private double refIndex;


    public Dielectric(Double refIndex) {
        this(new Color(1.0f, 1.0f, 1.0f), refIndex);
    }
    
    public Dielectric(Color albedo, Double refIndex) {
        super(albedo);
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

        Vec3 refracted = refract(r.direction(), outN, rRatio);
        if(refracted != null) {
            reflectProb = schlick(cosine, refIndex);
        } else {
            reflectProb = 1.0;
        }

        if(Math.random() <= reflectProb) {
            return new Ray(pos, reflect(r.direction(), n));
        } else {
            return new Ray(pos, refracted);
        }
    }

    @Override
    public Ray scatter(Ray r, Vec3 pos) { return null; }

}
