package henaxel.node;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class BaseNode extends JPanel {
    protected String name;


    protected BaseNode(String name, LayoutManager layoutManager) {
        super(layoutManager);
        this.name = name;
    }
    
    @Override
    public String getName() { return name; }

    // LISTENER ----------------
    protected class NodeDragListener extends MouseInputAdapter {
        private BaseNode node;
        private int dragX;
        private int dragY;

        public NodeDragListener(BaseNode node) {
            this.node = node;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            dragX = e.getPoint().x;
            dragY = e.getPoint().y;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                node.setBounds(node.getLocation().x + e.getX() - dragX, node.getLocation().y + e.getY() - dragY, node.getPreferredSize().width, node.getPreferredSize().height);
            }
        }
    }
}
