import henaxel.workbench.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        BufferedImage testTex = ImageReader.loadBufferedImage("src/res/earthx5400x2700.jpg");

        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), new Vec3(0, 0, 1), 0.5, new Lambertian(testTex))); //new Lambertian(new Color(0.8f, 0.3f, 0.3f, 1.0f))
        //env.add(new Sphere(new Vec3(0, 0, -1), 0.5, new Lambertian(new Color(0.8f, 0.3f, 0.3f, 1.0f))));
        env.add(new Sphere(new Vec3(0.2, 0, -0.4), 0, new Dielectric(new Color(1f, 1f, 1f), 1.3)));
        env.add(new Sphere(new Vec3(0, -100.5, -1), 100, new Lambertian(new Color(0.8f, 0.8f, 0.0f))));
        env.add(new Sphere(new Vec3(1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.6f, 0.2f), 0.3)));
        env.add(new Sphere(new Vec3(-1, 0, -1), 0.5, new Dielectric(1.0)));
        env.add(new Sphere(new Vec3(-1, 0, -1), -0.45, new Dielectric(1.0)));
        env.activeCam = new Camera(5.0);

        Raytracer rt = new Raytracer();
        rt.setEnvironment(env);

        Workbench wb = new Workbench(rt);
        wb.addToolbar(BorderLayout.EAST, rt.toolbar());



    }

}
