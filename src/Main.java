import henaxel.raytracer.*;
import henaxel.raytracer.materials.*;
import henaxel.utils.*;
import henaxel.workbench.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        BufferedImage testTex = ImageReader.loadBufferedImage("src/res/earthx5400x2700.jpg");
        
        
        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), 0.5, new Lambertian(new Color(0.8f, 0.3f, 0.3f, 1.0f))));
        env.add(new Sphere(new Vec3(0.2, 0, -0.4), 0, new Dielectric(new Color(1f, 1f, 1f), 1.3)));
        env.add(new Sphere(new Vec3(0, -100.5, -1), 100, new Lambertian(new Color(0.8f, 0.8f, 0.0f))));
        env.add(new Sphere(new Vec3(1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.6f, 0.2f), 0.3)));
        env.add(new Sphere(new Vec3(-1, 0, -1), 0.5, new Dielectric(1.5)));
        //env.add(new Sphere(new Vec3(-1, 0, -1), -0.45, new Dielectric(1.5)));
        
        
        
        Vec3 lookFrom = new Vec3(3,1,2);
        Vec3 lookAt = new Vec3(0, 0, -1);
        env.activeCam = new Camera(
                lookFrom,
                lookAt,
                new Vec3(0, 1, 0),
                30,
                Defaults.IMAGE_WIDTH/(double)Defaults.IMAGE_HEIGHT,
                0.05, Vec3.subtract(lookFrom, lookAt).length()
                );
        
        Raytracer rt = new Raytracer();
        rt.setEnvironment(env);

        Workbench wb = new Workbench(rt);
        wb.addToolbar(BorderLayout.EAST, rt.toolbar());
    
        rt.traceShaded(true);



    }

}
