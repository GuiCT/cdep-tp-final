package view;

import services.Hashing;
import services.HashingAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <p>
 * Classe que representa o painel de hashing de arquivos.
 * </p>
 */
public class PanelHashing extends JPanel {
    private final JPanel btnsPanel;
    private final JButton btnOpenFile;
    private final JButton btnHashFile;
    private final JTextArea textArea;
    private File file;

    public PanelHashing() {
        super();
        BorderLayout layoutMain = new BorderLayout();
        this.setLayout(layoutMain);

        this.btnsPanel = new JPanel();
        FlowLayout layoutBtns = new FlowLayout();
        layoutBtns.setVgap(5);
        layoutBtns.setHgap(5);
        this.btnsPanel.setLayout(layoutBtns);
        this.btnOpenFile = new JButton("Selecionar arquivo");
        this.btnHashFile = new JButton("Aplicar hashing");
        this.btnsPanel.add(this.btnOpenFile);
        this.btnsPanel.add(this.btnHashFile);
        this.add(this.btnsPanel, BorderLayout.NORTH);

        this.textArea = new JTextArea();
        this.add(this.textArea, BorderLayout.CENTER);

        this.btnOpenFile.addActionListener(this::selectFile);
        this.btnHashFile.addActionListener(this::doHash);
        this.btnHashFile.setEnabled(false);
    }

    /**
     * Abre um JFileChooser para selecionar um arquivo
     * 
     * @param e Evento de clique no botão
     */
    private void selectFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            this.btnHashFile.setEnabled(true);
        }
    }

    /**
     * Envia uma requisição de hashing para o servidor
     * 
     * @param e Evento de clique no botão
     */
    private void doHash(ActionEvent e) {
        HashingAlgorithm selectedHashingAlgorithm = this.choseHashingAlgorithm();
        if (selectedHashingAlgorithm == null) {
            return;
        }

        try {
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
            // Enviando operação
            Hashing hashService = new Hashing(file, selectedHashingAlgorithm);
            objOS.writeObject(hashService);
            System.out.println("Solicitado o serviço de hashing de arquivos ao servidor");
            // Recebendo resposta
            String hashed = (String) objIS.readObject();
            System.out.println("Recebido o resultado do servidor");
            this.textArea.setText(hashed);
            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Abre um JOptionPane para escolher o algoritmo de hashing
     * 
     * @return Algoritmo de hashing escolhido
     */
    private HashingAlgorithm choseHashingAlgorithm() {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        JComboBox<String> hashAlgComboBox = new JComboBox<>();
        hashAlgComboBox.addItem("MD5");
        hashAlgComboBox.addItem("SHA-1");
        hashAlgComboBox.addItem("SHA-256");
        hashAlgComboBox.addItem("SHA-512");
        panel.add(new JLabel("Escolha o algoritmo de hashing:"));
        panel.add(hashAlgComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Selecione o algoritmo de Hash",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String selected = (String) hashAlgComboBox.getSelectedItem();
            switch (selected) {
                case "MD5":
                    return HashingAlgorithm.MD5;
                case "SHA-1":
                    return HashingAlgorithm.SHA1;
                case "SHA-256":
                    return HashingAlgorithm.SHA256;
                case "SHA-512":
                    return HashingAlgorithm.SHA512;
                default:
                    throw new RuntimeException("Algoritmo de hashing não encontrado");
            }
        } else {
            return null;
        }
    }
}
