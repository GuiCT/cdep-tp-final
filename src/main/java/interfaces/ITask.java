package interfaces;

import java.io.Serializable;

/**
 * Interface destinada a representar uma tarefa executada em um ComputeEngine.
 *
 * @param <T>
 */
public interface ITask<T> extends Serializable {
    T execute();
}
