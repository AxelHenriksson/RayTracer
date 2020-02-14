package henaxel.raytracer.materials;

import henaxel.raytracer.node.InLink;
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
        super("Dielectric",
                new InLink[] {
                        new InLink("Albedo", null),
                        new InLink("Refractive Index", null)
                }
        );
        this.albedo = albedo;
        this.refIndex = refIndex;
    }



    @Override
    public Ray scatter(Ray r, Vec3 pos, Vec3 n) {
        Vec3 outN;
        double rRatio;
        double reflectProb;
        double cosine;
        
        double rIndex = refIndex.color(0,0).getRed()/255.0; //TODO: Implement proper texture mapping for refractive index

        if (Vec3.dot(r.direction(), n) > 0) {
            outN = Vec3.multiply(n, -1);
            rRatio = rIndex ;
            cosine = rIndex * Vec3.dot(r.direction(), n) / r.direction().length();
        } else {
            outN = n;
            rRatio = 1/rIndex;
            cosine = -Vec3.dot(r.direction(), n) / r.direction().length();
        }

        Vec3 refracted = Material.refract(r.direction(), outN, rRatio);
        if(refracted != null) {
            reflectProb = Material.schlick(cosine, rIndex);
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
