package henaxel.raytracer;

import java.util.ArrayList;

public class Environment {
    private ArrayList<Surface> surfaces;
    public Camera activeCam;


    public Environment() {
        surfaces = new ArrayList<>();
    }

    public void add(Surface surface) {
        surfaces.add(surface);
    }

    public HitResult hit(Ray r, double t_min, double t_max) {
        HitResult hr = null;
        HitResult tempHR;
        double closest = t_max;
        for(Surface surface : surfaces) {
            tempHR = surface.hit(r, t_min, closest);
            if (tempHR != null) {
                closest = tempHR.t;
                hr = tempHR;
            }
        }
        return hr;
    }
}
