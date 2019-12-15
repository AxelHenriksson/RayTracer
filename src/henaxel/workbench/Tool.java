package henaxel.workbench;

import javax.swing.*;
import java.awt.*;

public class Tool extends JPanel {
    JComponent component;

    public Tool(JButton button) {
        component = button;
        add(button);
    }

    public void setWidth(int width) {
        component.setPreferredSize(new Dimension(width, width));
    }
}
