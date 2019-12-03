import java.util.ArrayList;

public class Environment {
    private ArrayList<Object> objects;


    public Environment() {
        objects = new ArrayList<>();
    }

    public void add(Object object) {
        objects.add(object);
    }

    public boolean doesHit(Ray r) {
        for(Object object : objects) {
            if(object.doesHit(r)) { return true; }
        }
        return false;
    }
}
