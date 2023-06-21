package engine;

import interfaces.IComputeEngine;
import interfaces.ITask;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements IComputeEngine {
    public static void main(String[] args) {
        try {
            IComputeEngine computeEngine = new ComputeEngine();
            IComputeEngine stub = (IComputeEngine) UnicastRemoteObject.exportObject(computeEngine, 0);
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            rmiRegistry.rebind("ComputeEngine", stub);
        } catch (RemoteException e) {
            System.out.println("Erro ao inicializar Compute Engine.");
            e.printStackTrace();
        }
    }

    public <T> T executeTaskInEngine(ITask<T> task) {
        System.out.println("Executando task: " + task.getTaskName());
        return task.execute();
    }
}
