import java.util.ArrayList;

public class Environment {
    private ArrayList<Hitable> hitables;
    Camera activeCamera;


    public Environment() {
        hitables = new ArrayList<>();
    }

    public void add(Hitable hitable) {
        hitables.add(hitable);
    }

    public HitResult hit(Ray r, double t_min, double t_max) {
        HitResult hr = null;
        HitResult tempHR;
        double closest = t_max;
        for(Hitable hitable : hitables) {
            tempHR = hitable.hit(r, t_min, closest);
            if (tempHR != null) {
                closest = tempHR.t;
                hr = tempHR;
            }
        }
        return hr;
    }
}
