package henaxel.workbench;

import javax.swing.*;
import java.awt.*;

public class Workbench extends JFrame {

    public Workbench(JComponent view) {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.9)));
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void addToolbar(String anchor, Toolbar toolbar) {
        toolbar.setAnchor(anchor);
        getContentPane().add(toolbar, anchor);
        revalidate();
    }
}
