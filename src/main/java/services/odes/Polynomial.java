package services.odes;

import interfaces.IOrdinaryDifferentialEquation;

import java.util.Vector;

public class Polynomial implements IOrdinaryDifferentialEquation {
    private final Vector<Double> coefficientsU;
    private final Vector<Double> coefficientsT;

    public Polynomial(Vector<Double> coefficientsU, Vector<Double> coefficientsT) {
        this.coefficientsU = coefficientsU == null ? new Vector<>() : coefficientsU;
        this.coefficientsT = coefficientsT == null ? new Vector<>() : coefficientsT;
    }

    @Override
    public Double apply(Double t, Double u) {
        double result = 0.0;
        for (int i = 0; i < this.coefficientsU.size(); i++) {
            result += this.coefficientsU.get(i) * Math.pow(u, i);
        }
        for (int i = 0; i < this.coefficientsT.size(); i++) {
            result += this.coefficientsT.get(i) * Math.pow(t, i);
        }
        return result;
    }
}
