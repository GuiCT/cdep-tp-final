package view;

import services.odes.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class CreatePolynomial extends JPanel {
    private final JButton btnInsertUCoefficients;
    private final JButton btnInsertTCoefficients;
    private Integer nUCoefficients = 1;
    private Integer nTCoefficients = 1;
    private Vector<Double> uCoefficients;
    private Vector<Double> tCoefficients;

    public CreatePolynomial() {
        super();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JPanel uPanel = new JPanel();
        JPanel tPanel = new JPanel();
        JPanel btnsPanel = new JPanel();
        JLabel uLabel = new JLabel("Quantidade de termos u:");
        JSpinner nUField = new JSpinner();
        JLabel tLabel = new JLabel("Quantidade de termos t:");
        JSpinner nTField = new JSpinner();
        this.btnInsertUCoefficients = new JButton("Inserir coeficientes de u");
        this.btnInsertTCoefficients = new JButton("Inserir coeficientes de t");

        nUField.setValue(1);
        nUField.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            int value = (Integer) spinner.getValue();
            if (value < 0) {
                spinner.setValue(0);
                this.nUCoefficients = 0;
            } else if (value == 0) {
                this.btnInsertUCoefficients.setEnabled(false);
                this.nUCoefficients = 0;
            } else {
                this.btnInsertUCoefficients.setEnabled(true);
                this.nUCoefficients = value;
            }
        });
        nTField.setValue(1);
        nTField.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            int value = (Integer) spinner.getValue();
            if (value < 0) {
                spinner.setValue(0);
                this.nTCoefficients = 0;
            } else if (value == 0) {
                this.btnInsertTCoefficients.setEnabled(false);
                this.nTCoefficients = 0;
            } else {
                this.btnInsertTCoefficients.setEnabled(true);
                this.nTCoefficients = value;
            }
        });
        btnInsertUCoefficients.addActionListener(this::insertCoefficients);
        btnInsertTCoefficients.addActionListener(this::insertCoefficients);

        FlowLayout uLayout = new FlowLayout();
        uLayout.setHgap(5);
        uLayout.setVgap(5);
        uPanel.setLayout(uLayout);
        FlowLayout tLayout = new FlowLayout();
        tLayout.setHgap(5);
        tLayout.setVgap(5);
        tPanel.setLayout(tLayout);
        FlowLayout btnsLayout = new FlowLayout();
        btnsLayout.setHgap(5);
        btnsLayout.setVgap(5);
        btnsPanel.setLayout(btnsLayout);

        uPanel.add(uLabel);
        uPanel.add(nUField);
        tPanel.add(tLabel);
        tPanel.add(nTField);
        btnsPanel.add(btnInsertUCoefficients);
        btnsPanel.add(btnInsertTCoefficients);

        this.add(uPanel);
        this.add(Box.createVerticalStrut(15));
        this.add(tPanel);
        this.add(btnsPanel);
    }

    public Polynomial getPolynomial() {
        return new Polynomial(this.uCoefficients, this.tCoefficients);
    }

    private void insertCoefficients(ActionEvent e) {
        Object source = e.getSource();
        int quantity = source == this.btnInsertTCoefficients ? this.nTCoefficients : this.nUCoefficients;
        char variable = source == this.btnInsertTCoefficients ? 't' : 'u';

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        Vector<JTextField> fields = new Vector<>();

        for (int i = 0; i < quantity; i++) {
            JPanel subPanel = new JPanel();
            FlowLayout subLayout = new FlowLayout();
            subLayout.setHgap(5);
            subLayout.setVgap(5);
            subPanel.setLayout(subLayout);
            JLabel label = new JLabel(variable + "^" + i + ":");
            JTextField field = new JTextField(5);
            fields.add(field);
            subPanel.add(label);
            subPanel.add(field);
            panel.add(subPanel);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Insira os coeficientes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (source == this.btnInsertTCoefficients) {
                this.tCoefficients = new Vector<>();
                for (JTextField field : fields) {
                    this.tCoefficients.add(Double.parseDouble(field.getText()));
                }
            } else if (source == this.btnInsertUCoefficients) {
                this.uCoefficients = new Vector<>();
                for (JTextField field : fields) {
                    this.uCoefficients.add(Double.parseDouble(field.getText()));
                }
            }
        }
    }
}
