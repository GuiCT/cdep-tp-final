package interfaces;

import java.io.Serializable;

public interface IOrdinaryDifferentialEquation extends Serializable {
    Double apply(Double t, Double u);
}
