package services;

import interfaces.IOrdinaryDifferentialEquation;
import interfaces.ITask;
import utils.ODEResult;

import java.util.Vector;

/**
 * <p>
 * Classe que executa o método de Runge-Kutta 4 para solução de EDOs.
 * </p>
 */
public class RungeKutta implements ITask<ODEResult> {
    // Intervalo de integração
    private final Double tInitial;
    private final Double tFinal;
    // Tamanho do passo de integração
    private final Double step;
    // Valor inicial de u
    private final Double uInitial;
    // Função que representa a EDO
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
        // Vetores que armazenam os valores de u e t
        Vector<Double> result = new Vector<>();
        Vector<Double> tRange = new Vector<>();
        // Incluindo valores iniciais de u e t
        result.add(uInitial);
        tRange.add(tInitial);

        // Para cada passo de integração, calcula o valor de u
        // E adiciona ao vetor de resultados
        // Também calcula o novo t
        Double u = uInitial;
        for (Double t = tInitial; t < tFinal; t += step) {
            u = RungeKutta4(t, u);
            result.add(u);
            tRange.add(t);
        }

        // Utiliza o Record ODEResult para retornar os resultados
        return new ODEResult(tRange, result);
    }

    /**
     * <p>
     * Executa o método de Runge-Kutta 4 para um passo de integração.
     * </p>
     * 
     * @param t Valor de t
     * @param u Valor de u
     * @return Valor de u no ponto t + step
     */
    private Double RungeKutta4(Double t, Double u) {
        // Calculando os coeficientes k1, k2, k3 e k4
        Double k1 = derivativeFunction.apply(t, u);
        Double k2 = derivativeFunction.apply(t + step / 2, u + step * k1 / 2);
        Double k3 = derivativeFunction.apply(t + step / 2, u + step * k2 / 2);
        Double k4 = derivativeFunction.apply(t + step, u + step * k3);
        // Calculando o valor de u no ponto t + step
        return u + step * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
    }
}
