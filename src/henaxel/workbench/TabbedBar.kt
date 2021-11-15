package henaxel.workbench

import javax.swing.JTabbedPane

class TabbedBar(vararg toolbars: Toolbar) : JTabbedPane() {

    init {
        for(toolbar in toolbars) addTab(toolbar.name, toolbar)
    }
}