import henaxel.raytracer.*;
import henaxel.raytracer.materials.*;
import henaxel.utils.*;
import henaxel.workbench.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        BufferedImage testTex = ImageReader.loadBufferedImage("src/res/earthx5400x2700.jpg");
        
        /*
        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), 0.5, new Lambertian(new Color(0.8f, 0.3f, 0.3f, 1.0f))));
        env.add(new Sphere(new Vec3(0.2, 0, -0.4), 0, new Dielectric(new Color(1f, 1f, 1f), 1.3)));
        env.add(new Sphere(new Vec3(0, -1000.5, -1), 1000, new Lambertian(new CheckerTexture(new ConstantTexture(0.2, 0.2, 0.2), new ConstantTexture(0.8, 0.8, 0.8)))));
        env.add(new Sphere(new Vec3(1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.6f, 0.2f), 0.3)));
        env.add(new Sphere(new Vec3(-1, 0, -1), 0.5, new Dielectric(1.5)));
        
        int n = 500;
        for(int a = -11; a < 11; a++) {
            for(int b = -11; b < 11; b++) {
                n--;
                double chooseMat = Math.random();
                Vec3 center = new Vec3(a+0.9*Math.random(), -0.3, b+0.9*Math.random());
                if(Vec3.subtract(center, new Vec3(4, 0.2, 0)).length() > 0.9) {
                    if(chooseMat < 0.7) {
                        env.add(new Sphere(center, 0.2, new Lambertian(Math.random()*Math.random(), Math.random()*Math.random(), Math.random()*Math.random())));
                    } else if (chooseMat < 0.90) {
                        env.add(new Sphere(center, 0.2, new Metal(Math.random()*Math.random(), Math.random()*Math.random(), Math.random()*Math.random(), Math.random())));
                    } else {
                        env.add(new Sphere(center, 0.2, new Dielectric(Math.random()*2.0)));
                    }
                }
                if(n <= 0) break;
            }
            if(n <= 0) break;
        }
        
        Vec3 lookFrom = new Vec3(3,1,2);
        Vec3 lookAt = new Vec3(0, 0, -1);
        env.activeCam = new Camera(
                lookFrom,
                lookAt,
                new Vec3(0, 1, 0),
                50,
                Defaults.IMAGE_WIDTH/(double)Defaults.IMAGE_HEIGHT,
                0.05, Vec3.subtract(lookFrom, lookAt).length()
                );
        
        env.constructBVH();
        */
        
        Environment twoSpheres = new Environment();
        Texture checker = new CheckerTexture(new ConstantTexture(0.2, 0.3, 0.1), new ConstantTexture(0.9, 0.9, 0.9));
        Texture perTex = new NoiseTexture(1, 3);
        twoSpheres.add(new Sphere(new Vec3(0,-1000,0), 1000, new Lambertian(perTex)));
        twoSpheres.add(new Sphere(new Vec3(0,2,0), 2, new Lambertian(new ImageTexture(testTex))));
    
        Vec3 lookFrom2 = new Vec3(6,6,6);
        Vec3 lookAt2 = new Vec3(0,2,0);
        twoSpheres.activeCam = new Camera(lookFrom2, lookAt2, new Vec3(0,1,0), 40, 1, 0.2, Vec3.subtract(lookFrom2, lookAt2).length());
        
        Raytracer rt = new Raytracer();
        rt.setEnvironment(twoSpheres);

        Workbench wb = new Workbench(rt);
        wb.addToolbar(BorderLayout.EAST, rt.toolbar());
    
        rt.traceShaded(true);



    }

}
