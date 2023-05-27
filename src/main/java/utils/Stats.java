package utils;

import java.io.Serializable;

public record Stats(Double sum, Double mean, Double std, Integer count) implements Serializable {
}
