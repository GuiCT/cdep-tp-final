package view;

import interfaces.IOrdinaryDifferentialEquation;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import services.RungeKutta;
import services.odes.LogisticCurve;
import utils.ODEResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PanelODE extends JPanel {
    private final JPanel btnsPanel;
    private final JButton btnSelectFunction;
    private final JButton btnCalculateRK;
    private IOrdinaryDifferentialEquation function;
    private Double tInitial;
    private Double tFinal;
    private Double uInitial;
    private Double step;
    private JPanel chartPanel;

    public PanelODE() {
        super();
        BorderLayout layoutMain = new BorderLayout();
        this.setLayout(layoutMain);

        this.btnsPanel = new JPanel();
        FlowLayout layoutBtns = new FlowLayout();
        layoutBtns.setVgap(5);
        layoutBtns.setHgap(5);
        this.btnsPanel.setLayout(layoutBtns);
        this.btnSelectFunction = new JButton("Selecionar função");
        this.btnCalculateRK = new JButton("Calcular com Runge-Kutta");
        this.btnsPanel.add(this.btnSelectFunction);
        this.btnsPanel.add(this.btnCalculateRK);
        this.add(this.btnsPanel, BorderLayout.NORTH);

        // Create empty chart
        // Get width and height of the panel
        this.chartPanel = new JPanel();
        this.add(this.chartPanel, BorderLayout.CENTER);

        this.btnSelectFunction.addActionListener(this::selectFunction);
        this.btnCalculateRK.addActionListener(this::setParametersAndSendTask);
        this.btnCalculateRK.setEnabled(false);
    }

    private void selectFunction(ActionEvent e) {
        String[] options = {"Curva Logística"};
        int option = JOptionPane.showOptionDialog(
                this,
                "Selecione a função",
                "Selecione a função",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (option == 0) {
            this.createLogisticCurve();
        }
    }

    private void calcRK() {
        try (Socket socket = new Socket("localhost", 7777)) {
            ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
            RungeKutta rk = new RungeKutta(this.tInitial, this.tFinal, this.step, this.uInitial, this.function);
            objOS.writeObject(rk);
            objOS.flush();
            ODEResult result = (ODEResult) objIS.readObject();
            XYChart chart = QuickChart.getChart("Gráfico", "t", "u", "u(t)", result.t, result.u);
            chart.getStyler().setXAxisDecimalPattern("#.##");
            this.remove(this.chartPanel);
            this.chartPanel = new XChartPanel<>(chart);
            this.add(this.chartPanel, BorderLayout.CENTER);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createLogisticCurve() {
        JTextField rField = new JTextField(5);
        JTextField kField = new JTextField(5);
        JPanel myPanel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(5);
        layout.setHgap(5);
        myPanel.setLayout(layout);

        myPanel.add(new JLabel("R:"));
        myPanel.add(rField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("K:"));
        myPanel.add(kField);

        int result = JOptionPane.showConfirmDialog(this, myPanel,
                "Insira os valores de R e K", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            this.function = new LogisticCurve(Double.parseDouble(rField.getText()), Double.parseDouble(kField.getText()));
            this.btnCalculateRK.setEnabled(true);
        }
    }

    private void setParametersAndSendTask(ActionEvent e) {
        JTextField tInitialField = new JTextField(5);
        JTextField tFinalField = new JTextField(5);
        JTextField uInitialField = new JTextField(5);
        JTextField stepField = new JTextField(5);
        JPanel myPanel = new JPanel();
        GridLayout layout = new GridLayout(2, 8);
        layout.setVgap(5);
        layout.setHgap(5);
        myPanel.setLayout(layout);

        myPanel.add(new JLabel("t0:"));
        myPanel.add(tInitialField);
        myPanel.add(new JLabel("t1:"));
        myPanel.add(tFinalField);
        myPanel.add(new JLabel("u0:"));
        myPanel.add(uInitialField);
        myPanel.add(new JLabel("h:"));
        myPanel.add(stepField);

        int result = JOptionPane.showConfirmDialog(this, myPanel,
                "Insira os parâmetros de integração", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            this.tInitial = Double.parseDouble(tInitialField.getText());
            this.tFinal = Double.parseDouble(tFinalField.getText());
            this.uInitial = Double.parseDouble(uInitialField.getText());
            this.step = Double.parseDouble(stepField.getText());
            this.calcRK();
        }
    }
}
