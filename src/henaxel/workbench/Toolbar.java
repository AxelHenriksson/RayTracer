package henaxel.workbench;

import javax.swing.*;
import java.awt.*;


public class Toolbar extends JPanel {

    public Toolbar(int width, Tool... tools) {
        setPreferredSize(new Dimension(width, width));

        for(Tool tool : tools) {
            addTool(tool);
        }
    }

    public void addTool(Tool tool) {
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

}
