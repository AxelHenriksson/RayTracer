public class Lambertian extends Material {
    Vec3 albedo;

    Lambertian(Vec3 albedo) {
        this.albedo = albedo;
    }

    @Override
    public HitResult scatter(Ray r, HitResult hr, Vec3 attenuation, Ray scattered) {
        Vec3 reflected = reflect(r.direction().unitVector(), hr.n);
        scattered = new Ray(hr.pos, reflected);
        attenuation = albedo;
        return new HitResult(scattered, attenuation, Vec3.dot(scattered.direction(), hr.n) > 0);
    }

    Vec3 reflect(Vec3 v, Vec3 n) {
        return Vec3.subtract(v, Vec3.multiply(Vec3.multiply(n, Vec3.dot(v,n)), 2));
    }
}
