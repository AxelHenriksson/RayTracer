package henaxel.workbench

import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

class Toolbar(name: String, width: Int, vararg tools: Tool) : JPanel() {


    init {
        setName(name)
        addTools(tools)
    }

    fun addTools(tools: Array<out Tool>) {
        for(tool in tools) add(tool)
    }

    fun setAnchor(anchor: String) {
        when(anchor) {
            BorderLayout.NORTH  -> layout = BoxLayout(this, BoxLayout.LINE_AXIS)
            BorderLayout.SOUTH  -> layout = BoxLayout(this, BoxLayout.LINE_AXIS)
            BorderLayout.EAST   -> layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
            BorderLayout.WEST   -> layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        }
    }
}