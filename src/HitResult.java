import java.awt.*;

public class HitResult {
    Vec3 pos;
    Vec3 n;
    double t;
    Ray scattered;
    Color attenuation;

    /*
    HitResult(Vec3 pos, Vec3 normal, double t) {
        this.pos = pos;
        this.n = normal;
        this.t = t;
    }
    */

    HitResult(Vec3 pos, Vec3 normal, double t, Ray scattered, Color attenuation) {
        this.pos = pos;
        this.n = normal;
        this.t = t;
        this.scattered = scattered;
        this.attenuation = attenuation;
    }
}
