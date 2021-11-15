package henaxel.raytracer

import henaxel.raytracer.materials.Material
import henaxel.raytracer.utils.Vec3
import kotlin.math.sign

class Box(pos: Vec3, var pos2: Vec3, material: Material) : Surface(pos, material){


    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult? {
        val hitResults: MutableList<HitResult> = ArrayList()
        for (dim: Int in 0 until 3) {
            val t = minOf(
                (pos[dim]-ray.pos[dim])/ray.dir[dim],
                (pos2[dim]-ray.pos[dim])/ray.dir[dim])

            if (t > tMin && t < tMax) {
                val hitPos = ray.pos + (ray.dir * t)

                val i = (dim+1) % 3
                val j = (dim+2) % 3
                if(hitPos[i] in pos[i]..pos2[i] && hitPos[j] in pos[j]..pos2[j]) {
                    val normal = Vec3(
                        if (dim == 0) -sign(ray.dir[0]) else 0.0,
                        if (dim == 1) -sign(ray.dir[1]) else 0.0,
                        if (dim == 2) -sign(ray.dir[2]) else 0.0
                    )

                    val scattered = mat.scatter(ray, hitPos, normal)
                    val uv = getUV(hitPos)
                    hitResults.add(HitResult(hitPos, normal, t, scattered, mat.getAlbedo(uv[0],uv[1])))
                }
            }
            //else { }  // implement almost identical routine for backface if backface is needed
                        // backface is not seen unless camera is inside box.
        }
        return when (hitResults.size) {
            0 -> null
            1 -> hitResults[0]
            else -> {
                var closest: HitResult = hitResults[0]
                for (i in  1 until hitResults.size) {
                    if (hitResults[i].t < closest.t)
                        closest = hitResults[i]
                }
                closest
            }
        }
    }

    override fun getUV(pos: Vec3): Array<Double> {
        return arrayOf(0.0, 0.0)    //TODO: implement proper uv-mapping
    }

    override fun boundingBox(): AABB {
        return AABB(pos, pos2)
    }

    fun move(movement: Vec3) {
        pos += movement
        pos2 += movement
    }
}