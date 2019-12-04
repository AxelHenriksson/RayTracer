import javax.swing.*;
import java.awt.*;

public class Raytracer extends JComponent {
    private Color[][] image;
    private int imageWidth, imageHeight, pixSize;
    private Environment env;
    private double t_min = 0.0;
    private double t_max = 1000.0;

    public Raytracer(int width, int height, int pixSize) {
        this.imageWidth = width / pixSize;
        this.imageHeight = height / pixSize;
        this.pixSize = pixSize;

        image = new Color[imageWidth][imageHeight];

        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width-(width%pixSize), height-(height%pixSize)));
    }



    public void traceNormals() {
        Camera cam = env.activeCamera;

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                double u = ((double) col)/ imageWidth;
                double v = ((double) row)/ imageHeight;
                Ray r = cam.getRay(u, v);
                image[col][row] = color(r);
            }
        }
        repaint();
    }

    private Color color(Ray r) {
        HitResult hr = env.hit(r,  t_min, t_max);
        if(hr != null) {
            return new Color((float)((hr.n.x+1)*0.5), (float)((hr.n.y+1)*0.5), (float)((hr.n.z+1)*0.5));
        }
        return getBackground(r);
    }

    private Color getBackground(Ray r) {
        Vec3 unitDir = r.direction().unitVector();
        double t = 0.5 * (unitDir.y + 1.0);
        return Utils.lerp(new Color(1.0f, 1.0f, 1.0f), new Color(0.5f, 0.7f, 1.0f), t);
    }


    public void setEnvironment(Environment env) { this.env = env; }
    public void setClipDist(double t_min, double t_max) { this.t_min = t_min; this.t_max = t_max; }

    @Override
    public void paintComponent(Graphics g) {
        for(int col = 0; col < imageWidth; col++) {
            for(int row = 0; row < imageHeight; row++) {
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
