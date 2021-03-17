package henaxel.raytracer.node;

import henaxel.node.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Node extends BaseNode {
    private InLink[] inlinks;
    private OutLink[] outlinks;
    
    protected Node(String name, Color color, InLink[] inlinks, OutLink[] outlinks) {
        super(name, new BorderLayout());

        this.inlinks = inlinks;
        this.outlinks = outlinks;
        
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new LineBorder(Color.black, 2, true));

        //NameField
        JLabel nameField = new JLabel(name, SwingConstants.CENTER);
        nameField.setBackground(color);
        nameField.setForeground(Color.black);
        nameField.setBorder(new EmptyBorder(2, 4, 2, 4));
        nameField.setOpaque(true);
        NodeDragListener dragListener = new NodeDragListener(this);
        nameField.addMouseMotionListener(dragListener);
        nameField.addMouseListener(dragListener);
        add(nameField, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new GridLayout(1, 2));

        if(inlinks != null) {
            JPanel inputPanel = new JPanel(new GridLayout(inlinks.length, 1));
            for (InLink inlink : inlinks) {
                inputPanel.add(inlink);
            }
            bodyPanel.add(inputPanel);
        }

        if(outlinks != null) {
            JPanel outputPanel = new JPanel(new GridLayout(outlinks.length, 1));
            for (OutLink outlink : outlinks) {
                outputPanel.add(outlink);
            }
            bodyPanel.add(outputPanel);
        }

        add(bodyPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(Math.max(getPreferredSize().width, 100), getPreferredSize().height));

    }

    public String getName() { return name; }


}
