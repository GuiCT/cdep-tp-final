package services;

import interfaces.ITask;
import utils.Stats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Classe que representa uma tarefa de cálculo de estatísticas básicas.
 * </p>
 * <p>
 * As estatísticas básicas calculadas são:
 * </p>
 * <ul>
 * <li>Soma</li>
 * <li>Média</li>
 * <li>Desvio padrão</li>
 * </ul>
 */
public class BasicStats implements ITask<Stats> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Double> data;
    private double sum;
    private double mean;
    private double std;

    public BasicStats(File file) throws IOException {
        // Lendo informações de um arquivo CSV com delimitador ','
        this.data = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                if (c == ',') {
                    data.add(Double.parseDouble(sb.toString()));
                    sb = new StringBuilder();
                } else {
                    sb.append((char) c);
                }
            }
            data.add(Double.parseDouble(sb.toString()));
        }
    }

    @Override
    public String getTaskName() {
        return "Estatísticas de um vetor";
    }

    // Calcula cada uma das estatísticas
    @Override
    public Stats execute() {
        calculateSum();
        calculateMean();
        calculateStd();
        return new Stats(sum, mean, std, data.size());
    }

    private void calculateSum() {
        // Reduce inicia com 0.0 e soma cada elemento do vetor
        this.sum = data.stream().reduce(0.0, Double::sum);
    }

    private void calculateMean() {
        // Média aritmética é a soma dos elementos dividido pelo número de elementos
        this.mean = sum / data.size();
    }

    private void calculateStd() {
        // Desvio padrão é a raiz quadrada da soma dos quadrados
        // das diferenças entre cada elemento e a média
        double sumOfSquaredDifferences = data.stream().reduce(0.0, (acc, value) -> acc + Math.pow(value - mean, 2));
        this.std = Math.sqrt(sumOfSquaredDifferences / data.size());
    }
}
