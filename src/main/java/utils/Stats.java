package utils;

import java.io.Serializable;

public class Stats implements Serializable {
    public final Double sum;
    public final Double mean;
    public final Double std;
    public final Integer count;

    public Stats(Double sum, Double mean, Double std, Integer count) {
        this.sum = sum;
        this.mean = mean;
        this.std = std;
        this.count = count;
    }
}
