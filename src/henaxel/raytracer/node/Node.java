package henaxel.raytracer.node;

import henaxel.node.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Node extends BaseNode {

    protected Node(String name, Color color, String[] inputNames, Object[] inputs, String[] outputNames, OutLink[] outLinks) {
        super(name, new BorderLayout());

        setBackground(new Color(0, 0, 0, 0));
        setBorder(new LineBorder(Color.black, 2, true));

        //NameField
        JLabel nameField = new JLabel(name);
        nameField.setBackground(color);
        nameField.setForeground(Color.black);
        nameField.setBorder(new EmptyBorder(2, 4, 2, 4));
        nameField.setOpaque(true);
        NodeDragListener dragListener = new NodeDragListener(this);
        nameField.addMouseMotionListener(dragListener);
        nameField.addMouseListener(dragListener);
        add(nameField, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new GridLayout(1, 2));

        if(inputs != null) {
            JPanel inputPanel = new JPanel(new GridLayout(inputs.length, 1));
            for (int i = 0; i < inputs.length; i++) {
                inputPanel.add(new NInputPanel(inputNames[i], inputs[i]));
            }
            bodyPanel.add(inputPanel);
        }

        if(outLinks != null) {
            JPanel outputPanel = new JPanel(new GridLayout(outLinks.length, 1));
            for (OutLink link : outLinks) {
                outputPanel.add(link);
            }
            bodyPanel.add(outputPanel);
        }

        add(bodyPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(Math.max(getPreferredSize().width, 100), getPreferredSize().height));

    }

    public String getName() { return name; }


}
