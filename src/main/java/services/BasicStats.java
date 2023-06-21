package services;

import interfaces.ITask;
import utils.Stats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicStats implements ITask<Stats> {
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

    @Override
    public Stats execute() {
        calculateSum();
        calculateMean();
        calculateStd();
        return new Stats(sum, mean, std, data.size());
    }

    private void calculateSum() {
        this.sum = data.stream().reduce(0.0, Double::sum);
    }

    private void calculateMean() {
        this.mean = sum / data.size();
    }

    private void calculateStd() {
        double sumOfSquaredDifferences = data.stream().reduce(0.0, (acc, value) -> acc + Math.pow(value - mean, 2));
        this.std = Math.sqrt(sumOfSquaredDifferences / data.size());
    }
}
