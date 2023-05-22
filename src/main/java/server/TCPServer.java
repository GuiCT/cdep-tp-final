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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                updateStreams(this.serverSocket.accept());
                System.out.println("New client connected");
                Object result = this.receiveTaskAndExecute();
                this.objOS.writeObject(result);
                this.objOS.flush();
                System.out.println("Task executed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateStreams(Socket clientSocket) throws IOException {
        System.out.println("Updating streams");
        this.objIS = new ObjectInputStream(clientSocket.getInputStream());
        this.objOS = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    private Object receiveTaskAndExecute() throws IOException {
        Object task = null;
        try {
            task = this.objIS.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (task instanceof ITask taskToExecute) {
            return computeEngine.executeTaskInEngine(taskToExecute);
        } else {
            return null;
        }
    }
}
