package henaxel.raytracer

import henaxel.raytracer.utils.Vec3
import kotlin.math.tan

class Camera(
    imageWidth: Int,
    imageHeight: Int,
    var lookFrom: Vec3,
    var lookAt: Vec3,
    var vup: Vec3,
    var vfov: Double,
    var aperture: Double,
    var focusDist: Double,
)
{
    private lateinit var upperLeft: Vec3
    private lateinit var horizontal: Vec3
    private lateinit var vertical: Vec3
    private lateinit var origin: Vec3
    lateinit var u: Vec3
    lateinit var v: Vec3
    lateinit var w: Vec3

    init{
        calcVec(imageWidth, imageHeight)
    }

    fun calcVec(imageWidth: Int, imageHeight: Int) {
        val aspectRatio = imageWidth.toDouble() / imageHeight
        val theta: Double = vfov*Math.PI/180.0
        val height: Double = 2.0*tan(theta/2.0)
        val width = aspectRatio * height
        w = (lookFrom - lookAt).norm()
        u = (vup x w).norm()
        v= w x u
        origin = lookFrom
        upperLeft = (origin + (v * (focusDist*height/2.0))) - ((u * (focusDist*width/2.0)) + (w * focusDist))
        horizontal = (u * (focusDist*width/2.0)) * 2.0
        vertical = (v * (focusDist*height/2.0)) * 2.0
    }

    fun getRay(s: Double, t: Double): Ray {
        val rd = Vec3.randomInUnitDisk() * (aperture/2.0)
        val offset = (u*rd.x) + (v*rd.y)
        return Ray(origin + offset, (upperLeft+(horizontal * s)) - ((vertical*t) + origin + offset))
    }

}