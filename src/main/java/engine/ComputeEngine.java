package engine;

import interfaces.IComputeEngine;
import interfaces.ITask;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe responsável pela execução dos métodos definidos.
 * A interface IComputeEngine é uma interface remota que permite a execução
 * da task a partir do método executeTaskInEngine.
 * Essa interface é então implementada a partir da classe ComputeEngine.
 */
public class ComputeEngine implements IComputeEngine {
    public static void main(String[] args) {
        try {
            // Instanciação da Compute Engine
            IComputeEngine computeEngine = new ComputeEngine();
            // Exportando o objeto instanciado e formando um *stub*
            IComputeEngine stub = (IComputeEngine) UnicastRemoteObject.exportObject(computeEngine, 0);
            // Criando um Java RMI Registry na porta 1099
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            // Associando o *stub* gerado ao nome "ComputeEngine"
            rmiRegistry.rebind("ComputeEngine", stub);
        } catch (RemoteException e) {
            System.out.println("Erro ao inicializar Compute Engine.");
            e.printStackTrace();
        }
    }

    public <T> T executeTaskInEngine(ITask<T> task) {
        System.out.println("Recebida solicitação do servidor para executar a task " + task.getTaskName());
        // Implementação da *task* é feita a partir do método execute,
        // definido na interface ITask
        T retVal = task.execute();
        System.out.println("Task " + task.getTaskName() + " executada com sucesso");
        return retVal;
    }
}
