package services.odes;

import interfaces.IOrdinaryDifferentialEquation;

/**
 * <p>
 * Classe que representa uma equação diferencial ordinária de uma curva
 * logística.
 * </p>
 * <p>
 * A EDO de uma curva logística é dada por:
 * </p>
 * <h3>r * u * (1 - u / k)</h3>
 */
public class LogisticCurve implements IOrdinaryDifferentialEquation {
    // R determina a taxa de crescimento/decrescimento
    private final Double r;
    // K determina capacidade máxima de crescimento
    private final Double k;

    public LogisticCurve(Double r, Double k) {
        this.r = r;
        this.k = k;
    }

    @Override
    public Double apply(Double t, Double u) {
        return r * u * (1 - u / k);
    }
}
