package henaxel.workbench;

import javax.swing.*;
import java.awt.*;

public class Workbench extends JFrame {

    public Workbench(JComponent view) {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void addToolbar(String anchor, Toolbar toolbar) {
        toolbar.setAnchor(anchor);
        getContentPane().add(toolbar, anchor);
        pack();
    }
}
