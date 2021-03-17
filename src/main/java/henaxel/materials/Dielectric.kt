package henaxel.materials;

import henaxel.raytracer.node.InLink;
import henaxel.raytracer.utils.Vec3;
import henaxel.raytracer.Ray;
import java.awt.*;

class Dielectric(private var albedo: Texture, private var refIndex: Texture)
    : Material("Dielectric",  arrayOf(InLink("Albedo", null), InLink("Refractive Index", null))) {

    constructor(refIndex: Double) : this(ConstantTexture(1.0, 1.0, 1.0), ConstantTexture(refIndex, refIndex, refIndex))




    override fun scatter(r: Ray, pos: Vec3, n: Vec3): Ray {
        var outN: Vec3;
        var rRatio: Double;
        var reflectProb: Double;
        var cosine: Double;
        
        var rIndex: Double = refIndex.color(0.0,0.0).red /255.0; //TODO: Implement proper texture mapping for refractive index

        if (Vec3.dot(r.direction(), n) > 0) {
            outN = Vec3.multiply(n, -1.0);
            rRatio = rIndex ;
            cosine = rIndex * Vec3.dot(r.direction(), n) / r.direction().length();
        } else {
            outN = n;
            rRatio = 1/rIndex;
            cosine = -Vec3.dot(r.direction(), n) / r.direction().length();
        }

        var refracted: Vec3 = refract(r.direction(), outN, rRatio);
        if(refracted != null) {
            reflectProb = Material.schlick(cosine, rIndex);
        } else {
            reflectProb = 1.0;
        }

        if(Math.random() <= reflectProb) {
            return Ray(pos, reflect(r.direction(), n));
        }
        return Ray(pos, refracted);

    }

    override fun scatter(r: Ray, pos: Vec3): Ray? { return null; }

    override fun getAlbedo(u: Double, v: Double): Color {
        return albedo.color(u, v);
    }
}
