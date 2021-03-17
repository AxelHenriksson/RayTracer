package henaxel.materials;

import henaxel.raytracer.node.OutLink;
import henaxel.raytracer.node.Node;

import java.awt.*;

public abstract class Texture extends Node {

    public Texture(String name, String[] inputNames, Object[] inputs, String[] outputNames, OutLink[] outLinks) {
        super(name, Color.orange,
                null, null);
    }
    
    abstract Color color(double u, double v);
}
