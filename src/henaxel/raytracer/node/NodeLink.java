package henaxel.raytracer.node;

import henaxel.node.BaseNodeLink;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public abstract class NodeLink extends BaseNodeLink {

    protected NodeLink(String name, Object object, int horizontalAlignment) {
        super(name, horizontalAlignment);
        setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, Color.darkGray), new EmptyBorder(0, 2, 0, 2)));
    }

}
