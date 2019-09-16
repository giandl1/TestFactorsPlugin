package gui;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import net.miginfocom.layout.Grid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Comparator;
import java.util.Vector;

public class AnalysisResultsUI extends JFrame {
    private TestProjectAnalysis project;
    private JPanel eastPanel;

    public AnalysisResultsUI(TestProjectAnalysis project) throws HeadlessException {
        this.project = project;
        setPreferredSize(new Dimension(700,500));
        JPanel grid = new JPanel(new GridLayout(1,3));

        add(projectPanel(), BorderLayout.NORTH);
       // add(classPanel(), BorderLayout.WEST);
        grid.add(classPanel());
        grid.add(new JLabel());
        eastPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        eastPanel.setBorder(new EmptyBorder(10,10,10,10));
        grid.add(eastPanel);
        add(grid);
        pack();
        setLocationRelativeTo(null);
    }

    public JPanel classPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10,10,10,10));
      //  panel.setLayout(new GridLayout(1,1));
        Vector<TestClassAnalysis> classAnalysis = project.getClassAnalysis();
        classAnalysis.sort(new Comparator<TestClassAnalysis>() {
            @Override
            public int compare(TestClassAnalysis o1, TestClassAnalysis o2) {
                return o1.getSmellsThreshold() > o2.getSmellsThreshold() ? -1 :(o1.getSmellsThreshold() < o2.getSmellsThreshold() ? 1 : 0);
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
                    if(colorChange.getSmellsThreshold()>5)
                        renderer.setForeground(Color.RED);
                }
                return renderer;
            }
        });
        classPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        classPanel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    if(!listSelectionEvent.getValueIsAdjusting()) {
                        TestClassAnalysis selected = classPanel.getSelectedValue();
                        if(selected!=null){
                            eastPanel.removeAll();
                            eastPanel.revalidate();
                            eastPanel.repaint();
                       eastPanel.add(new JLabel("Class name:" + selected.getName()));
                        System.out.println("Class name:" + selected.getName());

                        }}
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
    }

    public JPanel projectPanel(){
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
}
