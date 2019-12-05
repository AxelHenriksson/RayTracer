public abstract class Material {



    public abstract HitResult scatter(Ray r, HitResult hr, Vec3 Attenuation, Ray scattered);
}
