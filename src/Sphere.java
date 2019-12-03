public class Sphere extends Hitable {
    private double radius;

    Sphere(Vec3 pos, double radius) {
        super(pos);
        this.radius = radius;
    }

    public double getRadius() { return radius; }

    @Override
    HitResult hit(Ray r, double t_min, double t_max) {
        HitResult hr = new HitResult();
        Vec3 oc = Vec3.subtract(r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - a*c;

        if(discriminant > 0) {
            double tmp = (-b - Math.sqrt(b*b-a*c))/a;
            if (tmp < t_max && tmp > t_min) {
                hr.t = tmp;
                hr.pos = r.pointAtParameter(hr.t);
                hr.n = Vec3.divide(Vec3.subtract(hr.pos, pos), radius);
                return hr;
            }
            tmp = (-b + Math.sqrt(b*b-a*c))/a;
            if (tmp < t_max && tmp > t_min) {
                hr.t = tmp;
                hr.pos = r.pointAtParameter(hr.t);
                hr.n = Vec3.divide(Vec3.subtract(hr.pos, pos), radius);
                return hr;
            }

        }
        return null;
    }
}
