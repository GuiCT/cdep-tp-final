package utils;

import java.io.Serializable;
import java.util.Vector;

public record ODEResult(Vector<Double> t, Vector<Double> u) implements Serializable {
}
