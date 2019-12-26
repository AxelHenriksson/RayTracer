public class Sphere extends Surface {
    double radius;

    Sphere(Vec3 pos, Vec3 up, Vec3 lookAt, double radius, Material material) {
        super(pos, up.unitVector(), lookAt.unitVector(), material);
        this.radius = radius;
    }
    Sphere(Vec3 pos, Vec3 lookAt, double radius, Material material) {
        this(pos, new Vec3(0, 1, 0), lookAt, radius, material);
    }
    Sphere(Vec3 pos, double radius, Material material) {
        this(pos, new Vec3(0, 1, 0), new Vec3(0, 0, -1), radius, material);
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
                Vec3 perpVec = Vec3.cross(lookAt, up);
                double u;
                if (Vec3.dot(perpVec, hitVec) < 0) {
                    u = 0.5 + Math.acos(Vec3.dot(lookAt, hitVec))/(2*Math.PI);
                } else {
                    u = 1.0 - (0.5 + Math.acos(Vec3.dot(lookAt, hitVec))/(2*Math.PI));
                }
                double v = 0.5 - (Vec3.dot(hitVec, up)/2);

                //System.out.println(Vec3.dot(perpVec, hitVec) < 0);
                //System.out.println(u + " | " + v);

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(u, v));
            }
            t = (-b + Math.sqrt(b*b-a*c))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                Ray scatter = mat.scatter(r, hitPos, n);

                Vec3 hitVec = Vec3.subtract(hitPos, pos).unitVector();
                Vec3 perpVec = Vec3.cross(lookAt, up);
                double u;
                if (Vec3.dot(perpVec, hitVec) < 0) {
                    u = 0.5 + Math.acos(Vec3.dot(lookAt, hitVec))/(2*Math.PI);
                } else {
                    u = 1.0 - (0.5 + Math.acos(Vec3.dot(lookAt, hitVec))/(2*Math.PI));
                }
                double v = 0.5 - (Vec3.dot(hitVec, up)/2);

                return new HitResult(hitPos, n, t, scatter, mat.getAlbedo(u, v));
            }

        }
        return null;
    }
}
