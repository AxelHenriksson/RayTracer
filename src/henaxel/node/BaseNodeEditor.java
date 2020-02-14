package henaxel.node;

import henaxel.workbench.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import static java.awt.event.MouseEvent.BUTTON1;

public class BaseNodeEditor extends Workbench {
    private ArrayList<BaseNode> nodeList;
    private Class<BaseNode>[] nodableList;

    public BaseNodeEditor() {
        super(new JPanel(null));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getWorkSpace().setBackground(new Color(57, 62, 65));
        nodeList = new ArrayList<>();

        EditorMouseListener editorMouseListener = new EditorMouseListener(this);
        getWorkSpace().addMouseMotionListener(editorMouseListener);
        getWorkSpace().addMouseListener(editorMouseListener);
        getWorkSpace().addMouseWheelListener(editorMouseListener);
    }

    public void addNode(BaseNode node, int x, int y) {
        nodeList.add(node);
        super.getWorkSpace().add(node);
        node.setBounds(x, y, node.getPreferredSize().width, node.getPreferredSize().height);
        revalidate();
    }
    public void addNode(BaseNode node) {
        addNode(node, this.getWidth()/2, this.getHeight()/2);
    }

    public BaseNode[] getNodeList() { return nodeList.toArray(new BaseNode[0]); }
    public void setNodeClassList(Class<BaseNode>... nodables) {
        this.nodableList = nodables;
    }
    private Class<BaseNode>[] getNodeClassList() { return nodableList; }

    private void scaleWorkspace(MouseWheelEvent e) {
        int x = e.getX();
        int y = e.getY();
        int s = e.getUnitsToScroll();
        int scale = -10;

        for(BaseNode node : nodeList) {
            node.setBounds(node.getX() + (((node.getX()+(node.getPreferredSize().width/2))-x)*s)/scale, node.getY() + (((node.getY()+(node.getPreferredSize().height/2))-y)*s)/scale, node.getPreferredSize().width, node.getPreferredSize().height);
        }
    }

    private void fitToScreen() {
        BaseNode node0 = nodeList.get(0);
        int minX = node0.getX();
        int minY = node0.getY();
        int maxX = node0.getX() + node0.getPreferredSize().width;
        int maxY = node0.getY() + node0.getPreferredSize().height;

        for(BaseNode node : nodeList) {
            minX = Math.min(minX, node.getX());
            minY = Math.min(minY, node.getY());
            maxX = Math.max(maxX, node.getX()+node.getPreferredSize().width);
            maxY = Math.max(maxY, node.getY()+node.getPreferredSize().height);
        }
        for(BaseNode node : nodeList) {
            int x = ((getWorkSpace().getSize().width*(node.getX() - minX))/(maxX-minX));
            int y = ((getWorkSpace().getSize().height*(node.getY() - minY))/(maxY-minY));
            node.setBounds(x, y, node.getPreferredSize().width, node.getPreferredSize().height);

        }
    }

    // POPUP MENU -----------
    private static class EditorPopup extends JPopupMenu {
        Class<BaseNode>[] nodeClassList;

        public EditorPopup(BaseNodeEditor editor) {
            nodeClassList = editor.getNodeClassList();

            JMenu addNodeMenu = new JMenu("Add Node");
            if(nodeClassList != null && nodeClassList.length > 0) {
                for (Class<BaseNode> nodeClass : nodeClassList) {
                    JMenuItem item = new JMenuItem(nodeClass.getName());
                    item.addActionListener(new PlaceNodeAction(editor, nodeClass));
                    addNodeMenu.add(item);
                }
            }
            add(addNodeMenu);
        }
    }

    public static class PlaceNodeAction extends AbstractAction {
        private BaseNodeEditor editor;
        private Class<BaseNode> nodeClass;

        PlaceNodeAction(BaseNodeEditor editor, Class<BaseNode> nodeClass) {
            this.editor = editor;
            this.nodeClass = nodeClass;
        }

        public void actionPerformed(ActionEvent e) {
                new EditorPlacementListener(editor, nodeClass);
        }
    }

    // LISTENERS ------------
    private class EditorMouseListener extends MouseInputAdapter {
        private Point dragOrigin;
        private BaseNodeEditor editor;

        public EditorMouseListener(BaseNodeEditor editor) {
            this.editor = editor;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton() == BUTTON1) {
                dragOrigin = e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.isPopupTrigger()) {
                EditorPopup popup = new EditorPopup(editor);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                for(BaseNode node : nodeList) {
                    node.setBounds(node.getLocation().x + e.getX() - dragOrigin.x, node.getY() + e.getY() - dragOrigin.y, node.getPreferredSize().width, node.getPreferredSize().height);
                }
                dragOrigin.x = e.getX();
                dragOrigin.y = e.getY();
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            editor.scaleWorkspace(e);
        }
    }

    private static class EditorPlacementListener extends MouseInputAdapter {
        private BaseNodeEditor editor;
        private BaseNode node;

        public EditorPlacementListener(BaseNodeEditor editor, Class<BaseNode> nodeClass) {
            this.editor = editor;
            try {
                this.node = nodeClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
            editor.getWorkSpace().addMouseListener(this);
            editor.getWorkSpace().addMouseMotionListener(this);
            editor.getWorkSpace().add(node);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            node.setBounds(e.getX(), e.getY(), node.getPreferredSize().width, node.getPreferredSize().height);
            editor.revalidate();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            editor.getWorkSpace().remove(node);
            if(e.getButton() == BUTTON1) {
                editor.addNode(node, e.getX(), e.getY());
            }
            editor.revalidate();
            editor.getWorkSpace().removeMouseListener(this);
            editor.getWorkSpace().removeMouseMotionListener(this);
        }
    }


    // TOOLS ------------------------------

    public Toolbar toolbarBasicTools() {
        int size = 32;
        return new Toolbar("Basic Tools", size,
                toolFitToScreen(size)
        );
    }


    private Tool toolFitToScreen(int iconSize) {
            return Tool.buildButtonTool(
                    "Fit to screen",
                    "fitToScreen",
                    iconSize,
                    new AbstractAction() {
                        public void actionPerformed(ActionEvent e) {
                            fitToScreen();
                        }
                    }
            );
    }
}
