package henaxel.raytracer;

import henaxel.utils.Vec3;
import henaxel.raytracer.materials.Material;

public class Sphere extends Surface {
    double radius;

    public Sphere(Vec3 pos, double radius, Material material) {
        super(pos, material);
        this.radius = radius;
    }

    @Override
    HitResult hit(Ray r, double t_min, double t_max) {
        Vec3 oc = Vec3.subtract(r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - a*c;

        if(discriminant > 0) {
            double t = (-b - Math.sqrt(discriminant))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.divide(Vec3.subtract(hitPos, pos), radius);
                Ray scatter = mat.scatter(r, hitPos, n);

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
            }
            t = (-b + Math.sqrt(discriminant))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.divide(Vec3.subtract(hitPos, pos), radius);
                Ray scatter = mat.scatter(r, hitPos, n);

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
            }

        }
        return null;
    }
}
