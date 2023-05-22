import services.BasicStats;
import services.TaskTest;
import utils.Stats;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            BasicStats basicStats = new BasicStats(new File("teste.csv"));
            // Atuando como cliente, faz requisição ao server TCP na porta 7777
            System.out.println("Sending task");
            Socket clientSocket = new Socket("localhost", 7777);
            ObjectOutputStream objOS = new ObjectOutputStream(clientSocket.getOutputStream());
            objOS.writeObject(basicStats);
            objOS.flush();
            // Recebe resultado
            ObjectInputStream objIS = new ObjectInputStream(clientSocket.getInputStream());
            Stats result = (Stats) objIS.readObject();
            System.out.printf("""
                    Sum: %f
                    Mean: %f
                    Std: %f
                    Size: %d""", result.sum, result.mean, result.std, result.count);
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
