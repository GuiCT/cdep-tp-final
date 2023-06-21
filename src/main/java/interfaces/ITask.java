package interfaces;

import java.io.Serializable;

/**
 * <p>
 * Interface destinada a representar uma <em>task</em> executada em um
 * ComputeEngine.
 * </p>
 * <p>
 * A interface é genérica, de forma que o tipo de retorno do método
 * <strong>execute</strong>
 * </p>
 * Dessa forma, é possível parametrizar o tipo de retorno da <em>task</em>.
 * Também é definido um método para retornar o nome da <em>task</em>.
 */
public interface ITask<T> extends Serializable {
    String getTaskName();

    T execute();
}
