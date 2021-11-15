package henaxel.workbench

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.LayoutManager
import javax.swing.*

class Tool() : JPanel() {

    constructor(layoutManager: LayoutManager) : this() {
        layout = layoutManager
    }

    companion object {

        fun buildButtonTool(label: String, name: String, iconSize: Int, action: AbstractAction): Tool {
            val button = JButton().apply {

                if (javaClass.getResource("res/${name}${iconSize}.png") != null) {
                    icon = ImageIcon(this.javaClass.getResource("res/${name}${iconSize}.png"))
                } else {
                    text = label
                    font = Font("Dialog", Font.PLAIN, 10)
                }

                background = Color.white
                border = BorderFactory.createLineBorder(Color.lightGray, 1)
                addActionListener(action)
            }

            return Tool().apply {
                layout = BorderLayout()
                add(button, BorderLayout.CENTER)
            }
        }

        fun buildToggleButtonTool(label: String, name: String, iconSize: Int, selected: Boolean, action: AbstractAction): Tool {
            val button = JToggleButton().apply {
                isSelected = selected
                if (javaClass.getResource("res/${name}${iconSize}.png") != null) {
                    icon = ImageIcon(this.javaClass.getResource("res/${name}${iconSize}.png"))
                } else {
                    text = label
                    font = Font("Dialog", Font.PLAIN, 10)
                }

                background = Color.white
                border = BorderFactory.createLineBorder(Color.lightGray, 1)
                addActionListener(action)
            }

            return Tool().apply {
                layout = BorderLayout()
                add(button, BorderLayout.CENTER)
            }
        }
    }
}