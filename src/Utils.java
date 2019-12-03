import java.awt.*;

public class Utils {

    public static Color lerp(Color c1, Color c2, double fac) {
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
}