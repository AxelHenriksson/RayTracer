import henaxel.workbench.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Raytracer extends JComponent {
    private Color[][] image;
    private int imageWidth, imageHeight, pixSize;
    private Environment env;
    private double t_min = 0.001;
    private double t_max = 1000.0;
    private int samples;
    private int depth;
    double gamma;
    boolean transparentBackground = false;


    final int MAX_FPS = 60;
    double fps;
    Timer timer;

    private int progress;

    Raytracer(int width, int height, int pixSize) {
        this.imageWidth = width / pixSize;
        this.imageHeight = height / pixSize;
        this.pixSize = pixSize;

        image = new Color[imageWidth][imageHeight];

        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width-(width%pixSize), height-(height%pixSize)));
    }



    void traceNormals() {
        Camera cam = env.activeCam;
        progress = 0;

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                float red = 0;
                float green = 0;
                float blue = 0;
                float alpha = 0;
                for(int s = 0; s < samples; s++) {
                    double u = ((double) col + Math.random()) / imageWidth;
                    double v = ((double) row + Math.random()) / imageHeight;
                    Ray r = cam.getRay(u, v);
                    Color sColor = colorNormal(r);
                    red += sColor.getRed()/255.0;
                    green += sColor.getGreen()/255.0;
                    blue += sColor.getBlue()/255.0;
                    alpha += sColor.getAlpha()/255.0;
                }
                Color color = new Color( red/samples,  green/samples,  blue/samples, alpha/samples);
                image[col][row] = color;
            }
            progress = (int)(100*((double)col/imageWidth));
        }
        revalidate();
        repaint();
    }
    private Color colorNormal(Ray r) {
        HitResult hr = env.hit(r,  t_min, t_max);
        if(hr != null) {
            return new Color((float)((hr.n.x+1)*0.5), (float)((hr.n.y+1)*0.5), (float)((hr.n.z+1)*0.5), 1.0f);
        }
            return getBackground(r);
    }


    void traceShaded() {
        Camera cam = env.activeCam;
        progress = 0;

        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                float red = 0;
                float green = 0;
                float blue = 0;
                float alpha = 0;
                for(int s = 0; s < samples; s++) {
                    double u = ((double) col + Math.random()) / imageWidth;
                    double v = ((double) row + Math.random()) / imageHeight;
                    Ray r = cam.getRay(u, v);
                    Color sColor = colorShaded(r, 0);
                    red += sColor.getRed()/255.0;
                    green += sColor.getGreen()/255.0;
                    blue += sColor.getBlue()/255.0;
                    alpha += sColor.getAlpha()/255.0;
                }
                Color color = new Color( red/samples,  green/samples,  blue/samples, alpha/samples);
                color = Utils.correctGamma(color, gamma);
                image[col][row] = color;
            }
            progress = (int)(100*((double)col/imageWidth));
        }
        repaint();
    }
    private Color colorShaded(Ray r, int depth) {
        HitResult hr = env.hit(r, t_min, t_max);
        if (depth < this.depth && hr != null) {
            return Utils.multiply(colorShaded(hr.scattered, depth+1), hr.attenuation);
        }
        return getBackground(r);
    }

    private Color getBackground(Ray r) {
        Vec3 unitDir = r.direction().unitVector();
        double t = 0.5 * (unitDir.y + 1.0);
        if(!transparentBackground) {
            return Utils.lerp(new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(0.5f, 0.7f, 1.0f, 1.0f), t);
        }
        return Utils.lerp(new Color(1.0f, 1.0f, 1.0f, 0.0f), new Color(0.5f, 0.7f, 1.0f, 0.0f), t);
    }

    public void drawCoordImage() {
        for(int col = 0; col<imageWidth;col++) {
            for (int row = 0; row < imageHeight; row++) {
                image[col][row] = new Color(col / (float)imageWidth, 1.0f-(row / (float)imageHeight), 0.0f);
            }
        }
        repaint();
    }


    void setSamples(int samples) {this.samples = samples; }
    void setDepth(int depth) { this.depth = depth; }
    void setEnvironment(Environment env) { this.env = env; }
    void setClipDist(double t_min, double t_max) { this.t_min = t_min; this.t_max = t_max; }

    int getProgress() { return progress; }


    public void saveImage(String path) {
        BufferedImage bufferedImage = new BufferedImage(image.length, image[0].length,
                BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[x].length; y++) {
                bufferedImage.setRGB(x, y, image[x][y].getRGB());
            }
        }

        File outputFile = new File(path);
        try {
            ImageIO.write(bufferedImage, path.substring(path.lastIndexOf(".") + 1), outputFile);
        } catch (IOException e) {
            System.out.println("Raytracer: saveImage() exception");
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, String.format("Saved to %s", path));
    }

    @Override
    public void paintComponent(Graphics g) {
        for(int col = 0; col < imageWidth; col++) {
            for(int row = 0; row < imageHeight; row++) {
                g.setColor(image[col][row]);
                g.fillRect(col*pixSize, row*pixSize, pixSize, pixSize);
            }
        }
    }



    public void traceLoop() {
        TraceLoop loop = new TraceLoop();
        timer = new Timer(1, loop);
        timer.setInitialDelay(1);
        timer.start();
    }
    private class TraceLoop implements ActionListener {
        long lastMS;
        long deltaMS;

        TraceLoop() {
            lastMS = System.currentTimeMillis();
        }
    @Override
    public void actionPerformed(ActionEvent e) {
            try {
                Thread.sleep((long) Math.max(0, (1.0 / MAX_FPS) * 1000 - (System.currentTimeMillis() - lastMS)));
            } catch (InterruptedException exc) { exc.printStackTrace(); }
        deltaMS = System.currentTimeMillis()-lastMS;
            fps = (1.0/deltaMS)/1000.0;
        traceShaded();
        }
    }


    // TOOLS ---------------------------

    public Toolbar toolbar() {
        int size = 64;
        return new Toolbar(size, traceNormalsTool(size), traceShadedTool(size), saveImageTool(size), toggleTransparentTool(size));
    }

    Tool saveImageTool(int iconSize) {
        JButton button;
        if(getClass().getResource(String.format("res/saveImage%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/saveImage%d.png", iconSize))));
        } else {
            button = new JButton("saveImageTool");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("Enter filename and extension.");
                saveImage(path);
            }
        });
        return new Tool(button);
    }

    public Tool traceNormalsTool(int iconSize) {
        JButton button;
        if(getClass().getResource(String.format("res/traceNormals%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/traceNormals%d.png", iconSize))));
        } else {
            button = new JButton("traceNormals");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                traceNormals();
            }
        });
        return new Tool(button);
    }

    public Tool traceShadedTool(int iconSize) {
        JButton button;
        if(getClass().getResource(String.format("res/traceShaded%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/traceShaded%d.png", iconSize))));
        } else {
            button = new JButton("traceShaded");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                traceShaded();
            }
        });
        return new Tool(button);
    }

    public Tool toggleTransparentTool(int iconSize) {
        JButton button;
        if(getClass().getResource(String.format("res/toggleTransparent%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/toggleTransparent%d.png", iconSize))));
        } else {
            button = new JButton("toggleTransparent");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                transparentBackground = !transparentBackground;
                repaint();
            }
        });
        return new Tool(button);
    }

}
