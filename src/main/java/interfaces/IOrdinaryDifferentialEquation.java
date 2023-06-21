package interfaces;

import java.io.Serializable;

/**
 * <p>
 * Interface para definir uma equação diferencial ordinária.
 * </p>
 * <p>
 * Onde
 * </p>
 * <h3>ret = du/dt = f(t, u)</h3>
 */
public interface IOrdinaryDifferentialEquation extends Serializable {
    Double apply(Double t, Double u);
}
