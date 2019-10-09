package gui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBComboBoxLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.sun.jna.platform.unix.solaris.LibKstat;
import config.ConfigUtils;
import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import data.TestSmellsMetrics;
import gherkin.lexer.Ca;
import it.unisa.testSmellDiffusion.metrics.TestSmellMetrics;
import it.unisa.testSmellDiffusion.testSmellRules.TestSmellMetric;
import net.miginfocom.layout.Grid;
import processor.MetricStoricValues;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.concurrent.Flow;

public class AnalysisResultsUI extends JFrame {
    private static final Logger LOGGER = Logger.getInstance("global");

    private TestProjectAnalysis project;
    private JPanel eastPanel;
    private Vector<TestSmellMetric> selectedMetrics;
    private MetricsChart chart;
    private TestSmellMetricsThresholdsList thresholdsList;
    private Vector<TestSmellMetricThresholds> selectedThresholds;
    private JPanel mainPanel;
    private JPanel classPanel;

    public AnalysisResultsUI(TestProjectAnalysis project) throws HeadlessException {
        this.project = project;
        File default_conf = new File(project.getPath() + "\\default_config.ini");
        File conf = new File(project.getPath() + "\\config.ini");
       /*if(!default_conf.exists()) {
            thresholds = new SmellsThresholds(1,1,1,1,1,1,1,1,1);
            new ConfigUtils().writeThresholds(new File(projdir + "\\default_config.ini"), thresholds);
        }*/
        if(default_conf.exists() && !conf.exists()){
            thresholdsList = new ConfigUtils().readThresholds(default_conf);
        }
        else if(conf.exists()){
            thresholdsList = new ConfigUtils().readThresholds(conf);
        }
        setPreferredSize(new Dimension(1280,800));
        JPanel grid = new JPanel(new GridLayout(1,3));
        setTitle("Analysis Results");
        add(projectPanel(), BorderLayout.NORTH);
        classPanel = classPanel();
       add(classPanel, BorderLayout.WEST);
       // grid.add(classPanel());
       // grid.add(new JLabel());
        eastPanel = new JPanel();
        eastPanel.setBorder(new EmptyBorder(10,10,10,10));
        //grid.add(eastPanel);
        add(eastPanel,BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
    }

    public JPanel classPanel(){
        try {
            JPanel panel = new JPanel();
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel titleText = new JLabel(("Classes list"));
            titleText.setBorder(new EmptyBorder(0,0,0,8));
            titleText.setFont(titleText.getFont().deriveFont(15.0f));
            panel.add(titleText);
           panel.add(Box.createRigidArea(new Dimension(0, 10)));

            //  panel.setLayout(new GridLayout(1,1));
            Vector<TestClassAnalysis> classAnalysis = project.getClassAnalysis();
       classAnalysis.sort(new Comparator<TestClassAnalysis>() {
            @Override
            public int compare(TestClassAnalysis o1, TestClassAnalysis o2) {
                return o1.getSmells().getWeight() > o2.getSmells().getWeight() ? -1 :(o1.getSmells().getWeight() < o2.getSmells().getWeight() ? 1 : 0);
            }
        });
            JList<TestClassAnalysis> classPanel = new JBList(classAnalysis);
            classPanel.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (renderer instanceof JLabel && value instanceof TestClassAnalysis) {
                        // Here value will be of the Type 'CD'
                        ((JLabel) renderer).setText(((TestClassAnalysis) value).getName());
                        TestClassAnalysis colorChange = (TestClassAnalysis) value;
                        JLabel text = (JLabel) renderer;
                        renderer.setFont( (text.getFont ().deriveFont (15.0f)));
                        if (colorChange.getSmells().isAffectedCritic()) {
                            renderer.setForeground(new Color(255,102,102));
                        }
                        else if(colorChange.getSmells().isAffected())
                            renderer.setForeground(Color.YELLOW);
                    }
                    return renderer;
                }
            });
            classPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            classPanel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    GregorianCalendar calendar = new GregorianCalendar();

                    if (!listSelectionEvent.getValueIsAdjusting()) {
                        TestClassAnalysis selected = classPanel.getSelectedValue();
                        if (selected != null) {
                            LOGGER.info("sono qui nella lista");
                            eastPanel.removeAll();
                            eastPanel.revalidate();
                            eastPanel.repaint();

                            JPanel azz = new JPanel();
                            azz.setLayout(new BoxLayout(azz,BoxLayout.X_AXIS));
                            JPanel classInfo = new JPanel();
                            classInfo.setLayout(new BoxLayout(classInfo, BoxLayout.Y_AXIS));
                            classInfo.add(new JLabel("<html>Belonging package:<font color='white'> " + selected.getBelongingPackage() + "</font></html>") );
                            classInfo.add(new JLabel("<html>Class name:<font color='white'> "+ selected.getName() + "</font></html>"));
                            classInfo.add(new JLabel("<html>LOC:<font color='white'> " + selected.getCkMetrics().getLoc() + "</font></html>"));
                            classInfo.add(new JLabel("<html>NOM:<font color='white'> "+ selected.getCkMetrics().getNom() + "</font></html>"));
                            classInfo.add(new JLabel("<html>WMC:<font color='white'> " + selected.getCkMetrics().getWmc() + "</font></html>"));
                            classInfo.add(new JLabel("<html>RFC: <font color='white'> " + selected.getCkMetrics().getRfc() + "</font></html>"));
                            classInfo.add(new JLabel("<html>Assertion Density: <font color='white'> " + selected.getCoverage().getAssertionDensity() + "</font></html>"));
                            double prevLineCov = new MetricStoricValues().getPreviousLineCoverage(selected.getBelongingPackage() + "." + selected.getName(), project.getPath() + "\\reports" );
                            if(prevLineCov!=-1){
                                String color;
                                if(selected.getCoverage().getLineCoverage() > prevLineCov) color = "#00e600";
                                else if(selected.getCoverage().getLineCoverage() < prevLineCov) color = "#ff6666";
                                else color = "'white'";
                                JLabel label = new JLabel("<html>Line Coverage: <font color=" + color + "> " + selected.getCoverage().getLineCoverage() + "</font>" + "&nbsp;&nbsp;(was " + prevLineCov + ")</html>" );
                                classInfo.add(label);
                            }
                            else
                            classInfo.add(new JLabel("<html>Line Coverage: <font color='white'> " + selected.getCoverage().getLineCoverage() + "</font></html>"));
                            double prevBranchCov = new MetricStoricValues().getPreviousBranchCoverage(selected.getBelongingPackage() + "." + selected.getName(), project.getPath() + "\\reports" );
                            if(selected.getCoverage().getBranchCoverage()==-1.0)
                                classInfo.add(new JLabel("<html>Branch Coverage: <font color='white'> N/A</font></html>"));

                            else{
                                if(prevBranchCov!=-1.0) {
                                    String color;
                                    if (selected.getCoverage().getBranchCoverage() > prevBranchCov) color = "#00e600";
                                    else if (selected.getCoverage().getBranchCoverage() < prevBranchCov)
                                        color = "#ff6666";
                                    else color = "'white'";
                                    JLabel label = new JLabel("<html>Branch Coverage: <font color=" + color + "> " + selected.getCoverage().getBranchCoverage() + "</font>" + "&nbsp;&nbsp;(was " + prevBranchCov + ")</html>");
                                    classInfo.add(label);
                                }
                                else
                                    classInfo.add(new JLabel("<html>Branch Coverage: <font color='white'> " + selected.getCoverage().getBranchCoverage() + "</font></html>"));

                            }
                            classInfo.setBorder(new EmptyBorder(0,0,300,40));
                            azz.add(classInfo);

                            JComboBox<String> smells = new ComboBox<>();
                            smells.addItem("Assertion Roulette");
                            smells.addItem("Eager Test");
                            smells.addItem("General Fixture");
                            smells.addItem("Lazy Test");
                            smells.addItem("Sensitive Equality");
                            smells.addItem("Mystery Guest");
                            smells.addItem("Indirect Testing");
                            smells.addItem("For Testers Only");
                            smells.addItem("Resource Optimism");

                            JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                            comboPanel.add(new JLabel("Select a smell: "));
                            comboPanel.add(smells);
                            mainPanel = new JPanel();
                            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                            mainPanel.add(comboPanel);
                            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                            smells.setSelectedIndex(0);
                            selectedMetrics = selected.getSmells().getMetrics().getArMetrics();
                            selectedThresholds = thresholdsList.getArMetrics();
                            smells.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    String selectedSmell = (String) smells.getSelectedItem();
                                    if (selectedSmell.equalsIgnoreCase("assertion roulette")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getArMetrics();
                                        selectedThresholds = thresholdsList.getArMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("eager test")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getEtMetrics();
                                        selectedThresholds = thresholdsList.getEtMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("general fixture")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getGfMetrics();
                                        selectedThresholds = thresholdsList.getGfMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("lazy test")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getLtMetrics();
                                        selectedThresholds = thresholdsList.getLtMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("for testers only")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getFtoMetrics();
                                        selectedThresholds = thresholdsList.getFtoMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("mystery guest")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getMgMetrics();
                                        selectedThresholds = thresholdsList.getMgMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("sensitive equality")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getSeMetrics();
                                        selectedThresholds = thresholdsList.getSeMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("resource optimism")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getRoMetrics();
                                        selectedThresholds = thresholdsList.getRoMetrics();
                                    } else if (selectedSmell.equalsIgnoreCase("indirect testing")) {
                                        selectedMetrics = selected.getSmells().getMetrics().getItMetrics();
                                        selectedThresholds = thresholdsList.getItMetrics();
                                    }
                                    chart = new MetricsChart(selectedMetrics, selectedThresholds, selected.getBelongingPackage() + "." + selected.getName(), project.getPath(), 1, calendar.get(Calendar.YEAR) -3);
                                    mainPanel.remove(4);
                                    mainPanel.remove(3);
                                    mainPanel.remove(2);

                                    mainPanel.add(filterPanel(selected,selectedMetrics,selectedThresholds));
                                    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                                    mainPanel.add(chart.getPanel());
                                    mainPanel.revalidate();
                                    mainPanel.repaint();

                                }
                            });
                            mainPanel.add(filterPanel(selected,selectedMetrics,selectedThresholds));
                            chart = new MetricsChart(selectedMetrics, selectedThresholds, selected.getBelongingPackage() + "." + selected.getName(), project.getPath(), 1, calendar.get(Calendar.YEAR) - 3);

                            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                            mainPanel.add(chart.getPanel());
                            azz.add(mainPanel);
                            eastPanel.add(azz);

                        }
                    }
                }
            });


        JScrollPane scrollPane = new JBScrollPane(classPanel);
        scrollPane.setPreferredSize(new Dimension(150,300));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel yellow = new JLabel("*Class is affected by one or more smells");
        yellow.setForeground(Color.YELLOW);
        JLabel red = new JLabel(("**Class is critically affected by one or more smells"));
        red.setForeground(new Color(255,102,102));
        panel.add(yellow);
        panel.add(red);

            return panel;
        } catch(Exception e){
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    private JPanel projectPanel(){
        JPanel projectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        projectPanel.setBorder(new EmptyBorder(10,10,10,10));
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Project Name: " + project.getName()));
        panel.add(new JLabel());
        panel.add(new JLabel("Nr. of test classes: " + project.getTestClassesNumber()));
        panel.add(new JLabel("Nr. of test classes affected by smells: " + project.getAffectedClasses()));
        panel.add(new JLabel("LOC: " + project.getLoc() + "   NOM: " + project.getNom() + "   WMC: " + project.getWmc() + "   RFC:" + project.getRfc() + "   Line Coverage: " + project.getLineCoverage()  + "         "));
        projectPanel.add(panel);
        return projectPanel;

    }

    private JPanel filterPanel(TestClassAnalysis selected, Vector<TestSmellMetric> selectedMetrics, Vector<TestSmellMetricThresholds> selectedThresholds){
        GregorianCalendar calendar = new GregorianCalendar();
        JSlider yearSlider = new JSlider(JSlider.HORIZONTAL, calendar.get(Calendar.YEAR )-3, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR)-3);
        yearSlider.setMajorTickSpacing(1);
        yearSlider.setMinorTickSpacing(1);
        yearSlider.setPaintTicks(true);
        yearSlider.setPaintLabels(true);
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        int currYear = calendar.get(Calendar.YEAR);
        int max=12;


        JSlider monthSlider = new JSlider(JSlider.HORIZONTAL, 1, 12, 1);

        String[] months = (new DateFormatSymbols()).getShortMonths();
        Hashtable hashTable = new Hashtable(12);
        for (int i = 0; i < 12; i++)
            hashTable.put(i+1, new JLabel(months[i],
                    JLabel.CENTER));
        monthSlider.setLabelTable(hashTable);
        monthSlider.setPaintLabels(true);
        monthSlider.setMajorTickSpacing(1);
        monthSlider.setPaintTicks(true);
        monthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
               int month = monthSlider.getValue();
               int year = yearSlider.getValue();
                chart = new MetricsChart(selectedMetrics, selectedThresholds, selected.getBelongingPackage() + "." + selected.getName(), project.getPath(), month, year);
                mainPanel.remove(4);
                mainPanel.add(chart.getPanel());
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        yearSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int year = yearSlider.getValue();
                int month = monthSlider.getValue();
                chart = new MetricsChart(selectedMetrics, selectedThresholds, selected.getBelongingPackage() + "." + selected.getName(), project.getPath(), month, year);
                mainPanel.remove(4);
                mainPanel.add(chart.getPanel());
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Show smells metrics evolution starting from:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(monthSlider);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(yearSlider);

        return panel;
    }
}
