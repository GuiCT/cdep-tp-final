package view;

import services.BasicStats;
import utils.Stats;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PanelStats extends JPanel {
    private final JPanel btnsPanel;
    private final JButton btnOpenFile;
    private final JButton btnCalcStats;
    private final JTable tableStats;
    private File file;

    public PanelStats() {
        super();
        BorderLayout layoutMain = new BorderLayout();
        this.setLayout(layoutMain);

        this.btnsPanel = new JPanel();
        FlowLayout layoutBtns = new FlowLayout();
        layoutBtns.setVgap(5);
        layoutBtns.setHgap(5);
        this.btnsPanel.setLayout(layoutBtns);
        this.btnOpenFile = new JButton("Abrir arquivo .csv");
        this.btnCalcStats = new JButton("Calcular estatísticas");
        this.btnsPanel.add(this.btnOpenFile);
        this.btnsPanel.add(this.btnCalcStats);
        this.add(this.btnsPanel, BorderLayout.NORTH);

        this.tableStats = new JTable();
        this.add(this.tableStats, BorderLayout.CENTER);

        this.btnOpenFile.addActionListener(this::selectFile);
        this.btnCalcStats.addActionListener(this::calcStats);
        this.btnCalcStats.setEnabled(false);
    }

    private void selectFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo .csv");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new utils.CSVFilter());
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            this.btnCalcStats.setEnabled(true);
        }
    }

    private void calcStats(ActionEvent e) {
        try {
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
            // Enviando operação
            BasicStats basicStats = new BasicStats(this.file);
            objOS.writeObject(basicStats);
            // Recebendo resposta
            Stats stats = (Stats) objIS.readObject();
            this.updateTable(stats);
            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateTable(Stats stats) {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {"Soma", stats.sum()},
                        {"Média", stats.mean()},
                        {"Desvio padrão", stats.std()},
                        {"Quantidade", stats.count()}
                },
                new Object[]{"Estatística", "Valor"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableStats.setModel(tableModel);
    }
}
