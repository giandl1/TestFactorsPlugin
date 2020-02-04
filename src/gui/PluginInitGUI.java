package gui;

import javafx.scene.control.Spinner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PluginInitGUI extends JFrame {
    private static JCheckBox ckMetrics, flakyTests, mutationCoverage, lineBranchCoverage, codeSmells;
    private static JLabel flakyTestsExecutions, mutationCoverageTimeout;
    private static JSpinner ftExecNumber, mcTimeout;
    private static JButton editMetricsThresholds, runAnalysis;
    private static JFrame initFrame, metricsThresholdFrame;

    public static void addComponents(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // CheckBox ckMetrics COL1 - ROW1 1[x--]
        ckMetrics = new JCheckBox("CK Metrics", true);
        ckMetrics.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(ckMetrics, constraints);
        // CheckBox FlakyTests COL1 - ROW2 2[x--]
        flakyTests = new JCheckBox("Flaky Tests", false);
        flakyTests.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (flakyTests.isSelected()) {
                    ftExecNumber.setEnabled(true);
                } else {
                    ftExecNumber.setEnabled(false);
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(flakyTests, constraints);
        // Label FlakyTestsExecutions COL2 - ROW2 2[-x-]
        flakyTestsExecutions = new JLabel("Numbers of Flaky Tests Executions:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        pane.add(flakyTestsExecutions, constraints);
        // JSpinner FlakyTestsExecutions COL3 - ROW2 2[--x]
        SpinnerModel ftSpinnerModel = new SpinnerNumberModel(10, 10, 50, 1);
        ftExecNumber = new JSpinner(ftSpinnerModel);
        ftExecNumber.setEnabled(false);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(ftExecNumber, constraints);
        // CheckBox Mutation Coverage COL1 - ROW3 3[x--]
        mutationCoverage = new JCheckBox("Mutation Coverage", false);
        mutationCoverage.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (mutationCoverage.isSelected()) {
                    mcTimeout.setEnabled(true);
                } else {
                    mcTimeout.setEnabled(false);
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(mutationCoverage, constraints);
        // Label MutationCoverage Timeout COL2 - ROW3 3[-x-]
        mutationCoverageTimeout = new JLabel("Mutation Coverage Timeout:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        pane.add(mutationCoverageTimeout, constraints);
        // JSpinner MutationCoverageTimeout COL3 - ROW3 3[--x]
        SpinnerModel mcSpinnerModel = new SpinnerNumberModel(100, 100, 1000, 100);
        mcTimeout = new JSpinner(mcSpinnerModel);
        mcTimeout.setEnabled(false);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(mcTimeout, constraints);
        // CheckBox LineBranchCoverage COL1 - ROW4 4[x--]
        lineBranchCoverage = new JCheckBox("Line and Branch Coverage");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(lineBranchCoverage, constraints);
        // CheckBox CodeSmells COL1 - ROW5 5[x--]
        codeSmells = new JCheckBox("Code Smells");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.LINE_START;
        pane.add(codeSmells, constraints);
        // Button editMetricsThresholds COL2 - ROW6 6[-x-]
        editMetricsThresholds = new JButton("Edit Metrics Thresholds");
        editMetricsThresholds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initFrame.setEnabled(false);
                metricsThresholdsFrame();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.insets = new Insets(10, 0, 10, 0);
        pane.add(editMetricsThresholds, constraints);
        // Button runAnalysis COL3 - ROW6 6[--x]
        runAnalysis = new JButton("Start Analysis");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.insets = new Insets(10, 0, 10, 10);
        pane.add(runAnalysis, constraints);

        // constraints.fill = GridBagConstraints.HORIZONTAL; // natural height, maximum width
    }

    public static void metricsThresholdsFrame() {
        metricsThresholdFrame = new JFrame("Edit Metrics Thresholds");
        metricsThresholdFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        metricsThresholdFrame.setSize(600, 450);
    }

    public PluginInitGUI() {
        initFrame = new JFrame("TEMEVI");
        initFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponents(initFrame.getContentPane());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        initFrame.setLocation(dimension.width/2-initFrame.getSize().width/2, dimension.height/2-initFrame.getSize().height/2);
        initFrame.pack();
        initFrame.setVisible(true);
    }
}
