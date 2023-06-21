import services.Hashing;
import services.HashingAlgorithm;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Temp {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
            // Enviando operação
            File file = new File("teste.csv");
            Hashing hashing = new Hashing(file, HashingAlgorithm.SHA256);
            objOS.writeObject(hashing);
            // Recebendo resposta
            String hash = (String) objIS.readObject();
            System.out.println(hash);
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
