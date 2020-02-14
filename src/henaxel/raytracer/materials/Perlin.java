package henaxel.raytracer.materials;

import henaxel.raytracer.utils.Utils;
import henaxel.raytracer.utils.Vec3;

public class Perlin {
    private Vec3[] ranVec = perlinGenerate();
    private int[] permX = perlinGeneratePerm();
    private int[] permY = perlinGeneratePerm();
    private int[] permZ = perlinGeneratePerm();
    
    
    public double noise(Vec3 pos) {
        double u = pos.x - Math.floor(pos.x);
        double v = pos.y - Math.floor(pos.y);
        double w = pos.z - Math.floor(pos.z);
        int i = (int)Math.floor(pos.x);
        int j = (int)Math.floor(pos.y);
        int k = (int)Math.floor(pos.z);
        Vec3[][][] c = new Vec3[2][2][2];
        for(int di = 0; di < 2; di++) {
            for (int dj = 0; dj < 2; dj++) {
                for (int dk = 0; dk < 2; dk++) {
                    c[di][dj][dk] = ranVec[permX[(i + di) & 255] ^ permY[(j + dj) & 255] ^ permZ[(k + dk) & 255]];
                }
            }
        }
        return perlinInterp(c, u, v, w);
    }
    public double noise(double u, double v) {
        int i = (int)Math.floor(u);
        int j = (int)Math.floor(v);
        int k = 0;
        Vec3[][][] c = new Vec3[2][2][2];
        for(int di = 0; di < 2; di++) {
            for (int dj = 0; dj < 2; dj++) {
                for (int dk = 0; dk < 2; dk++) {
                    c[di][dj][dk] = ranVec[permX[(i + di) & 255] ^ permY[(j + dj) & 255] ^ permZ[(k + dk) & 255]];
                }
            }
        }
        return perlinInterp(c, u, v, 0);
    }
    
    private static Vec3[] perlinGenerate() {
        Vec3[] p = new Vec3[256];
        for(int i = 0; i < 256; i++) {
            p[i] = new Vec3(-1 + 2*Math.random(), -1 + 2*Math.random(), -1 + 2*Math.random()).unitVector();
        }
        return p;
    }
    
    private static void permute(int[] p) {
        for(int i = p.length-1; i > 0; i--) {
            int target = (int)(Math.random()*(i+1));
            int tmp = p[i];
            p[i] = p[target];
            p[target] = tmp;
        }
    }
    
    private static int[] perlinGeneratePerm() {
        int[] p = new int[256];
        for(int i = 0; i < 256; i++) {
            p[i] = i;
        }
        permute(p);
        return p;
    }
    
    private static double perlinInterp(Vec3[][][] c, double u, double v, double w) {
        double uu = u*u*(3-2*u);
        double vv = v*v*(3-2*v);
        double ww = w*w*(3-2*w);
        double accum = 0;
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                for(int k = 0; k < 2; k++) {
                    Vec3 weightV = new Vec3(u-i, v-j, w-k);
                    accum +=
                            (i*uu + (1-i)*(1-uu))*
                            (j*vv + (1-j)*(1-vv))*
                            (k*ww + (1-k)*(1-ww))*Vec3.dot(c[i][j][k], weightV);
                }
        return accum;
    }
    
    double turb(Vec3 p, int depth) {
        double accum = 0;
        Vec3 tmp = p;
        double weight = 1;
        for (int i = 0; i < depth; i++) {
            accum+= weight*noise(tmp);
            weight *= 0.5;
            tmp = Vec3.multiply(tmp, 2);
        }
        return Math.abs(accum);
    }
    double turb(double u, double v, int depth) {
        double accum = 0;
        double i = u;
        double j = v;
        double weight = 1;
        for (int k = 0; k < depth; k++) {
            accum+= weight*noise(i, j);
            weight *= 0.5;
            i *= 2;
            j *= 2;
        }
        return Math.abs(accum);
    }
}
