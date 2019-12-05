import java.awt.*;

public class Utils {

    static Color lerp(Color c1, Color c2, double fac) {
        float r1 = c1.getRed()/255.0f;
        float g1 = c1.getGreen()/255.0f;
        float b1 = c1.getBlue()/255.0f;
        float a1 = c1.getAlpha()/255.0f;
        float r2 = c2.getRed()/255.0f;
        float g2 = c2.getGreen()/255.0f;
        float b2 = c2.getBlue()/255.0f;
        float a2 = c2.getAlpha()/255.0f;

        return new Color((float)(r1*(1.0-fac) + r2*fac), (float)(g1*(1.0-fac) + g2*fac), (float)(b1*(1.0-fac) + b2*fac), (float)(a1*(1.0-fac) + a2*fac));
    }

    static float colorIntToFloat(int color) { return color/255.0f; }

    static Color multiply(Color c1, Color c2) {
        float r = colorIntToFloat(c2.getRed()) * colorIntToFloat(c1.getRed());
        float g = colorIntToFloat(c2.getBlue()) * colorIntToFloat(c1.getBlue());
        float b = colorIntToFloat(c2.getGreen()) * colorIntToFloat(c1.getGreen());
        return new Color(r, g, b);
    }

    static Color multiply(Color color, double factor) {
        int r = (int) (factor * color.getRed());
        int g = (int) (factor * color.getGreen());
        int b = (int) (factor * color.getBlue());
        return new Color(r, g, b);
    }

    static Color correctGamma(Color color, double gamma) {
        float r = (float) (Math.pow(color.getRed()/255.0, 1/gamma));
        float g = (float) (Math.pow(color.getGreen()/255.0, 1/gamma));
        float b = (float) (Math.pow(color.getBlue()/255.0, 1/gamma));
        return new Color(r, g, b);
    }

    static Vec3 randomInUnitSphere() {
        Vec3 p;
        do {
            p = Vec3.subtract(Vec3.multiply(new Vec3(Math.random(), Math.random(), Math.random()), 2.0), new Vec3(1, 1, 1));
        } while (p.length() * p.length() >= 1.0);
        return p;
    }
}