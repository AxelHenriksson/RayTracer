public class HitResult {
    Vec3 pos;
    Vec3 n;
    double t;
    Ray scattered;
    Vec3 attenuation;
    boolean scatter;

    HitResult() {}
    HitResult(Vec3 pos, Vec3 normal, double t) {
        this.pos = pos;
        this.n = normal;
        this.t = t;
    }
    HitResult(Ray scattered, Vec3 attenuation, boolean scatter) {
        this.scattered = scattered;
        this.attenuation = attenuation;
        this.scatter = scatter;
    }
}
