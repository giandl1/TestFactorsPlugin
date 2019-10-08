package gui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormatSymbols;
import java.util.*;

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
        setPreferredSize(new Dimension(1200,800));
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
                        if (colorChange.getSmells().isAffectedCritic())
                            renderer.setForeground(Color.RED);
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
                            classInfo.add(new JLabel("Belonging package: " + selected.getBelongingPackage()));
                            classInfo.add(new JLabel("Class name: " + selected.getName()));
                            classInfo.add(new JLabel("LOC: " + selected.getCkMetrics().getLoc()));
                            classInfo.add(new JLabel("NOM: " + selected.getCkMetrics().getNom()));
                            classInfo.add(new JLabel("WMC: " + selected.getCkMetrics().getWmc()));
                            classInfo.add(new JLabel("RFC: " + selected.getCkMetrics().getRfc()));
                            classInfo.add(new JLabel("Line Coverage: " + selected.getCoverage().getLineCoverage()));
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

      /*  classPanel.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof TestClassAnalysis) {
                    // Here value will be of the Type 'CD'
                    ((JLabel) renderer).setText(((TestClassAnalysis) value).getName());
                }
                return renderer;
            }
        }); */
      //  classPanel.setPreferredSize(new Dimension(150,300));

        JScrollPane scrollPane = new JBScrollPane(classPanel);
        scrollPane.setPreferredSize(new Dimension(150,300));
        panel.add(scrollPane);
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
        panel.add(new JLabel("LOC: " + project.getLoc() + "   NOM: " + project.getNom() + "   WMC: " + project.getWmc() + "   RFC:" + project.getRfc() + "   Line Coverage: " + project.getLineCoverage() + "         "));
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
