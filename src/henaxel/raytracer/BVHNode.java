package henaxel.raytracer;

import henaxel.utils.Vec3;

import java.util.Arrays;

public class BVHNode extends Hitable {
    Hitable left;
    Hitable right;
    AABB box;
    
    public BVHNode(Hitable[] l) {
        if (l.length > 1) {
            int axis = (int) (3 * Math.random());
            if (axis == 0) {
                l = boxSort(l, 0);
            } else if (axis == 1) {
                l = boxSort(l, 1);
            } else {
                l = boxSort(l, 2);
            }
        }
        
        if (l.length == 1) {
            left = l[0];
            right = l[0];
            box = left.boundingBox();
        } else if (l.length == 2) {
            left = l[0];
            right = l[1];
            box = new AABB(new Vec3(
                            Math.min(left.boundingBox().min().x, right.boundingBox().min().x),
                            Math.min(left.boundingBox().min().y, right.boundingBox().min().y),
                            Math.min(left.boundingBox().min().z, right.boundingBox().min().z)),
                    new Vec3(
                            Math.max(left.boundingBox().max().x, right.boundingBox().max().x),
                            Math.max(left.boundingBox().max().y, right.boundingBox().max().y),
                            Math.max(left.boundingBox().max().z, right.boundingBox().max().z)));
        } else {
            left = new BVHNode(Arrays.copyOfRange(l, 0, l.length/2));
            right = new BVHNode(Arrays.copyOfRange(l, l.length/2, l.length));
            box = new AABB(new Vec3(
                            Math.min(left.boundingBox().min().x, right.boundingBox().min().x),
                            Math.min(left.boundingBox().min().y, right.boundingBox().min().y),
                            Math.min(left.boundingBox().min().z, right.boundingBox().min().z)),
                    new Vec3(
                            Math.max(left.boundingBox().max().x, right.boundingBox().max().x),
                            Math.max(left.boundingBox().max().y, right.boundingBox().max().y),
                            Math.max(left.boundingBox().max().z, right.boundingBox().max().z)));
            
        }
    }
    
    static Hitable[] boxSort(Hitable[] l, int axis) {
        boolean done;
        do {
            done = true;
            for (int i = 0; i < l.length-1; i++) {
                AABB leftBox = l[i].boundingBox();
                AABB rightBox = l[i+1].boundingBox();
                if(leftBox == null || rightBox == null) { System.out.println("BVHNode ERROR: No bounding box in constructor!"); }
                if (leftBox.min().get(axis) > rightBox.min().get(axis)) {
                    Hitable tmp = l[i];
                    l[i] = l[i+1];
                    l[i+1] = tmp;
                    done = false;
                }
            }
        } while (!done);
        
        return l;
    }
    
    @Override
    public HitResult hit(Ray r, double tmin, double tmax) {
        if (box.hit(r, tmin, tmax)) {
            HitResult leftHR = left.hit(r, tmin, tmax);
            HitResult rightHR = right.hit(r, tmin, tmax);
            if (leftHR != null && rightHR != null) {
                if (leftHR.t < rightHR.t) {
                    return leftHR;
                } else {
                    return rightHR;
                }
            } else if (leftHR != null) {
                return leftHR;
            } else {
                return rightHR;
            }
        } else {
            return null;
        }
    }
    
    @Override
    protected AABB boundingBox() {
        return box;
    }
    
}
