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

        rt.drawCoordImage();

    }
}
