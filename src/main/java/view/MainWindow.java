package view;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {
	private final JPanel contentPane;
	private final JTabbedPane tabbedPane;
	private final PanelStats panelStats;
	private final PanelODE panelODE;

	public static void main(String[] args) {
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

	public MainWindow() {
		super();
		this.contentPane = new JPanel();
		this.tabbedPane = new JTabbedPane();
		// ======================================================================
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.contentPane.add(this.tabbedPane, gbc);

		this.panelStats = new PanelStats();
		this.tabbedPane.addTab("Estatísticas de um vetor", this.panelStats);
		this.panelODE = new PanelODE();
		this.tabbedPane.addTab("EDO", this.panelODE);
		this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
	}
}
