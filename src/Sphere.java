public class Sphere extends Object {
    private double radius;

    Sphere(Vec3 pos, double radius) {
        super(pos);
        this.radius = radius;
    }

    public double getRadius() { return radius; }

    @Override boolean doesHit(Ray r) {
        Vec3 oc = Vec3.subtract(r.origin(), pos);
        double a = Vec3.dot(r.direction(), r.direction());
        double b = 2.0 * Vec3.dot(oc, r.direction());
        double c = Vec3.dot(oc, oc) - radius*radius;
        double discriminant = b*b - 4*a*c;
        return (discriminant > 0);
    }
}
