public class Camera {
    private Vec3 upperLeft;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 origin;

    Camera(int imageWidth, int imageHeight, double viewPlaneWidth) {
        double viewPlaneHeight = viewPlaneWidth*((double) imageHeight/imageWidth);
        upperLeft = new Vec3(-viewPlaneWidth/2.0, viewPlaneHeight/2.0, -1.0);
        horizontal = new Vec3(viewPlaneWidth, 0.0, 0.0);
        vertical = new Vec3(0.0, -viewPlaneHeight, 0.0);
        origin = new Vec3(0, 0, 0);
    }

    Ray getRay(double u, double v) {
        return new Ray(origin, Vec3.add(upperLeft, Vec3.multiply(horizontal, u), Vec3.multiply(vertical, v)));
    }

    Vec3 getUpperLeft() { return upperLeft; }
    Vec3 getHorizontal() { return horizontal; }
    Vec3 getVertical() { return vertical; }
    Vec3 getOrigin() { return origin; }
}