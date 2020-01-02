package henaxel.raytracer.materials;

import henaxel.utils.Vec3;

import java.awt.*;

public abstract class Texture {
    
    abstract Color color(double u, double v, Vec3 hitPos);
}
