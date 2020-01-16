package henaxel.raytracer;

import henaxel.workbench.*;
import henaxel.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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


public class Raytracer extends JComponent {
    private BufferedImage image;
    private Environment env;
    private int imageWidth = Defaults.IMAGE_WIDTH;
    private int imageHeight = Defaults.IMAGE_HEIGHT;
    private double t_min = Defaults.CLIP_MIN;
    private double t_max = Defaults.CLIP_MAX;
    private int samples = Defaults.SAMPLES;
    private int depth = Defaults.DEPTH;
    private double gamma = Defaults.GAMMA;
    private boolean transparentBackground = Defaults.TRANSPARENT;
    private Thread[] renderThreads;
    private int threadNumber = Defaults.RTHREAD_NUMBER;

    double fps;
    Timer timer;

    private double progress;
    private double renderTime;


    public void traceNormals(boolean logProgress) {

        Thread thread = new Thread(() -> {
            Camera cam = env.activeCam;
            cam.setAspectRatio((double)imageWidth/imageHeight);
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
                //TODO: Implement proper progress tracking as tool
                progress = Math.max(0.1, (100.0*((double)col/imageWidth)));
                long time = System.currentTimeMillis();
                double estimatedTime = ((((time-t0)*100.0)/progress)/1000.0) - ((time-t0)/1000.0);
                if(logProgress) { System.out.printf("Rendering Normals | Progress: %3.0f%c | ETR: %1dh %2dm %2ds\n", progress, '%', (int)estimatedTime/3600, (int)(estimatedTime/60)%60, (int)estimatedTime%60); }

            }
            repaint();
            renderTime = (System.currentTimeMillis() - t0)/1000.0;

        }); thread.start();

    }
    private Color colorNormal(Ray r) {
        HitResult hr = env.hit(r,  t_min, t_max);
        if(hr != null) {
            return new Color((float)((hr.n.x+1)*0.5), (float)((hr.n.y+1)*0.5), (float)((hr.n.z+1)*0.5), 1.0f);
        }
            return getBackground(r);
    }
    
    
    public void traceShaded(boolean gradualRepaint) {
        if (renderThreads != null) {
            for (Thread thread : renderThreads) {
                thread.interrupt();
            }
        }

        Camera cam = env.activeCam;
        cam.setAspectRatio((double)imageWidth/imageHeight);
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        //TODO: look over pixel lost when  dividing threadcells
        int threadWidth = (int) Math.sqrt(threadNumber);
        int threadHeight = threadNumber/threadWidth;
        int cellWidth = imageWidth/threadWidth;
        int cellHeight = imageHeight/threadHeight;
        renderThreads = new Thread[threadNumber];

        for(int cellX = 0; cellX < threadWidth; cellX++) {
            for(int cellY = 0; cellY < threadHeight; cellY++) {
                int finalCellX = cellX;
                int finalCellY = cellY;

                Thread thread = new Thread(() -> {
                    for(int col = finalCellX*cellWidth; col< (finalCellX+1)*cellWidth; col++) {
                        for (int row = finalCellY*cellHeight; row < (finalCellY+1)*cellHeight; row++) {
                            if(Thread.currentThread().isInterrupted()) return;
                            float red = 0, green = 0, blue = 0, alpha = 0;
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
                        if(gradualRepaint) repaint();
                    }
                    repaint();

                });
                renderThreads[cellX + cellY*threadWidth] = thread;
                thread.start();
            }
        }
    }
    private Color colorShaded(Ray r, int depth) {
        HitResult hr = env.hit(r, t_min, t_max);
        if (hr == null) { return getBackground(r); }
        if (depth >= this.depth || hr.scattered == null) { return hr.attenuation; }

        return Utils.multiply(colorShaded(hr.scattered, depth+1), hr.attenuation);
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
    
    
    public void setSamples(int samples) {this.samples = samples; }
    public void setDepth(int depth) { this.depth = depth; }
    public void setEnvironment(Environment env) { this.env = env; }
    public void setClipDist(double t_min, double t_max) { this.t_min = t_min; this.t_max = t_max; }
    
    public double getProgress() { return progress; }
    public double getRenderTime() { return renderTime; }


    public void saveImage(String path) {
        BufferedImage bufferedImage;
        switch(path.substring(path.lastIndexOf(".") + 1).toLowerCase()) {
            case "png" :
                bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                break;
            case "jpg" :
                bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
                break;
            default :
                bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                break;
        }


        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                bufferedImage.setRGB(x, y, image.getRGB(x, y));
            }
        }

        File outputFile = new File(path);
        try {
            ImageIO.write(bufferedImage, path.substring(path.lastIndexOf(".") + 1), outputFile);
        } catch (IOException e) {
            System.out.println("ERROR - henaxel.raytracer.Raytracer: saveImage() exception");
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, String.format("Saved as %s", path));
    }

    @Override
    public void paintComponent(Graphics g) {
        int height = Math.min(this.getHeight(), (int)(((double)imageHeight/imageWidth)*this.getWidth()));
        int width = Math.min(this.getWidth(), (int)(((double)imageWidth/imageHeight)*this.getHeight()));
        
        g.drawImage(image, (this.getWidth()-width)/2, (this.getHeight()-height)/2, width, height, null);
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

        Surface spinningSurface;

        TraceLoop() {
            lastMS = System.currentTimeMillis();
        }
    @Override
    public void actionPerformed(ActionEvent e) {
            try {
                Thread.sleep((long) Math.max(0, (1.0 / Defaults.MAX_FPS) * 1000 - (System.currentTimeMillis() - lastMS)));
            } catch (InterruptedException exc) { exc.printStackTrace(); }
            deltaMS = System.currentTimeMillis()-lastMS;
            fps = (1.0/deltaMS)/1000.0;
            lastMS = System.currentTimeMillis();
            traceShaded(false);
        }
    }







    // TOOLS ---------------------------

    public Toolbar toolbar() {
        int size = 32;
        return new Toolbar(size,
                saveImageTool(size),
                traceNormalsTool(size),
                traceShadedTool(size),
                toggleTransparentTool(size),
                resolutionTool(size),
                samDepTool(size),
                threadCountTool(size)
        );
    }

    Tool saveImageTool(int iconSize) {
        return Tool.buildButtonTool(
                "Save Image",
                "saveImage",
                iconSize,
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        String path = JOptionPane.showInputDialog("Enter filename with extension.");
                        if (path == null) return;
                        saveImage("src/out/" + path);
                    }
                }
                );
    }

    public Tool traceNormalsTool(int iconSize) {
        return Tool.buildButtonTool(
                "Trace Normals",
                "traceNormals",
                iconSize,
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        traceNormals(true);
                    }
                }
        );
    }

    public Tool traceShadedTool(int iconSize) {
        return Tool.buildButtonTool(
                "Trace Shaded",
                "traceShaded",
                iconSize,
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        traceShaded(true);
                    }
                }
        );
    }

    public Tool toggleTransparentTool(int iconSize) {
        return Tool.buildToggleButtonTool(
                "Transparent",
                "toggleTransparent",
                iconSize,
                transparentBackground,
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        transparentBackground = !transparentBackground;
                    }
                }
        );
    }

    public Tool resolutionTool(int iconSize) {
        JFormattedTextField widthField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        widthField.setValue(imageWidth);
        widthField.setColumns(4);
        widthField.setBorder(new TitledBorder(new LineBorder(Color.black), "Width"));
        widthField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            imageWidth = Integer.parseInt(widthField.getText());
            }
        });

        JFormattedTextField heightField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        heightField.setValue(imageHeight);
        heightField.setColumns(4);
        heightField.setBorder(new TitledBorder(new LineBorder(Color.black), "Height"));
        heightField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            imageHeight = Integer.parseInt(heightField.getText());
        }
        });

        Tool tool = new Tool(new GridLayout(2, 1));
        tool.add(widthField);
        tool.add(heightField);

        return tool;
    }

    public Tool samDepTool(int iconSize) {
        JFormattedTextField sampleField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        sampleField.setValue(samples);
        sampleField.setColumns(4);
        sampleField.setBorder(new TitledBorder(new LineBorder(Color.black), "Samples"));
        sampleField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            samples = Integer.parseInt(sampleField.getText());
        }
        });

        JFormattedTextField depthField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        depthField.setValue(depth);
        depthField.setColumns(4);
        depthField.setBorder(new TitledBorder(new LineBorder(Color.black), "Depth"));
        depthField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            depth = Integer.parseInt(depthField.getText());
        }
        });

        Tool tool = new Tool(new GridLayout(2, 1));
        tool.add(sampleField);
        tool.add(depthField);

        return tool;
    }

    public Tool threadCountTool(int iconSize) {
        JFormattedTextField threadField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        threadField.setValue(threadNumber);
        threadField.setColumns(4);
        threadField.setBorder(new TitledBorder(new LineBorder(Color.black), "Threads"));
        threadField.addPropertyChangeListener("value", new PropertyChangeListener()
        {public void propertyChange(PropertyChangeEvent evt) {
            threadNumber = Integer.parseInt(threadField.getText());
        }
        });

        Tool tool = new Tool(new GridLayout(1, 1));
        tool.add(threadField);

        return tool;
    }

}
