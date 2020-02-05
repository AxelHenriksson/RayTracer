package henaxel.raytracer.materials;

import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public class CheckerTexture extends Texture {
    private Texture odd;
    private Texture even;
    
    public CheckerTexture(Texture t0, Texture t1) {
        odd = t0;
        even = t1;
    }
    
    @Override
    Color color(double u, double v, Vec3 hitPos) {
        double sines = Math.sin(10*hitPos.x)*Math.sin(10*hitPos.y)*Math.sin(10*hitPos.z);
        if (sines < 0) {
            return odd.color(u, v, hitPos);
        } else {
            return even.color(u, v, hitPos);
        }
    }
}
