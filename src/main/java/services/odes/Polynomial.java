package services.odes;

import interfaces.IOrdinaryDifferentialEquation;

import java.util.Vector;

/**
 * <p>
 * Classe que representa uma EDO no formato polinomial.
 * </p>
 * <p>
 * Formato de uma EDO polinomial:
 * </p>
 * <h3>u<sub>n</sub> + u<sub>n-1</sub> + ... + u<sub>1</sub> + u<sub>0</sub> +
 * t<sub>m</sub> + t<sub>m-1</sub> + ... + t<sub>1</sub> + t<sub>0</sub></h3>
 */
public class Polynomial implements IOrdinaryDifferentialEquation {
    // Coeficientes associados a u
    private final Vector<Double> coefficientsU;
    // Coeficientes associados a t
    private final Vector<Double> coefficientsT;

    public Polynomial(Vector<Double> coefficientsU, Vector<Double> coefficientsT) {
        // Em caso de valor null, inicializa com um vetor vazio
        this.coefficientsU = coefficientsU == null ? new Vector<>() : coefficientsU;
        this.coefficientsT = coefficientsT == null ? new Vector<>() : coefficientsT;
    }

    @Override
    public Double apply(Double t, Double u) {
        // Para cada coeficiente de t e u
        // Calcula o valor de u ou t elevado ao índice do coeficiente
        // Multiplica-o pelo coeficiente e soma ao resultado final
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
