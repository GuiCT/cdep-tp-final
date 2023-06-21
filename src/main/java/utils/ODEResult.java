package utils;

import java.io.Serializable;
import java.util.Vector;

/**
 * <p>
 * Record para armazenar os resultados de uma EDO.
 * </p>
 */
public record ODEResult(Vector<Double> t, Vector<Double> u) implements Serializable {
}
