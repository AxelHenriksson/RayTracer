package henaxel.raytracer;

public abstract class Hitable {
    
    protected abstract HitResult hit(Ray r, double t_min, double t_max);
    protected abstract AABB boundingBox();
}
