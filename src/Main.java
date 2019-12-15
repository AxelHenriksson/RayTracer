import henaxel.workbench.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        final double ASPECT_RATIO = 1.6/1.0;
        final int HEIGHT = 720;

        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), 0.5, new Lambertian(new Color(0.8f, 0.3f, 0.3f, 1.0f))));
        env.add(new Sphere(new Vec3(0, -100.5, -1), 100, new Lambertian(new Color(0.8f, 0.8f, 0.0f))));
        env.add(new Sphere(new Vec3(1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.6f, 0.2f))));
        env.add(new Sphere(new Vec3(-1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.8f, 0.8f))));
        env.activeCam = new Camera((int)(HEIGHT*ASPECT_RATIO), HEIGHT, 4.0);

        Raytracer rt = new Raytracer((int)(HEIGHT*ASPECT_RATIO), HEIGHT, 1);
        rt.setSamples(16);
        rt.setDepth(3);
        rt.gamma = 2.0;
        rt.setClipDist(0.01, 1000.0);
        rt.setEnvironment(env);

        rt.transparentBackground = true;
        rt.traceShaded();

        Workbench wb = new Workbench(rt);

        Toolbar testTB = new Toolbar(64, new Tool(new JButton("Test")), new Tool(new JButton("Test2")));
        wb.addToolbar(BorderLayout.SOUTH, rt.toolbar());



    }

}
