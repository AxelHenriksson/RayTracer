import javax.swing.*;
import java.awt.*;

public class Raytracer extends JComponent {
    private Color[][] image;
    private int width, height, pixSize;

    public Raytracer(int width, int height, int pixSize) {
        this.width = width / pixSize;
        this.height = height / pixSize;
        this.pixSize = pixSize;

        image = new Color[this.width][this.height];

        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width-(width%pixSize), height-(height%pixSize)));
    }



    Color testColor(Ray r) {
        Vec3 unitDir = r.direction().unitVector();
        double t = 0.5 * (unitDir.y + 1.0);
        return Utils.lerp(new Color(1.0f, 1.0f, 1.0f), new Color(0.5f, 0.7f, 1.0f), t);
    }

    public void rayTrace() {
        Vec3 lowerLeft = new Vec3(-2.0, -1.0, -1.0);
        Vec3 horizontal = new Vec3(4.0, 0.0, 0.0);
        Vec3 vertical = new Vec3(0.0, 2.0, 0.0);
        Vec3 origin = new Vec3(0, 0, 0);

        for(int col = 0; col<width;col++) {
            for (int row = 0; row < height; row++) {
                double u = ((double) col)/width;
                double v = ((double) row)/height;
                Ray r = new Ray(origin, lowerLeft.add(horizontal.mult(u).add(vertical.mult(v))));
                image[col][row] = testColor(r);
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        for(int col = 0; col < width; col++) {
            for(int row = 0; row < height; row++) {
                g.setColor(image[col][row]);
                g.fillRect(col*pixSize, row*pixSize, pixSize, pixSize);
            }
        }
    }

    /*
        public void drawCoordImage() {
        for(int col = 0; col<width;col++) {
            for (int row = 0; row < height; row++) {
                image[col][row] = new Color(col / (float)width, 1.0f-(row / (float)height), 0.0f);
            }
        }
        repaint();
    }
     */
}
