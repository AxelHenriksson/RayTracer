package henaxel.raytracer;

import henaxel.raytracer.utils.Vec3;
import henaxel.materials.Material;

public class Sphere extends Surface {
    double radius;

    public Sphere(Vec3 pos, double radius, Material material) {
        super(pos, material);
        this.radius = radius;
    }

    @Override
    protected HitResult hit(Ray r, double t_min, double t_max) {
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
                
                double[] uv = getUV(hitPos);
                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(uv[0], uv[1])); //TODO : Implement proper uv mapping
            }
            t = (-b + Math.sqrt(discriminant))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.divide(Vec3.subtract(hitPos, pos), radius);
                Ray scatter = mat.scatter(r, hitPos, n);

                double[] uv = getUV(hitPos);
                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(uv[0], uv[1])); //TODO : Implementing proper uv mapping
            }

        }
        return null;
    }
    
    @Override
    protected double[] getUV(Vec3 p) {
        p = Vec3.divide(Vec3.subtract(p, pos), radius);
        double[] uv = new double[2];
        double phi = Math.atan2(p.z, p.x);
        double theta = -Math.asin(p.y);
        uv[0] = 1-(phi + Math.PI) / (2*Math.PI);
        uv[1] = (theta + Math.PI/2) / Math.PI;
        return uv;
    }
    
    @Override
    protected AABB boundingBox() {
        return new AABB(Vec3.subtract(pos, new Vec3(radius, radius, radius)), Vec3.add(pos, new Vec3(radius, radius, radius)));
    }
}
