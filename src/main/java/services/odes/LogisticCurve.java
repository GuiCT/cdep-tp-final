package services.odes;

import interfaces.IOrdinaryDifferentialEquation;

public class LogisticCurve implements IOrdinaryDifferentialEquation {
    private final Double r;
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
