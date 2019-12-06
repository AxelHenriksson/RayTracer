import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), 0.5, new Lambertian(new Color(0.8f, 0.3f, 0.3f))));
        env.add(new Sphere(new Vec3(-0.2, 0, -0.8), 0.2, new Lambertian(new Color(0.1f, 0.1f, 1f))));
        env.add(new Sphere(new Vec3(0, -100.5, -1), 100, new Lambertian(new Color(0.8f, 0.8f, 0.0f))));
        env.add(new Sphere(new Vec3(1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.6f, 0.2f))));
        env.add(new Sphere(new Vec3(-1, 0, -1), 0.5, new Metal(new Color(0.8f, 0.8f, 0.8f))));
        env.activeCam = new Camera((int)(1024*(16/10.0)), 1024, 4.0);

        Raytracer rt = new Raytracer((int)(1024*(16/10.0)), 1024, 1);
        rt.setSamples(100);
        rt.setDepth(10);
        rt.gamma = 2.0;
        rt.setClipDist(0.0, 1000.0);
        rt.setEnvironment(env);

        JFrame frame = new JFrame();
        frame.getContentPane().add(rt);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


        rt.trace();

    }
}
