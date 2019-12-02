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

    public void drawCoordImage() {
    for(int col = 0; col<width;col++) {
        for (int row = 0; row < height; row++) {
            image[col][row] = new Color((int) ((col / (double)width) * 255.0), (int) ((1.0-(row / (double)height)) * 255.0), 0);
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
}
