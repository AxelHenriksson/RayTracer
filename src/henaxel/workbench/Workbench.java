package henaxel.workbench;

import javax.swing.*;
import java.awt.*;

public class Workbench extends JFrame {
    JComponent workSpace;

    public Workbench() {
        getContentPane().setLayout(new BorderLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.9)));
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    public Workbench(JComponent workSpace) {
        this.workSpace = workSpace;
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(workSpace, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.9)));
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void add(Toolbar toolbar, String anchor) {
        toolbar.setAnchor(anchor);
        getContentPane().add(toolbar, anchor);
        revalidate();
    }

    public void add(TabbedBar tabbedBar, String anchor) {
        getContentPane().add(tabbedBar, anchor);
        revalidate();
    }

    protected void setWorkSpace(JComponent workSpace) { this.workSpace = workSpace; }
    protected JComponent getWorkSpace() { return workSpace; }
}
