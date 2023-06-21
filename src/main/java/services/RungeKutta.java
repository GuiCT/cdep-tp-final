package services;

import interfaces.IOrdinaryDifferentialEquation;
import interfaces.ITask;
import utils.ODEResult;

import java.util.Vector;

public class RungeKutta implements ITask<ODEResult> {
    private final Double tInitial;
    private final Double tFinal;
    private final Double step;
    private final Double uInitial;
    private final IOrdinaryDifferentialEquation derivativeFunction;

    public RungeKutta(Double tInitial, Double tFinal, Double step, Double uInitial,
            IOrdinaryDifferentialEquation derivativeFunction) {
        this.tInitial = tInitial;
        this.tFinal = tFinal;
        this.step = step;
        this.uInitial = uInitial;
        this.derivativeFunction = derivativeFunction;
    }

    @Override
    public String getTaskName() {
        return "Runge-Kutta 4 para solução de EDOs";
    }

    @Override
    public ODEResult execute() {
        Vector<Double> result = new Vector<>();
        Vector<Double> tRange = new Vector<>();
        result.add(uInitial);
        tRange.add(tInitial);

        Double u = uInitial;
        for (Double t = tInitial; t < tFinal; t += step) {
            u = RungeKutta4(t, u);
            result.add(u);
            tRange.add(t);
        }

        return new ODEResult(tRange, result);
    }

    private Double RungeKutta4(Double t, Double u) {
        Double k1 = derivativeFunction.apply(t, u);
        Double k2 = derivativeFunction.apply(t + step / 2, u + step * k1 / 2);
        Double k3 = derivativeFunction.apply(t + step / 2, u + step * k2 / 2);
        Double k4 = derivativeFunction.apply(t + step, u + step * k3);
        return u + step * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
    }
}
