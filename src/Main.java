import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Environment env = new Environment();
        env.add(new Sphere(new Vec3(0, 0, -1), 0.5));

        Raytracer rt = new Raytracer((int)(1024*1.6), 1024, 2);
        rt.setEnvironment(env);

        JFrame frame = new JFrame();
        frame.getContentPane().add(rt);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        //rt.drawCoordImage();
        rt.rayTrace();

    }
}
