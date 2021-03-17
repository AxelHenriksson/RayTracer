package henaxel.raytracer;

public class RenderThread extends Thread {
    int startX, startY, endX, endY, currentX, index;

    RenderThread(int startX, int startY, int endX, int endY, int index) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.currentX = startX;
        this.index = index;
    }
}
