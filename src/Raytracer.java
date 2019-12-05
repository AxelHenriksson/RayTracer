import javax.swing.*;
import java.awt.*;

public class Raytracer extends JComponent {
    private Color[][] image;
    private int imageWidth, imageHeight, pixSize;
    private Environment env;
    private double t_min = 0.001;
    private double t_max = 1000.0;
    private int samples;
    double gamma;

    public Raytracer(int width, int height, int pixSize) {
        this.imageWidth = width / pixSize;
        this.imageHeight = height / pixSize;
        this.pixSize = pixSize;

        image = new Color[imageWidth][imageHeight];

        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width-(width%pixSize), height-(height%pixSize)));
    }



    public void traceNormals() {
        Camera cam = env.activeCam;

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for(int s = 0; s < samples; s++) {
                    double u = ((double) col + Math.random()) / imageWidth;
                    double v = ((double) row + Math.random()) / imageHeight;
                    Ray r = cam.getRay(u, v);
                    Color sColor = colorNormal(r);
                    red += sColor.getRed()/255.0;
                    green += sColor.getGreen()/255.0;
                    blue += sColor.getBlue()/255.0;
                }
                Color color = new Color((float) red/samples, (float) green/samples, (float) blue/samples);
                image[col][row] = color;
            }
        }
        repaint();
    }
    private Color colorNormal(Ray r) {
        HitResult hr = env.hit(r,  t_min, t_max);
        if(hr != null) {
            return new Color((float)((hr.n.x+1)*0.5), (float)((hr.n.y+1)*0.5), (float)((hr.n.z+1)*0.5));
        }
        return getBackground(r);
    }

    public void traceDiffuse() {
        Camera cam = env.activeCam;

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for(int s = 0; s < samples; s++) {
                    double u = ((double) col + Math.random()) / imageWidth;
                    double v = ((double) row + Math.random()) / imageHeight;
                    Ray r = cam.getRay(u, v);
                    Color sColor = colorDiffuse(r);
                    red += sColor.getRed()/255.0;
                    green += sColor.getGreen()/255.0;
                    blue += sColor.getBlue()/255.0;
                }
                Color color = new Color((float) red/samples, (float) green/samples, (float) blue/samples);
                color = Utils.correctGamma(color, gamma);
                image[col][row] = color;
            }
        }
        repaint();
    }

    private Color colorDiffuse(Ray r) {
        HitResult hr = env.hit(r, t_min, t_max);
        if(hr != null) {
            Vec3 target = Vec3.add(hr.pos, hr.n, Vec3.randomInUnitSphere());
            return Utils.multiply(colorDiffuse(new Ray(hr.pos, Vec3.subtract(target, hr.pos))), 0.5);
        }
        return getBackground(r);
    }

    private Color getBackground(Ray r) {
        Vec3 unitDir = r.direction().unitVector();
        double t = 0.5 * (unitDir.y + 1.0);
        return Utils.lerp(new Color(1.0f, 1.0f, 1.0f), new Color(0.5f, 0.7f, 1.0f), t);
    }

    void setSamples(int samples) {this.samples = samples; }
    void setEnvironment(Environment env) { this.env = env; }
    void setClipDist(double t_min, double t_max) { this.t_min = t_min; this.t_max = t_max; }

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
