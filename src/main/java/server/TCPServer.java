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

public class TCPServer implements Runnable {
    private final ServerSocket serverSocket;
    private final IComputeEngine computeEngine;
    private ObjectInputStream objIS;
    private ObjectOutputStream objOS;

    public TCPServer() throws IOException, NotBoundException {
        this.serverSocket = new ServerSocket(7777);
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
        while (true) {
            try {
                updateStreams(this.serverSocket.accept());
                Object result = this.receiveTaskAndExecute();
                this.objOS.writeObject(result);
                this.objOS.flush();
                System.out.println("Tarefa executada");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateStreams(Socket clientSocket) throws IOException {
        System.out.println("Novo cliente conectado");
        this.objOS = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objIS = new ObjectInputStream(clientSocket.getInputStream());
    }

    private Object receiveTaskAndExecute() throws IOException {
        Object task = null;
        try {
            task = this.objIS.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada");
            e.printStackTrace();
        }
        if (task instanceof ITask taskToExecute) {
            return computeEngine.executeTaskInEngine(taskToExecute);
        } else {
            System.out.println("Objeto recebido não é instância de Task. Retornando null...");
            return null;
        }
    }
}
