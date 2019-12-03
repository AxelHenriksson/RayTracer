import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Raytracer rt = new Raytracer((int)(1024*1.6), 1024, 2);

        JFrame frame = new JFrame();
        frame.getContentPane().add(rt);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        //rt.drawCoordImage();
        rt.rayTrace();

        Ray testRay = new Ray(new Vec3(1.5, 2.1, 3.2), new Vec3(7.5, 2.3, 9.8));
        System.out.println(testRay);

    }
}
