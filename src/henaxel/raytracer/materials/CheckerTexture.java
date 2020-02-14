package henaxel.raytracer.materials;

import henaxel.node.BaseNode;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class CheckerTexture extends Texture {
    private Texture odd;
    private Texture even;
    
    public CheckerTexture(Texture t0, Texture t1) {
        super("Checker Texture", null, null, null, null);
        odd = t0;
        even = t1;
    }
    
    @Override
    public Color color(double u, double v) {
        if (Math.round(u)+Math.round(v)-2*(Math.round(u)*Math.round(v)) < 0.5) {
            return odd.color(u, v);
        } else {
            return even.color(u, v);
        }
    }
    /*
    public Color color(double u, double v, Vec3 hitPos) {
        double sines = Math.sin(10*hitPos.x)*Math.sin(10*hitPos.y)*Math.sin(10*hitPos.z);
        if (sines < 0) {
            return odd.color(u, v, hitPos);
        } else {
            return even.color(u, v, hitPos);
        }
    }
     */
}
