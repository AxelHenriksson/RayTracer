package henaxel.node;

import javax.swing.*;

public class InLink extends NodeLink {

    public InLink(String name, Object object) {
        super(name, object, SwingConstants.LEFT);
    }
}
