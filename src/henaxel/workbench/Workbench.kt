package henaxel.workbench

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.JComponent
import javax.swing.JFrame

class Workbench(var workSpace: JComponent) : JFrame() {

    init{
        contentPane.layout = BorderLayout()
        contentPane.add(workSpace, BorderLayout.CENTER)

        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true
        size = Dimension(
            (Toolkit.getDefaultToolkit().screenSize.width * 0.9).toInt(),
            (Toolkit.getDefaultToolkit().screenSize.height * 0.9).toInt()
        )
        extendedState = MAXIMIZED_BOTH
        isVisible = true
    }

    fun add(toolbar: Toolbar, anchor: String) {
        toolbar.setAnchor(anchor)
        contentPane.add(toolbar, anchor)
        revalidate()
    }
}