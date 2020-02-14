package henaxel.workbench;

import javax.swing.*;
import java.awt.*;


public class Toolbar extends JPanel {
    private int width;

    public Toolbar(String name, int width, Tool... tools) {
        setName(name);
        this.width = width;
        addTools(tools);

    }

    public void addTools(Tool... tools) {
        for(Tool tool : tools) {
            add(tool);
        }
    }

    public void setAnchor(String anchor) {
        switch(anchor) {
            case BorderLayout.NORTH: case BorderLayout.SOUTH:
                setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                break;
            case BorderLayout.WEST: case BorderLayout.EAST:
                setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                break;
        }
    }

}
