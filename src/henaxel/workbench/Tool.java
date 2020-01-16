package henaxel.workbench;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Tool extends JPanel {

    public Tool() {

    }
    public Tool(LayoutManager layoutMGR) {
        this.setLayout(layoutMGR);
    }

    
    public static Tool buildButtonTool(String label, String toolName, int iconSize, AbstractAction abstractAction) {
        JButton button = new JButton();
    
        if(button.getClass().getResource(String.format("res/%s%d.png", toolName, iconSize)) != null) {
            button.setIcon(new ImageIcon(button.getClass().getResource(String.format("res/saveImage%d.png", iconSize))));
        } else {
            button.setText(label);
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }
    
        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        button.addActionListener(abstractAction);
    
        Tool tool = new Tool();
        tool.setLayout(new BorderLayout());
        tool.add(button, BorderLayout.CENTER);
        return tool;
    }
    
    public static Tool buildToggleButtonTool(String label, String toolName, int iconSize, boolean selected, AbstractAction abstractAction) {
        JToggleButton button = new JToggleButton();
        button.setSelected(selected);
        if(button.getClass().getResource(String.format("res/%s%d.png", toolName, iconSize)) != null) {
            button.setIcon(new ImageIcon(button.getClass().getResource(String.format("res/saveImage%d.png", iconSize))));
        } else {
            button.setText(label);
            button.setFont(new Font("Dialog", Font.PLAIN, 10));
        }
        
        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        button.addActionListener(abstractAction);
        
        Tool tool = new Tool();
        tool.setLayout(new BorderLayout());
        tool.add(button, BorderLayout.CENTER);
        return tool;
    }
}
