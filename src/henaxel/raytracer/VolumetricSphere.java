package henaxel.raytracer;

import henaxel.raytracer.materials.Material;
import henaxel.utils.Vec3;

public class VolumetricSphere extends Sphere {
    private double density;

    public VolumetricSphere(Vec3 pos, double radius, Material material, double density) {
        super(pos, radius, material);
        this.density = density;
    }

    @Override
    protected HitResult hit(Ray r, double t_min, double t_max) {
        Vec3 oc = Vec3.subtract( r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - a*c;

        //TODO: Fix discrepancies in below code
        if(discriminant > 0) {
                double tInObject = ((Math.random() * 2.0) - 1) * (Math.sqrt(b * b - a * c) / a);
                double t = (-b + tInObject);
                if (t < t_max && t > t_min && Math.random() < density) {
                    Vec3 hitPos = r.pointAtParameter(t);
                    Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                    Ray scatter = mat.scatter(r, hitPos);
                    return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
                } else {
                if (t < t_max && t > t_min) {
                    Vec3 hitPos = r.pointAtParameter(t);
                    Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                    Ray scatter = new Ray(hitPos, r.direction());
                    return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
                }
            }
        }
        return null;
    }
}
