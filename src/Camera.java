public class Camera {
    private double viewPlaneWidth;
    
    private Vec3 lookFrom;
    private Vec3 lookAt;
    private Vec3 vup;
    private double vfov;
    private double aspectRatio;
    
    private Vec3 upperLeft;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 origin;

    Camera(Vec3 lookFrom, Vec3 lookAt, Vec3 vup, double vfov, double aspectRatio) {
        setCamera(lookFrom, lookAt, vup, vfov, aspectRatio);
    }
    
    Ray getRay(double u, double v) {
        return new Ray(origin, Vec3.subtract(Vec3.add(upperLeft, Vec3.multiply(horizontal, u)), Vec3.add(Vec3.multiply(vertical, v), origin)));
    }
    
    void setCamera(Vec3 lookFrom, Vec3 lookAt, Vec3 vup, double vfov, double aspectRatio) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.vup = vup;
        this.vfov = vfov;
        this. aspectRatio = aspectRatio;
    
        calcVectors();
    }
    
    void setVFOV(double vfov) {
        this.vfov = vfov;
        calcVectors();
    }
    
    void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
        calcVectors();
    }
    
    private void calcVectors() {
        Vec3 u, v, w;
        double theta = vfov*Math.PI/180;
        double height = 2*Math.tan(theta/2);
        double width = aspectRatio * height;
        w = Vec3.subtract(lookFrom, lookAt).unitVector();
        u = Vec3.cross(vup, w).unitVector();
        v = Vec3.cross(w, u);
        origin = lookFrom;
        upperLeft = Vec3.subtract(Vec3.add(origin, Vec3.multiply(v, height/2.0)), Vec3.add(Vec3.multiply(u, width/2.0), w));
        horizontal = Vec3.multiply(Vec3.multiply(u, width/2.0), 2);
        vertical = Vec3.multiply(Vec3.multiply(v, height/2.0), 2);
    }
}
