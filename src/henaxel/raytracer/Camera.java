package henaxel.raytracer;

import henaxel.utils.Vec3;

public class Camera {
    private Vec3 lookFrom;
    private Vec3 lookAt;
    private Vec3 vup;
    private double vfov;
    private double aspectRatio;
    private double aperture;
    private double focusDist;
    
    private Vec3 upperLeft;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 origin;
    private Vec3 u, v, w;

    public Camera(Vec3 lookFrom, Vec3 lookAt, Vec3 vup, double vfov, double aspectRatio, double aperture, double focusDist) {
        setCamera(lookFrom, lookAt, vup, vfov, aspectRatio, aperture, focusDist);
    }
    
    public void setCamera(Vec3 lookFrom, Vec3 lookAt, Vec3 vup, double vfov, double aspectRatio, double aperture, double focusDist) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.vup = vup;
        this.vfov = vfov;
        this.aspectRatio = aspectRatio;
        this.aperture = aperture;
        this.focusDist = focusDist;
        calcVectors();
    }
    
    public Ray getRay(double s, double t) {
        Vec3 rd = Vec3.multiply(Vec3.randomInUnitDisk(), aperture/2);
        Vec3 offset = Vec3.add(Vec3.multiply(u, rd.x), Vec3.multiply(v, rd.y));
        return new Ray(Vec3.add(origin, offset), Vec3.subtract(Vec3.add(upperLeft, Vec3.multiply(horizontal, s)), Vec3.add(Vec3.multiply(vertical, t), origin, offset)));
    }
    
    public void setVFOV(double vfov) {
        this.vfov = vfov;
        calcVectors();
    }
    
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
        calcVectors();
    }
    
    private void calcVectors() {
        double theta = vfov*Math.PI/180;
        double height = 2*Math.tan(theta/2);
        double width = aspectRatio * height;
        w = Vec3.subtract(lookFrom, lookAt).unitVector();
        u = Vec3.cross(vup, w).unitVector();
        v = Vec3.cross(w, u);
        origin = lookFrom;
        upperLeft = Vec3.subtract(Vec3.add(origin, Vec3.multiply(v, focusDist*height/2.0)), Vec3.add(Vec3.multiply(u, focusDist*width/2.0), Vec3.multiply(w, focusDist)));
        horizontal = Vec3.multiply(Vec3.multiply(u, focusDist*width/2.0), 2);
        vertical = Vec3.multiply(Vec3.multiply(v, focusDist*height/2.0), 2);
    }
}
