package henaxel.raytracer

interface Hitable {
    fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult?
    fun boundingBox(): AABB
}