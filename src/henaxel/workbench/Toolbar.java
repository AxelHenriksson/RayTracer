package henaxel.workbench;

import javax.swing.*;
import java.awt.*;


public class Toolbar extends JPanel {
    private int width;

    public Toolbar(int width, Tool... tools) {
        this.width = width;

        for(Tool tool : tools) {
            addTools(tool);
        }
    }

    public void addTools(Tool... tools) {
        for(Tool tool : tools) {
            tool.setMaximumSize(new Dimension(width, width));
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
