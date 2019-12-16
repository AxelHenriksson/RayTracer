package henaxel.workbench;

import javax.swing.*;
import java.awt.*;


public class Toolbar extends JPanel {
    private int width;

    public Toolbar(int width, Tool... tools) {
        this.width = width;

        for(Tool tool : tools) {
            addTool(tool);
        }
    }

    public void addTool(Tool tool) {
        tool.setWidth(width);
        add(tool);
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
    public void setWidth(int width) { this.width = width; }

}
