public class Sphere extends Hitable {
    private double radius;
    private Material mat;

    Sphere(Vec3 pos, double radius, Material material) {
        super(pos);
        this.radius = radius;
        this.mat = material;
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
                if(scatter != null) {
                    return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
                }
            }
            t = (-b + Math.sqrt(b*b-a*c))/a;
            if (t < t_max && t > t_min) {
                Vec3 hitPos = r.pointAtParameter(t);
                Vec3 n = Vec3.subtract(hitPos, pos).unitVector();
                Ray scatter = mat.scatter(r, hitPos, n);
                if(scatter != null) {
                    return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
                }
            }

        }
        return null;
    }
}
