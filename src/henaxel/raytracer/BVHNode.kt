package henaxel.raytracer

import henaxel.raytracer.utils.Vec3
import kotlin.random.Random

class BVHNode(l: Array<Hitable>) : Hitable {
    private var left: Hitable
    private var right: Hitable
    private var box: AABB

    init  {
        val l = boxSort(l,Random.nextInt(0,3))

        when(l.size) {
            1 -> {
                left = l[0]
                right = l[0]
                box = left.boundingBox()
            }
            2 -> {
                left = l[0]
                right = l[1]
                box = AABB(
                    Vec3(
                        minOf(left.boundingBox().min.x, right.boundingBox().min.x),
                        minOf(left.boundingBox().min.y, right.boundingBox().min.y),
                        minOf(left.boundingBox().min.z, right.boundingBox().min.z)
                    ),
                    Vec3(
                        maxOf(left.boundingBox().max.x, right.boundingBox().max.x),
                        maxOf(left.boundingBox().max.y, right.boundingBox().max.y),
                        maxOf(left.boundingBox().max.z, right.boundingBox().max.z)
                    )
                )
            }
            else -> {
                left = BVHNode(l.copyOfRange(0, l.size/2))
                right = BVHNode(l.copyOfRange(l.size/2, l.size))
                box = AABB(
                    Vec3(
                        minOf(left.boundingBox().min.x, right.boundingBox().min.x),
                        minOf(left.boundingBox().min.y, right.boundingBox().min.y),
                        minOf(left.boundingBox().min.z, right.boundingBox().min.z)
                    ),
                    Vec3(
                        maxOf(left.boundingBox().max.x, right.boundingBox().max.x),
                        maxOf(left.boundingBox().max.y, right.boundingBox().max.y),
                        maxOf(left.boundingBox().max.z, right.boundingBox().max.z)
                    )
                )
            }
        }
    }

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitResult? {
        return if(box.hit(ray, tMin, tMax)) {
            val leftHR: HitResult? = left.hit(ray, tMin, tMax)
            val rightHR: HitResult?  = right.hit(ray, tMin, tMax)
            if (leftHR != null && rightHR != null)
                if (leftHR.t < rightHR.t) leftHR else rightHR
            else rightHR
        }
        else null
    }

    override fun boundingBox(): AABB {
        return box
    }

    companion object {
        fun boxSort(l: Array<Hitable>, axis: Int): Array<Hitable> {
            if (l.size < 2) return l
            var done: Boolean
            do{
                done = true
                for (i: Int in l.indices) {
                    val leftBox  = l[i].boundingBox()
                    val rightBox = l[i+1].boundingBox()
                    if(leftBox.min[axis] > rightBox.min[axis]) {
                        val tmp = l[i]
                        l[i] = l[i+1]
                        l[i+1] = tmp
                        done = false
                    }
                }
            } while (!done)
            return l
        }
    }
}