package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>
 * Interface responsável pela execução REMOTA dos métodos definidos.
 * </p>
 * <p>
 * Recebe uma Task da interface ITask e chama o método execute na Compute
 * Engine.
 * </p>
 */
public interface IComputeEngine extends Remote {
    <T> T executeTaskInEngine(ITask<T> toBeExecutedTask) throws RemoteException;
}
