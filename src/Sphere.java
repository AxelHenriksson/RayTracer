public class Sphere extends Hitable {
    double radius;

    Sphere(Vec3 pos, Vec3 rot, double radius, Material material) {
        super(pos, rot.unitVector(), material);
        this.radius = radius;
    }

    Sphere(Vec3 pos, double radius, Material material) {
        this(pos, new Vec3(0, 0, 0), radius, material);
    }

    @Override
    HitResult hit(Ray r, double t_min, double t_max) {
        Vec3 oc = Vec3.subtract(r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - a*c;

        if(discriminant > 0) {
            double t = (-b - Math.sqrt(b*b-a*c))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                Ray scatter = mat.scatter(r, hitPos, n);

                Vec3 hitVec = Vec3.subtract(hitPos, pos).unitVector();
                double u = Math.acos(Vec3.dot(rot, hitVec))/Math.PI;
                double v = 1.0 - (Vec3.subtract(hitPos, pos).unitVector().y/2)-0.5;

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(u, v));
            }
            t = (-b + Math.sqrt(b*b-a*c))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                Ray scatter = mat.scatter(r, hitPos, n);

                double u = 0;
                double v = (Vec3.subtract(hitPos, pos).unitVector().y/2)+0.5;

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(u, v));
            }

        }
        return null;
    }
}
