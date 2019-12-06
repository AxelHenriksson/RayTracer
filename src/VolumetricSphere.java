public class VolumetricSphere extends Hitable {
    private double radius;
    private Material mat;
    private double density;

    VolumetricSphere(Vec3 pos, double radius, Material material, double density) {
        super(pos);
        this.radius = radius;
        this.mat = material;
        this.density = density;
    }

    public double getRadius() { return radius; }

    @Override
    HitResult hit(Ray r, double t_min, double t_max) {
        Vec3 oc = Vec3.subtract( r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - a*c;

        if(discriminant > 0 && Math.random() < density) {
                double tInObject = ((Math.random() * 2.0) - 1) * Math.sqrt(b * b - a * c) / a;
                double t = (-b + tInObject);
                if (t < t_max && t > t_min) {
                    Vec3 hitPos = r.pointAtParameter(t);
                    Vec3 n = Vec3.divide(Vec3.subtract(hitPos, pos), radius);
                    Ray scatter = mat.scatter(r, hitPos);
                    return new HitResult(hitPos, n, t, scatter, mat.getAlbedo());
                }
        }
        return null;
    }
}
