package gui;

import data.ClassCKInfo;
import data.TestProjectCKInfo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class CKFrame extends JFrame {
    private TestProjectCKInfo projectCKInfo;


    public CKFrame(TestProjectCKInfo projectCKInfo) throws HeadlessException {
        this.projectCKInfo = projectCKInfo;
        setTitle("CKMetrics");
        setPreferredSize(new Dimension(600, 500));

        pack();
        setLocationRelativeTo(null);
        add(createProjectPanel(), BorderLayout.NORTH);
        add(createClassesPanel(), BorderLayout.CENTER);
    }


    public JPanel createProjectPanel(){
        JPanel panel = new JPanel(new GridLayout(5,1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel name = new JLabel("Project name: " + projectCKInfo.getName());
        JLabel loc = new JLabel("LOC: " + projectCKInfo.getLoc());
        JLabel nom = new JLabel("NOM: " + projectCKInfo.getNom());
        JLabel rfc = new JLabel("RFC: " + projectCKInfo.getRfc());
        JLabel wmc = new JLabel("WMC: " + projectCKInfo.getWmc());
        panel.add(name);
        panel.add(loc);
        panel.add(nom);
        panel.add(rfc);
        panel.add(wmc);
        return panel;
    }

    public JPanel createClassesPanel(){
        int numberOfClasses = projectCKInfo.getTestClassesNumber();
        Vector<ClassCKInfo> classCKInfo = projectCKInfo.getClassesInfo();
        JPanel main = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel = new JPanel(new GridLayout(numberOfClasses+1, 6));

        panel.add(new JLabel("Class name"));
        panel.add(new JLabel());
        panel.add(new JLabel("LOC"));
        panel.add(new JLabel("NOM"));
        panel.add(new JLabel("RFC"));
        panel.add(new JLabel("WMC"));
        for(ClassCKInfo classInfo : classCKInfo){
            panel.add(new JLabel(classInfo.getName()));
            panel.add(new JLabel());
            panel.add(new JLabel("" + classInfo.getLoc()));
            panel.add(new JLabel("" + classInfo.getNom()));
            panel.add(new JLabel("" + classInfo.getRfc()));
            panel.add(new JLabel("" + classInfo.getWmc()));

        }
        main.add(panel);
        return main;

    }


}
