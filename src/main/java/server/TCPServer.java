package server;

import interfaces.IComputeEngine;
import interfaces.ITask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * <p>
 * Servidor TCP que recebe e encaminha a <em>task</em> de um cliente.
 * </p>
 */
public class TCPServer implements Runnable {
    // ServerSocket para receber a conexão do cliente
    // Mantém-se o mesmo durante toda a execução do servidor TCP
    private final ServerSocket serverSocket;
    // Referência para a ComputeEngine
    private final IComputeEngine computeEngine;
    // Stream de entrada, pela qual se recebe a Task enviada
    private ObjectInputStream objIS;
    // Stream de saída, pela qual se envia o resultado da Task
    private ObjectOutputStream objOS;

    /**
     * <p>
     * Construtor do Server TCP
     * </p>
     */
    public TCPServer() throws IOException, NotBoundException {
        // Criando server socket na porta 7777
        this.serverSocket = new ServerSocket(7777);
        // Obtendo referência para a ComputeEngine
        Registry rmiRegistry = LocateRegistry.getRegistry("localhost", 1099);
        this.computeEngine = (IComputeEngine) rmiRegistry.lookup("ComputeEngine");
    }

    public static void main(String[] args) {
        try {
            TCPServer tcpServer = new TCPServer();
            tcpServer.run();
        } catch (IOException | NotBoundException e) {
            System.out.println("Erro ao executar o servidor TCP");
            System.out.println("Verifique se o Compute Engine está em execução");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Recebendo conexões indefinidamente
        while (true) {
            try {
                // Atualizando streams de entrada e saída a partir do socket do cliente
                // A cada nova conexão, é necessário atualizar as streams
                updateStreams(this.serverSocket.accept());
                // Recebendo a Task e executando-a
                Object result = this.receiveTaskAndExecute();
                // Enviando o resultado da Task para o cliente
                this.objOS.writeObject(result);
                this.objOS.flush();
                System.out.println("Tarefa executada");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Função que atualiza as streams de entrada e saída a partir do socket do
     * cliente
     * 
     * @param clientSocket Socket do cliente recém conectado
     * @throws IOException Exceção lançada caso haja erro ao atualizar as streams
     */
    private void updateStreams(Socket clientSocket) throws IOException {
        System.out.println("Novo cliente conectado");
        this.objOS = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objIS = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Função que recebe a Task enviada pelo cliente e a executa na ComputeEngine
     * 
     * @return Resultado da execução da Task
     * @throws IOException Exceção lançada caso haja erro ao receber ou executar a
     *                     Task
     */
    private Object receiveTaskAndExecute() throws IOException {
        Object task = null;
        try {
            task = this.objIS.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada");
            e.printStackTrace();
        }
        if (task instanceof ITask taskToExecute) {
            // Enviando task para o ComputeEngine
            // Automaticamente retorna o resultado da execução da task para o método run
            System.out.println("Enviado o objeto " + taskToExecute.getClass().getName() + " que tem o serviço " + taskToExecute.getTaskName());
            Object retVal = computeEngine.executeTaskInEngine(taskToExecute);
            System.out.println("Recebida a resposta do serviço " + taskToExecute.getTaskName());
            return retVal;
        } else {
            System.out.println("Objeto recebido não é instância de ITask. Retornando null...");
            return null;
        }
    }
}
