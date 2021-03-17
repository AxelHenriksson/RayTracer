package henaxel.raytracer;

import java.util.ArrayList;

public class Environment {
    private ArrayList<Hitable> hitables;
    public Camera activeCam;
    BVHNode bvhNode;


    public Environment() {
        hitables = new ArrayList<>();
    }

    public void add(Hitable hitable) {
        hitables.add(hitable);
    }
    public void constructBVH() { bvhNode = new BVHNode(hitables.toArray(Hitable[]::new)); }

    public HitResult hit(Ray r, double tmin, double tmax) {
        if (bvhNode != null) {
            return bvhNode.hit(r, tmin, tmax);
        } else {
        HitResult hr = null;
        HitResult tempHR;
        double closest = tmax;
        for(Hitable hitable : hitables) {
            tempHR = hitable.hit(r, tmin, closest);
            if (tempHR != null) {
                closest = tempHR.t;
                hr = tempHR;
            }
        }
        return hr;
        }
    }
    
    AABB boundingBox(double t0, double t1) {
        if(hitables.size() < 1) return null;
        AABB box;
        AABB tmpBox = hitables.get(0).boundingBox();
        if(tmpBox == null) {
            return null;
        } else {
            box = tmpBox;
        }
        for (int i = 1; i < hitables.size(); i++) {
            tmpBox = hitables.get(0).boundingBox();
            if (tmpBox != null) {
                box = AABB.surroundingBox(box, tmpBox);
            } else {
                return null;
            }
        }
        return box;
    }
}
