package henaxel.raytracer.node;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class NInputPanel extends JLabel {
    protected NInputPanel(String name, Object object) {
        super(name, SwingConstants.LEFT);
        setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, Color.blue), new CompoundBorder(new MatteBorder(0, 0, 1, 0, Color.darkGray), new EmptyBorder(0, 0, 0, 2))));
    }
}
