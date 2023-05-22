package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IComputeEngine extends Remote {
    <T> T executeTaskInEngine(ITask<T> toBeExecutedTask) throws RemoteException;
}
