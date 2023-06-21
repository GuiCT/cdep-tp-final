package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super();
        JPanel contentPane = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        // ======================================================================
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(tabbedPane, gbc);

        PanelStats panelStats = new PanelStats();
        tabbedPane.addTab("Estatísticas de um vetor", panelStats);
        PanelODE panelODE = new PanelODE();
        tabbedPane.addTab("EDO", panelODE);
        PanelHashing panelHashing = new PanelHashing();
        tabbedPane.addTab("Hashing", panelHashing);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }
}
