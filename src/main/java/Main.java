import com.formdev.flatlaf.FlatLightLaf;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    /**
     * Método principal da aplicação
     * 
     * @param args Argumentos passados para a aplicação via linha de comando
     */
    public static void main(String[] args) {
        // FlatLaf é um Look and Feel para Swing
        // https://www.formdev.com/flatlaf/
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                MainWindow frame = new MainWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
