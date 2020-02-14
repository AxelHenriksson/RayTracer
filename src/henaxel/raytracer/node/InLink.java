package henaxel.raytracer.node;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class InLink extends NodeLink {

    public InLink(String name, Object object) {
        super(name, object, SwingConstants.LEFT);
    }
}
