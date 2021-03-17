package henaxel.raytracer;

public abstract class Hitable {

    abstract HitResult hit(Ray r, double t_min, double t_max);
    abstract AABB boundingBox();
}
