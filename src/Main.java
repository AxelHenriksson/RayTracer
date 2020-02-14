import henaxel.raytracer.*;
import henaxel.raytracer.materials.*;
import henaxel.raytracer.node.NodeEditor;
import henaxel.raytracer.utils.*;
import henaxel.workbench.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {


        BufferedImage testTex = ImageReader.loadBufferedImage("src/res/earthx5400x2700.jpg");
        
        Environment twoSpheres = new Environment();
        Texture checker = new CheckerTexture(new ConstantTexture(0.2, 0.3, 0.1), new ConstantTexture(0.9, 0.9, 0.9));
        Texture perTex = new NoiseTexture(1, 3);
        twoSpheres.add(new Sphere(new Vec3(0,-1000,0), 1000, new Lambertian(new ImageTexture(testTex))));

        Sphere metalSphere = new Sphere(new Vec3(0,2,0), 2, new Metal(checker, 0.2));
        twoSpheres.add(metalSphere);

        Vec3 lookFrom2 = new Vec3(6,6,6);
        Vec3 lookAt2 = new Vec3(0,2,0);
        twoSpheres.activeCam = new Camera(lookFrom2, lookAt2, new Vec3(0,1,0), 40, 1, 0.2, Vec3.subtract(lookFrom2, lookAt2).length());
        
        Raytracer rt = new Raytracer();
        rt.setEnvironment(twoSpheres);


        Workbench wb = new Workbench(rt);
        //wb.add(new TabbedBar(rt.actionBar(), rt.propertiesBar()) BorderLayout.NORTH);
        wb.add(rt.actionBar(), BorderLayout.NORTH);
        wb.add(rt.propertiesBar(), BorderLayout.EAST);


        //rt.traceShaded(true);

        
        NodeEditor ne = new NodeEditor();
        ne.add(ne.toolbarBasicTools(), BorderLayout.NORTH);
        ne.setNodeClassList(new Class[] {
                Metal.class,
                        Lambertian.class,
                        Dielectric.class,
                        ImageTexture.class,
                }
                );
        metalSphere.getMaterial().nodeEdit(ne);
        



    }

}
