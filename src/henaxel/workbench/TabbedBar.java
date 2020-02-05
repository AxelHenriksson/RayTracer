package henaxel.workbench;

import javax.swing.*;
import java.awt.*;

public class TabbedBar extends JTabbedPane {
    Toolbar[] toolbars;

    public TabbedBar (Toolbar... toolbars) {
        this.toolbars = toolbars;
        for(Toolbar toolbar : toolbars) {
            addTab(toolbar.getName(), toolbar);
        }

    }

}
