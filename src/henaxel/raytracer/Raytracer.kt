package henaxel.raytracer

import henaxel.raytracer.utils.correctGamma
import henaxel.raytracer.utils.lerp
import henaxel.raytracer.utils.times
import henaxel.workbench.Tool
import henaxel.workbench.Toolbar
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.AbstractAction
import javax.swing.JComponent
import javax.swing.JOptionPane
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.properties.ObservableProperty
import kotlin.random.Random


class Raytracer(var environment: Environment) : JComponent() {
    private var image: BufferedImage? = null
    private val imageWidth = Defaults.IMAGE_WIDTH
    private val imageHeight = Defaults.IMAGE_HEIGHT
    private val tMin = Defaults.CLIP_MIN
    private val tMax = Defaults.CLIP_MAX
    private val samples = Defaults.SAMPLES
    private val depth = Defaults.DEPTH
    private val gamma = Defaults.GAMMA
    private val transparentBackground = Defaults.TRANSPARENT
    private var renderThreads = ArrayList<RenderThread>()
    private val threadCount = Defaults.RTHREAD_COUNT
    private val cellCount = Defaults.RCELL_COUNT
    private var nextCellIndex = 0

    enum class RenderMode { SHADED, NORMALS }

    private data class RenderCellBound(val startX: Int, val endX: Int, val startY: Int, val endY: Int)
    private open class RenderThread(val raytracer: Raytracer, val renderMode: RenderMode, val renderCellBound: RenderCellBound) : Thread() {
        protected fun onThreadFinish() {
            raytracer.getNewRenderCellBound()?.let {
                with(raytracer.buildThread(renderMode, it)) {
                    raytracer.renderThreads.add(this)
                    this.start()
                }
            }
            raytracer.renderThreads.remove(this)
        }
    }

    fun render(renderMode: RenderMode) {
        //Cancel any ongoing rendering by interrupting any running renderThreads
        for (thread in renderThreads) thread.interrupt()
        renderThreads.clear()

        // Initialize camera with new aspect ratio and image with new resolution
        environment.activeCam.calcVec(imageWidth, imageHeight)
        image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        nextCellIndex = 0

        for(i in 0 until threadCount) {
            getNewRenderCellBound()?.let {
                with(buildThread(renderMode, it)) {
                    renderThreads.add(this)
                    this.start()
                }
            }
        }
    }

    //Build a renderThread capable of rendering a renderThread cell
    private fun buildThread(renderMode: RenderMode, renderCellBound: RenderCellBound): RenderThread {
        return when(renderMode) {
            RenderMode.NORMALS -> {
                object : RenderThread(this, RenderMode.NORMALS, renderCellBound) {
                    override fun run() {
                        for (col in renderCellBound.startX until renderCellBound.endX) {
                            for (row in renderCellBound.startY until renderCellBound.endY) {
                                var red = 0f
                                var green = 0f
                                var blue = 0f
                                var alpha = 0f
                                for (s in 0 until samples) {
                                    val u = (col.toDouble() + Math.random()) / imageWidth
                                    val v = (row.toDouble() + Math.random()) / imageHeight
                                    val r = environment.activeCam!!.getRay(u, v)
                                    val sColor = colorNormal(r)
                                    red += (sColor.red / 255.0).toFloat()
                                    green += (sColor.green / 255.0).toFloat()
                                    blue += (sColor.blue / 255.0).toFloat()
                                    alpha += (sColor.alpha / 255.0).toFloat()
                                }
                                val color = Color(red / samples, green / samples, blue / samples, alpha / samples)
                                image!!.setRGB(col, imageHeight-1-row, color.rgb)
                            }
                        }
                        repaint()
                        onThreadFinish()
                    }
                }
            }
            RenderMode.SHADED -> {
                object : RenderThread(this, RenderMode.SHADED, renderCellBound) {
                    override fun run() {
                        for (col in renderCellBound.startX until renderCellBound.endX) {
                            for (row in renderCellBound.startY until renderCellBound.endY) {
                                //if (currentThread().isInterrupted) return
                                var red = 0f
                                var green = 0f
                                var blue = 0f
                                var alpha = 0f
                                for (s in 0 until samples) {
                                    val u = (col.toDouble() + Random.nextDouble()) / imageWidth
                                    val v = (row.toDouble() + Random.nextDouble()) / imageHeight
                                    val ray = environment.activeCam.getRay(u, v)
                                    val sColor = colorShaded(ray, 0)
                                    red += (sColor.red / 255.0).toFloat()
                                    green += (sColor.green / 255.0).toFloat()
                                    blue += (sColor.blue / 255.0).toFloat()
                                    alpha += (sColor.alpha / 255.0).toFloat()
                                }
                                val color = Color(red / samples, green / samples, blue / samples, alpha / samples)
                                image!!.setRGB(col, imageHeight-1-row, color.correctGamma(gamma).rgb)
                            }
                        }
                        repaint()
                        onThreadFinish()
                    }
                }
            }
        }
    }

    private fun colorNormal(ray: Ray): Color {
        val hr = environment.hit(ray, tMin, tMax)
        return if (hr != null) {
            Color(
                ((hr.n.x + 1) * 0.5).toFloat(),
                ((hr.n.y + 1) * 0.5).toFloat(), ((hr.n.z + 1) * 0.5).toFloat(), 1.0f
            )
        } else getBackground(ray)
    }
    private fun colorShaded(ray: Ray, depth: Int): Color {
        val hr = environment.hit(ray, tMin, tMax) ?: return getBackground(ray)
        if(depth >= this.depth || hr.scattered == null)  return hr.attenuation
        return colorShaded(hr.scattered, depth+1) * hr.attenuation
    }

    private fun getBackground(ray: Ray): Color {
        val unitDir = ray.dir.norm()
        val t = 0.5f * (unitDir.y + 1.0).toFloat()
        return if (!transparentBackground)
            Color(1.0f, 1.0f, 1.0f, 1.0f).lerp(Color(0.5f, 0.7f, 1.0f, 1.0f), t)
        else
            Color(1.0f, 1.0f, 1.0f, 0.0f).lerp(Color(0.5f, 0.7f, 1.0f, 0.0f), t)
    }

    // TODO: There may be errors with how cell bounds are handled, especially at end edges of render
    private fun getNewRenderCellBound():  RenderCellBound? {
        val horizontalCount = floor(sqrt(cellCount.toDouble())).toInt()
        val verticalCount = (cellCount / horizontalCount)
        val width = imageWidth / horizontalCount
        val height = imageHeight / verticalCount
        val horizontalIndex = nextCellIndex % horizontalCount
        val verticalIndex = nextCellIndex / horizontalCount
        val cellBounds = RenderCellBound(
            horizontalIndex * width,
            ((horizontalIndex + 1) * width).coerceAtMost(imageWidth),
            verticalIndex * height,
            ((verticalIndex + 1) * height).coerceAtMost(imageHeight)
        )
        return if (cellBounds.startX < imageWidth && cellBounds.startY < imageHeight) {
            nextCellIndex++
            cellBounds
        } else {
            null
        }
    }

    override fun paintComponent(g: Graphics) {
        val height = minOf(
            height,
            (imageHeight.toDouble() / imageWidth * width).toInt())
        val width = minOf(width,
            (imageWidth.toDouble() / imageHeight * this.height).toInt())

        g.drawImage(image, (this.width - width) / 2, (this.height - height) / 2, width, height, null)
    }


    fun saveImage(path: String) {
        val bufferedImage = when (path.substring(path.lastIndexOf(".") + 1).toLowerCase()) {
            "png" -> BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
            "jpg" -> BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
            else -> BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        }
        for (x in 0 until imageWidth) {
            for (y in 0 until imageHeight) {
                bufferedImage.setRGB(x, y, image!!.getRGB(x, y))
            }
        }
        val outputFile = File(path)

        ImageIO.write(bufferedImage, path.substring(path.lastIndexOf(".") + 1), outputFile)

        //JOptionPane.showMessageDialog(null, String.format("Saved as %s", path))
    }

    // TOOLS --
    fun actionBar(): Toolbar {
        val size = 32
        return Toolbar("Actions", size,
            traceNormalsTool(size),
            traceShadedTool(size),
            saveImageTool(size))
    }

    private fun traceNormalsTool(iconSize: Int): Tool {
        return Tool.buildButtonTool(
            "Trace Normals",
            "traceNormals",
            iconSize,
            object : AbstractAction() {
                override fun actionPerformed(e: ActionEvent) {
                    render(RenderMode.NORMALS)
                }
            }
        )
    }

    private fun traceShadedTool(iconSize: Int): Tool {
        return Tool.buildButtonTool(
            "Trace Shaded",
            "traceShaded",
            iconSize,
            object : AbstractAction() {
                override fun actionPerformed(e: ActionEvent) {
                    render(RenderMode.SHADED)
                }
            }
        )
    }

    fun saveImageTool(iconSize: Int): Tool {
        return Tool.buildButtonTool(
            "Save Image",
            "saveImage",
            iconSize,
            object : AbstractAction() {
                override fun actionPerformed(e: ActionEvent) {
                    saveImage("src/out/${JOptionPane.showInputDialog("Enter filename with extension.") ?: return}")
                }
            }
        )
    }
}