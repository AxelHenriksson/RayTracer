package henaxel.workbench;

import javax.swing.*;

public class TabbedBar extends JTabbedPane {
    Toolbar[] toolbars;

    public TabbedBar (Toolbar... toolbars) {
        this.toolbars = toolbars;
        for(Toolbar toolbar : toolbars) {
            addTab(toolbar.getName(), toolbar);
        }

    }

}
