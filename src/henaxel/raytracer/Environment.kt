package henaxel.raytracer

import java.util.*


data class Environment (
    val hitables: MutableList<Hitable> = ArrayList(),
    var activeCam: Camera,
    var bvhNode: BVHNode? = null
    ){

    constructor(activeCam: Camera) : this(ArrayList(), activeCam, null)

    fun constructBVH() { bvhNode = BVHNode(hitables.toTypedArray()) }

    fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult? {
        if (bvhNode != null) return bvhNode!!.hit(ray, tMin, tMax)
        var hr: HitResult? = null
        var tmpHR: HitResult?
        var closest = tMax
        for( hitable: Hitable in hitables) {
            tmpHR = hitable.hit(ray, tMin, closest)
            if(tmpHR != null) {
                closest = tmpHR.t
                hr = tmpHR
            }
        }
        return hr
    }
}