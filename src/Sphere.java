public class Sphere extends Object {
    private double radius;

    Sphere(Vec3 pos, double radius) {
        super(pos);
        this.radius = radius;
    }

    public double getRadius() { return radius; }

    @Override boolean hit(Ray r) {
        Vec3 oc = r.origin().subt(pos);
        double a = r.direction().dot(r.direction());
        double b = 2.0 * oc.dot(r.direction());
        double c = oc.dot(oc) - radius*radius;
        double discriminant = b*b - 4*a*c;
        return (discriminant > 0);
    }
}
