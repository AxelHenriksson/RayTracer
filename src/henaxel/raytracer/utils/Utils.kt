package henaxel.raytracer.utils

import java.awt.Color
import kotlin.math.pow

fun Color.correctGamma(gamma: Float): Color {
    return Color(
        (this.red /255.0f).pow(1.0f/gamma),
        (this.green /255.0f).pow(1.0f/gamma),
        (this.blue /255.0f).pow(1.0f/gamma),
        this.alpha/255.0f
    )
}
fun Color.lerp(other: Color, fac: Float): Color {
    return this*(1.0f-fac) + other*fac
}
fun Color.average(): Int {
    return (this.red+this.green+this.blue)/3
}
operator fun Color.times(other: Color): Color {
    return Color((this.red * other.red)/65025f, (this.green * other.green)/65025f, (this.blue*other.blue)/65025f)
}
operator fun Color.times(fac: Float): Color {
    return Color((this.red*fac).toInt().coerceIn(0,255), (this.green*fac).toInt().coerceIn(0,255), (this.blue*fac).toInt().coerceIn(0,255))
}
operator fun Color.plus(other: Color): Color {
    return Color((this.red+other.red).coerceIn(0,255), (this.green+other.green).coerceIn(0,255), (this.blue+other.blue).coerceIn(0,255))
}
