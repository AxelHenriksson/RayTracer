import henaxel.raytracer.*
import henaxel.raytracer.materials.*
import henaxel.raytracer.utils.ImageReader
import henaxel.raytracer.utils.Vec3
import henaxel.workbench.Workbench
import java.awt.BorderLayout
import java.awt.Color

fun main() {
    val testTex = ImageReader.loadBufferedImage("src/res/earthx5400x2700.jpg")

    val lookFrom2 = Vec3(12, 6, 16)
    val lookAt2 = Vec3(0, 2, 0)

    val twoSpheres = Environment(
        Camera(0,0,lookFrom2, lookAt2, Vec3(0, 1, 0), 40.0, 0.2, (lookFrom2 - lookAt2).length())
    )
    val checker: Texture = CheckerTexture(ConstantTexture(Color(0.2f, 0.3f, 0.1f)), ConstantTexture(Color(0.9f, 0.9f, 0.9f)))
    val perTex: Texture = NoiseTexture(1.0, 3)
    twoSpheres.hitables.add(Sphere(Vec3(0, -1000, 0), 1000.0, Lambertian(ImageTexture(testTex!!))))

    twoSpheres.hitables.add(Box(Vec3(-2, 0, -2), Vec3(2, 4, 2), Lambertian(ConstantTexture(Color(0.9f, 0.4f, 0.1f)))))

    val smallBox1 = Box(Vec3(1, 0, 1), Vec3(3, 2, 3), Metal(ConstantTexture(Color(1.0f, 1.0f, 1.0f)), ConstantTexture(Color(0.5f, 0f, 0.5f))))
    twoSpheres.hitables.add(smallBox1)

    val smallBox2 = Box(Vec3(1, 0, 1), Vec3(3, 2, 3), Metal(ConstantTexture(Color(0.9f,0.9f,0.9f)), ConstantTexture(Color(0.9f, 0.9f, 0.5f))))
    smallBox2.move(Vec3(0.5, 0.0, -4.0))
    twoSpheres.hitables.add(smallBox2)

    val metalSphere = Sphere(Vec3(-5, 3, 3), 3.0, Metal(ImageTexture(testTex),ConstantTexture(Color(0.8f,0.8f,0.8f))))
    twoSpheres.hitables.add(metalSphere)

    val rt = Raytracer(twoSpheres)

    val wb = Workbench(rt)
    //wb.add(new TabbedBar(rt.actionBar(), rt.propertiesBar()) BorderLayout.NORTH);
    //wb.add(new TabbedBar(rt.actionBar(), rt.propertiesBar()) BorderLayout.NORTH);
    wb.add(rt.actionBar(), BorderLayout.NORTH)
    //wb.add(rt.propertiesBar(), BorderLayout.EAST)
}