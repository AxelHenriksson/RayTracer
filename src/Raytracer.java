import javax.swing.*;
import java.awt.*;

public class Raytracer extends JComponent {
    private Color[][] image;
    private int imageWidth, imageHeight, pixSize;
    private Environment env;
    private double T_MIN = 0.0;
    private double T_MAX = 1000.0;

    public Raytracer(int width, int height, int pixSize) {
        this.imageWidth = width / pixSize;
        this.imageHeight = height / pixSize;
        this.pixSize = pixSize;

        image = new Color[imageWidth][imageHeight];

        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width-(width%pixSize), height-(height%pixSize)));
    }




    public void rayTrace() {
        double viewPlaneWidth = 4.0;
        double viewPlaneHeight = viewPlaneWidth*((double) imageHeight/imageWidth);

        Vec3 upperLeft = new Vec3(-viewPlaneWidth/2.0, viewPlaneHeight/2.0, -1.0);
        Vec3 horizontal = new Vec3(viewPlaneWidth, 0.0, 0.0);
        Vec3 vertical = new Vec3(0.0, -viewPlaneHeight, 0.0);
        Vec3 origin = new Vec3(0, 0, 0);

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                double u = ((double) col)/ imageWidth;
                double v = ((double) row)/ imageHeight;
                Ray r = new Ray(origin, Vec3.add(upperLeft, Vec3.multiply(horizontal, u), Vec3.multiply(vertical, v)));
                image[col][row] = color(r);
            }
        }
        repaint();
    }

    private Color color(Ray r) {
        HitResult hr = env.hit(r, T_MIN, T_MAX);
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
