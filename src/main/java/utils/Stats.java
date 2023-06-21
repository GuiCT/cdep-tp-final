package utils;

import java.io.Serializable;

/**
 * <p>
 * Record para armazenar os resultados das estatísticas de um vetor.
 * </p>
 */
public record Stats(Double sum, Double mean, Double std, Integer count) implements Serializable {
}
