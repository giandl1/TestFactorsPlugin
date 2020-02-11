package gui;

import com.google.wireless.android.sdk.stats.GradleBuildProject;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.project.Project;
import data.*;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import javafx.scene.control.Spinner;
import org.apache.commons.io.FileUtils;
import processor.*;
import utils.CommandOutput;
import utils.VectorFind;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class PluginInitGUI extends JFrame {
    private Project proj;
    private Vector<PackageBean> packages;
    private Vector<PackageBean> testPackages;
    private File root;
    private JCheckBox ckMetrics, flakyTests, mutationCoverage, lineBranchCoverage, codeSmells;
    private JLabel flakyTestsExecutions, mutationCoverageTimeout;
    private JSpinner ftExecNumber, mcTimeout;
    private JButton editMetricsThresholds, runAnalysis;
    private JFrame initFrame, metricsThresholdFrame;

    public void addComponents(Container pane) {
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
        Long val = 10L;//set your own value, I used to check if it works
        Long min = 5L;
        Long max = 1000L;
        Long step = 1L;
        SpinnerModel mcSpinnerModel = new SpinnerNumberModel(val, min, max, step);
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
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.insets = new Insets(10, 0, 10, 0);
        pane.add(editMetricsThresholds, constraints);
        // Button runAnalysis COL3 - ROW6 6[--x]
        runAnalysis = new JButton("Start Analysis");
        runAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Thread() {
                    public void run() {
                        initFrame.setVisible(false);
                        JFrame frame = swingProgressBar();
                        boolean ok = false;
                        TestProjectAnalysis project = new TestProjectAnalysis();
                        String srcPath = root.getAbsolutePath() + "/src";
                        String mainPath = srcPath + "/main";
                        String testPath = srcPath + "/test";
                        String pluginPath = PathManager.getPluginsPath() + "\\TestFactorsPlugin\\lib";
                        project.setPluginPath(pluginPath);
                        boolean isMaven = false;
                        for (File file : root.listFiles()) {
                            if (file.isFile() && file.getName().equalsIgnoreCase("pom.xml"))
                                isMaven = true;
                        }
                        project.setName(proj.getName());
                        project.setPath(proj.getBasePath());
                        TestMutationUtilities utils = new TestMutationUtilities();
                        ArrayList<ClassBean> classes = utils.getClasses(packages);
                        Vector<ClassCoverageInfo> coverageInfos = null;
                        Vector<FlakyTestsInfo> flakyInfos=null;
                        Vector<TestClassAnalysis> classAnalyses = new Vector<>();
                        String javaLocation = CommandOutput.getCommandOutput("where java");
                        String[] location = javaLocation.split(".exe");
                        String notJbr = null;
                        for (String maro : location)
                            if (!maro.toLowerCase().contains("jetbrains"))
                                notJbr = maro;
                        notJbr += ".exe";
                        if (lineBranchCoverage.isSelected()) {
                            CoverageProcessor.setNotJbr(notJbr);
                            coverageInfos = CoverageProcessor.calculate(classes, testPackages, project, isMaven, pluginPath);
                        }
                        if(flakyTests.isSelected()) {
                            FlakyTestsProcessor.setJavaLocation(notJbr);
                            flakyInfos = FlakyTestsProcessor.calculate(packages, testPackages, project, isMaven, (int) ftExecNumber.getValue());
                        }
                        for (ClassBean prodClass : classes) {
                            ClassBean testSuite = utils.getTestClassBy(prodClass.getName(), testPackages);
                            if (testSuite != null) {
                                TestClassAnalysis analysis = new TestClassAnalysis();
                                analysis.setName(testSuite.getName());
                                analysis.setBelongingPackage(testSuite.getBelongingPackage());
                                analysis.setProductionClass(prodClass.getBelongingPackage() + "." + prodClass.getName());
                                analysis.setCkMetrics(CKMetricsProcessor.calculate(testSuite, project));
                                analysis.setSmells(SmellynessProcessor.calculate(testSuite, prodClass, packages, project));
                                if (coverageInfos != null) {
                                    analysis.setCoverage(VectorFind.findCoverageInfo(coverageInfos, testSuite.getName()));
                                } else {
                                    analysis.setCoverage(new ClassCoverageInfo());
                                }
                                if (mutationCoverage.isSelected()) {
                                    MutationCoverageProcessor.setJavaLocation(notJbr);
                                    analysis.setMutationCoverage(MutationCoverageProcessor.calculate(testSuite, prodClass, project, isMaven, (Long) mcTimeout.getValue()));
                                }
                                else
                                    analysis.setMutationCoverage(new ClassMutationCoverageInfo());
                                if (flakyTests.isSelected())
                                    analysis.setFlakyTests(VectorFind.findFlakyInfo(flakyInfos, testSuite.getName()));
                                else
                                    analysis.setFlakyTests(new FlakyTestsInfo());
                                classAnalyses.add(analysis);
                            }
                        }
                        project.setClassAnalysis(classAnalyses);
                        ReportProcessor.saveReport(project);

                        frame.setVisible(false);
                        JFrame ckShow = new AnalysisResultsUI(project);
                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        ckShow.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                JFrame frame = (JFrame) e.getSource();
                                try {
                                    FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\.temevi" + "\\htmlCoverage"));
                                    FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\coverage.csv"));
                                    FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\jacoco.exec"));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            }
                        });
                        ckShow.setVisible(true);

                    }

                }.start();
            }
        });


        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.insets = new

                Insets(10, 0, 10, 10);
        pane.add(runAnalysis, constraints);

        // constraints.fill = GridBagConstraints.HORIZONTAL; // natural height, maximum width
    }


    public PluginInitGUI(Vector<PackageBean> packages, Vector<PackageBean> testPackages, File root, Project proj) {
        this.packages = packages;
        this.testPackages = testPackages;
        this.root = root;
        this.proj = proj;
        initFrame = new JFrame("TEMEVI");
        initFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addComponents(initFrame.getContentPane());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        initFrame.setLocation(dimension.width / 2 - initFrame.getSize().width / 2, dimension.height / 2 - initFrame.getSize().height / 2);
        initFrame.pack();
        initFrame.setVisible(true);
    }

    public JFrame swingProgressBar() {
        JFrame frame = new JFrame("Performing analysis");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 150));
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Loading, please wait"));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        JProgressBar pbar = new JProgressBar();
        pbar.setIndeterminate(true);
        pbar.setVisible(true);
        panel.add(pbar);
        frame.add(panel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
