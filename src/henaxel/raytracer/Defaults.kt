package henaxel.raytracer

class Defaults {
    companion object{
        const val IMAGE_WIDTH = 800
        const val IMAGE_HEIGHT = 450
        const val SAMPLES = 32
        const val DEPTH = 8
        const val CLIP_MAX = 1000.0
        const val CLIP_MIN = 0.001
        const val GAMMA = 2.0f
        const val TRANSPARENT = true

        const val MAX_FPS = 60

        const val RTHREAD_COUNT = 7
        const val RCELL_COUNT = 37
        const val CONSTRUCT_BVH = true
    }
}