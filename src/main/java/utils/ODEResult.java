package utils;

import java.io.Serializable;
import java.util.Vector;

public class ODEResult implements Serializable {
    public final Vector<Double> t;
    public final Vector<Double> u;

    public ODEResult(Vector<Double> t, Vector<Double> u) {
        this.t = t;
        this.u = u;
    }
}
