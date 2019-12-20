import henaxel.workbench.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.NumberFormat;

//TODO: Implement bufferedImage and scaling of the image in the view

public class Raytracer extends JComponent {
    private BufferedImage image;
    private int imageWidth = 128;
    private int imageHeight = 128;
    private Environment env;
    private double t_min = 0.001;
    private double t_max = 1000.0;
    private int samples;
    private int depth;
    double gamma;
    boolean transparentBackground;


    final int MAX_FPS = 60;
    double fps;
    Timer timer;

    private int progress;
    private double renderTime;

    Raytracer() {
        setBackground(Color.MAGENTA);
    }



    void traceNormals() {
        Camera cam = env.activeCam;
        cam.updateVectors(imageWidth, imageHeight);
        progress = 0;
        long t0 = System.currentTimeMillis();
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

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
                image.setRGB(col, row, color.getRGB());
            }
            progress = (int)(100*((double)col/imageWidth));
            System.out.println(progress);
        }
        revalidate();
        repaint();
        renderTime = (System.currentTimeMillis() - t0)/1000.0;
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
        cam.updateVectors(imageWidth, imageHeight);
        progress = 0;
        long t0 = System.currentTimeMillis();
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

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
                image.setRGB(col, row, color.getRGB());
            }
            progress = (int)(100*((double)col/imageWidth));
            System.out.println(progress);
        }
        repaint();

        renderTime = (System.currentTimeMillis() - t0)/1000.0;
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
                image.setRGB(col, row, new Color(col / (float)imageWidth, 1.0f-(row / (float)imageHeight), 0.0f).getRGB());
            }
        }
        repaint();
    }


    void setSamples(int samples) {this.samples = samples; }
    void setDepth(int depth) { this.depth = depth; }
    void setEnvironment(Environment env) { this.env = env; }
    void setClipDist(double t_min, double t_max) { this.t_min = t_min; this.t_max = t_max; }

    int getProgress() { return progress; }
    double getRenderTime() { return renderTime; }


    public void saveImage(String path) {
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                bufferedImage.setRGB(x, y, image.getRGB(x, y));
            }
        }

        File outputFile = new File(path);
        try {
            ImageIO.write(bufferedImage, path.substring(path.lastIndexOf(".") + 1), outputFile);
        } catch (IOException e) {
            System.out.println("ERROR - Raytracer: saveImage() exception");
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, String.format("Saved to %s", path));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, Math.min(this.getWidth(), (imageWidth/imageHeight)*this.getHeight()), Math.min(this.getHeight(), (imageHeight/imageWidth)*this.getWidth()), null);
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
        int size = 32;
        return new Toolbar(size,
                traceNormalsTool(size),
                traceShadedTool(size),
                saveImageTool(size),
                toggleTransparentTool(size),
                resolutionTool(size),
                sampleTool(size)
        );
    }

    Tool saveImageTool(int iconSize) {
        Tool tool = new Tool();
        JButton button;

        if(getClass().getResource(String.format("res/saveImage%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/saveImage%d.png", iconSize))));
        } else {
            button = new JButton("saveImage");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        tool.add(button);

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("Enter filename with extension.");
                saveImage(path);
            }
        });

        return tool;
    }

    public Tool traceNormalsTool(int iconSize) {
        Tool tool = new Tool();
        JButton button;

        if(getClass().getResource(String.format("res/traceNormals%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/traceNormals%d.png", iconSize))));
        } else {
            button = new JButton("traceNormals");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        tool.add(button);

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                traceNormals();
            }
        });

        return tool;
    }

    public Tool traceShadedTool(int iconSize) {
        Tool tool = new Tool();
        JButton button;

        if(getClass().getResource(String.format("res/traceShaded%d.png", iconSize)) != null) {
            button = new JButton(new ImageIcon(getClass().getResource(String.format("res/traceShaded%d.png", iconSize))));
        } else {
            button = new JButton("traceShaded");
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        tool.add(button);

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                traceShaded();
            }
        });

        return tool;
    }

    public Tool toggleTransparentTool(int iconSize) {
        Tool tool = new Tool();
        JToggleButton button;

        if(getClass().getResource(String.format("res/toggleTransparent%d.png", iconSize)) != null) {
            button = new JToggleButton(new ImageIcon(getClass().getResource(String.format("res/toggleTransparent%d.png", iconSize))));
        } else {
            button = new JToggleButton("toggleTransparent", transparentBackground);
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }

        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        tool.add(button);

        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                transparentBackground = !transparentBackground;
                repaint();
            }
        });
        return tool;
    }

    public Tool resolutionTool(int iconSize) {
        Format resFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField widthField = new JFormattedTextField(resFormat);
        widthField.setValue(imageWidth);
        widthField.setColumns(6);
        widthField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            imageWidth = Integer.parseInt(widthField.getText());
            }
        });

        JFormattedTextField heightField = new JFormattedTextField(resFormat);
        heightField.setValue(imageHeight);
        heightField.setColumns(6);
        heightField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            imageHeight = Integer.parseInt(heightField.getText());
        }
        });

        Tool tool = new Tool();
        tool.setLayout(new GridLayout(2, 1));
        tool.add(widthField);
        tool.add(heightField);

        return tool;
    }

    public Tool sampleTool(int iconSize) {
        Format sampleFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField sampleField = new JFormattedTextField(sampleFormat);
        sampleField.setValue(samples);
        sampleField.setColumns(6);
        sampleField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            samples = Integer.parseInt(sampleField.getText());
        }
        });

        JFormattedTextField depthField = new JFormattedTextField(sampleFormat);
        depthField.setValue(depth);
        depthField.setColumns(6);
        depthField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            depth = Integer.parseInt(depthField.getText());
        }
        });

        Tool tool = new Tool();
        tool.setLayout(new GridLayout(2, 1));
        tool.add(sampleField);
        tool.add(depthField);

        return tool;
    }

}
