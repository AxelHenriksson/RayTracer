package henaxel.raytracer

import henaxel.raytracer.utils.Vec3

data class AABB (
    val min: Vec3,
    val max: Vec3
        ) {

    fun hit(ray: Ray, tMin: Double, tMax: Double): Boolean {
        for (a in 0..2) {
            val t0 = minOf(
                (min[a] - ray.pos[a]) / ray.dir[a],
                (max[a] - ray.pos[a]) / ray.dir[a])
            val t1 = maxOf(
                (min[a] - ray.pos[a]) / ray.dir[a],
                (max[a] - ray.pos[a]) / ray.dir[a])
            if (t1.coerceAtMost(tMax) <= t0.coerceAtLeast(tMin)) return false
        }
        return true
    }

    companion object {
        fun surroundingBox(box0: AABB, box1: AABB): AABB {
            return AABB(
                Vec3(   //minimum corner
                    minOf(box0.min.x, box1.min.x),
                    minOf(box0.min.y, box1.min.y),
                    minOf(box0.min.z, box1.min.z)
                ),
                Vec3(   //maximum corner
                    maxOf(box0.max.x, box1.max.x),
                    maxOf(box0.max.y, box1.max.y),
                    maxOf(box0.max.z, box1.max.z)
                )
            )
        }
    }
}