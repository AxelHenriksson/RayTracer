package henaxel.raytracer;

import henaxel.utils.Vec3;

public class AABB {
    private Vec3 min;
    private Vec3 max;
    
    public AABB(Vec3 a, Vec3 b) {
        min = a;
        max = b;
    }
    
    Vec3 min() {return min;}
    Vec3 max() {return max;}
    
    boolean hit(Ray r, double tmin, double tmax) {
        for(int a = 0; a < 3; a++) {
            double t0 = Math.min((min.get(a) - r.origin().get(a)) / r.direction().get(a),
            (max.get(a) - r.origin().get(a)) / r.direction().get(a));
            double t1 = Math.max((min.get(a)-r.origin().get(a)) / r.direction().get(a),
                    (max.get(a) - r.origin().get(a)) / r.direction().get(a));
            tmin = Math.max(t0, tmin);
            tmax = Math.min(t1, tmax);
            if(tmax <= tmin) {
                return false;
            }
        }
        return true;
    }
    
    static AABB surroundingBox(AABB box0, AABB box1) {
        Vec3 small = new Vec3(
                Math.min(box0.min.x, box1.min.x),
                Math.min(box0.min.y, box1.min.y),
                Math.min(box0.min.z, box1.min.z));
        Vec3 big = new Vec3(
                Math.max(box0.max.x, box1.max.x),
                Math.max(box0.max.y, box1.max.y),
                Math.max(box0.max.z, box1.max.z));
        return new AABB(small, big);
    }
    
}
