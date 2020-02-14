package henaxel.raytracer.materials;

import henaxel.node.Nodable;
import henaxel.node.OutLink;
import henaxel.raytracer.node.Node;
import henaxel.raytracer.utils.Vec3;

import java.awt.*;

public abstract class Texture extends Node {

    public Texture(String name, String[] inputNames, Object[] inputs, String[] outputNames, OutLink[] outLinks) {
        super(name, Color.orange, inputNames, inputs, outputNames, outLinks);
    }
    
    abstract Color color(double u, double v);
}
