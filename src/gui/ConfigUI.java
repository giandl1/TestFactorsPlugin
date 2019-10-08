package gui;

import com.intellij.ui.components.JBScrollPane;
import config.ConfigUtils;
import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class ConfigUI extends JFrame{
    private ArrayList<JTextField> arThresholds;
    private ArrayList<JTextField> etThresholds;
    private ArrayList<JTextField> ltThresholds;
    private ArrayList<JTextField> seThresholds;
    private ArrayList<JTextField> roThresholds;
    private ArrayList<JTextField> gfThresholds;
    private ArrayList<JTextField> ftoThresholds;
    private ArrayList<JTextField> itThresholds;
    private ArrayList<JTextField> mgThresholds;
    private TestSmellMetricsThresholdsList metricsList;
    private JButton save;
    private JButton restore;
    private JButton exit;
    private File default_conf;
    private File conf;
    private String projdir;
    private JPanel gridPanel;
   // private SmellsThresholds thresholds;


    public ConfigUI(String projdir) throws HeadlessException {
        arThresholds = new ArrayList<>();
        etThresholds = new ArrayList<>();
        ltThresholds = new ArrayList<>();
        seThresholds = new ArrayList<>();
        roThresholds = new ArrayList<>();
        gfThresholds = new ArrayList<>();
        ftoThresholds = new ArrayList<>();
        itThresholds = new ArrayList<>();
        mgThresholds = new ArrayList<>();

        gridPanel = new JPanel();

        this.projdir=projdir;
        this.setTitle("Metrics Thresholds Configuration");
        default_conf = new File(projdir + "\\default_config.ini");
        conf = new File(projdir + "\\config.ini");
       /*if(!default_conf.exists()) {
            thresholds = new SmellsThresholds(1,1,1,1,1,1,1,1,1);
            new ConfigUtils().writeThresholds(new File(projdir + "\\default_config.ini"), thresholds);
        }*/
       if(conf.exists())
            metricsList = new ConfigUtils().readThresholds(conf);
       else
            metricsList = new ConfigUtils().readThresholds(default_conf);



        for(TestSmellMetricThresholds metric : metricsList.getArMetrics()) {
            JTextField yellow = new JTextField(4);
            arThresholds.add(yellow);
            JTextField red = new JTextField(4);
            arThresholds.add(red);
        }

        for(TestSmellMetricThresholds metric : metricsList.getEtMetrics()){
            JTextField yellow = new JTextField(4);
            etThresholds.add(yellow);
            JTextField red = new JTextField(4);
            etThresholds.add(red);
        }

        for(TestSmellMetricThresholds metric : metricsList.getSeMetrics()){
            JTextField yellow = new JTextField(4);
            seThresholds.add(yellow);
            JTextField red = new JTextField(4);
            seThresholds.add(red);
        }

        for(TestSmellMetricThresholds metric : metricsList.getRoMetrics()){
            JTextField yellow = new JTextField(4);
            roThresholds.add(yellow);
            JTextField red = new JTextField(4);
            roThresholds.add(red);
        }
        for(TestSmellMetricThresholds metric : metricsList.getFtoMetrics()){
            JTextField yellow = new JTextField(4);
            ftoThresholds.add(yellow);
            JTextField red = new JTextField(4);
            ftoThresholds.add(red);
        }
        for(TestSmellMetricThresholds metric : metricsList.getMgMetrics()){
            JTextField yellow = new JTextField(4);
            mgThresholds.add(yellow);
            JTextField red = new JTextField(4);
            mgThresholds.add(red);
        }
        for(TestSmellMetricThresholds metric : metricsList.getGfMetrics()){
            JTextField yellow = new JTextField(4);
            gfThresholds.add(yellow);
            JTextField red = new JTextField(4);
            gfThresholds.add(red);
        }
        for(TestSmellMetricThresholds metric : metricsList.getLtMetrics()){
            JTextField yellow = new JTextField(4);
            ltThresholds.add(yellow);
            JTextField red = new JTextField(4);
            ltThresholds.add(red);
        }
        for(TestSmellMetricThresholds metric : metricsList.getItMetrics()){
            JTextField yellow = new JTextField(4);
            itThresholds.add(yellow);
            JTextField red = new JTextField(4);
            itThresholds.add(red);
        }
        setSoglieText();
       // System.out.println(arThresholds.get(0).getText());
        save = new JButton("Save");
        restore = new JButton("Restore default");
        exit = new JButton("Exit");
        setPreferredSize(new Dimension(800,600));
        add(createPanel(), BorderLayout.CENTER);
       add(buttonPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }


    public JScrollPane createPanel(){
        Border margin = new EmptyBorder(10,40,10,10);
        JPanel extPanel = new JPanel();
        extPanel.setBorder(margin);
        extPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        int size = metricsList.getArMetrics().size() + metricsList.getFtoMetrics().size() + metricsList.getEtMetrics().size()
                + metricsList.getGfMetrics().size() + metricsList.getItMetrics().size() + metricsList.getLtMetrics().size() + metricsList.getMgMetrics().size() +
                metricsList.getRoMetrics().size() + metricsList.getSeMetrics().size();
        gridPanel.setLayout(new GridLayout((size*4)+16+(size),1));
        JScrollPane scrollPane = new JBScrollPane(extPanel);
        gridPanel.add(titlePanel("Assertion Roulette"));
        addMetricPanel(metricsList.getArMetrics(), arThresholds);
        gridPanel.add(new MyLine());

        // gridPanel.add(new JPanel().add(new MyLine()));
        gridPanel.add(titlePanel("Eager Test"));
        addMetricPanel(metricsList.getEtMetrics(), etThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("Lazy Test"));
        addMetricPanel(metricsList.getLtMetrics(), ltThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("Mystery Guest"));
        addMetricPanel(metricsList.getMgMetrics(), mgThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("Resource Optimism"));
        addMetricPanel(metricsList.getRoMetrics(), roThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("General Fixture"));
        addMetricPanel(metricsList.getGfMetrics(), gfThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("Sensitive Equality"));
        addMetricPanel(metricsList.getSeMetrics(), seThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("For Testers Only"));
        addMetricPanel(metricsList.getFtoMetrics(), ftoThresholds);
        gridPanel.add(new MyLine());

        gridPanel.add(titlePanel("Indirect Testing"));
        addMetricPanel( metricsList.getItMetrics(), itThresholds);



        extPanel.add(gridPanel);
       //    scrollPane.add(arPanel);
      //  panel.add(scrollPane);

        return scrollPane;

    }

    private JPanel buttonPanel(){
        JPanel panel = new JPanel();
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int i = 0;
                    for(TestSmellMetricThresholds metric : metricsList.getArMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(arThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(arThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getEtMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(etThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(etThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getMgMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(mgThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(mgThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getRoMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(roThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(roThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getFtoMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(ftoThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(ftoThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getItMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(itThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(itThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getLtMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(ltThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(ltThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getGfMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(gfThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(gfThresholds.get(i+1).getText()));
                        i+=2;
                    }
                    i=0;
                    for(TestSmellMetricThresholds metric : metricsList.getSeMetrics()){
                        metric.setYellowThreshold(Double.parseDouble(seThresholds.get(i).getText()));
                        metric.setRedThreshold(Double.parseDouble(seThresholds.get(i+1).getText()));
                        i+=2;
                    }

                    new ConfigUtils().writeThresholds(new File(projdir+"\\config.ini"), metricsList);
                } catch(Exception e){
                    JOptionPane.showMessageDialog(panel, "ONLY INT VALUES ARE ALLOWED");
                }
            }
        });
        panel.add(save);
        restore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    metricsList = new ConfigUtils().readThresholds(default_conf);
                    setSoglieText();
                    FileUtils.forceDelete(conf);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        panel.add(restore);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        panel.add(exit);
        return panel;
    }

    private JPanel valuePanel(JTextField field, String label){
        JPanel valuePanel = new JPanel(new FlowLayout());
      //  System.out.println(field.getText());
    //    valuePanel.add(new JLabel("" + nome+ ":  "));
        valuePanel.add(new JLabel("<html>" + label +":&nbsp;</html>"));
        valuePanel.add(field);
        return valuePanel;
    }

    private JPanel titlePanel(String title){
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       // Border border=BorderFactory.createLineBorder(Color.WHITE,1);
        JLabel label = new JLabel(title);
        label.setForeground(Color.YELLOW);

        titlePanel.add(label);
        return titlePanel;
    }

    private JPanel metricName(String name){
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Border border=BorderFactory.createLineBorder(Color.WHITE,1);
        JLabel label = new JLabel(name);
        titlePanel.add(label);
        return titlePanel;
    }

    private JPanel descrPanel(String description){
        JPanel descr = new JPanel(new FlowLayout(FlowLayout.LEFT));

         descr.add(new JLabel("<html>" + description + "</html>"));
       // descr.setForeground(Color.YELLOW);
        return descr;
    }

    private void addMetricPanel(Vector<TestSmellMetricThresholds> metrics, ArrayList<JTextField> thresholdsFields){

        int i=0;
        int j=0;
        for(TestSmellMetricThresholds metric : metrics) {
            j++;
            JTextField yellow = thresholdsFields.get(i);
            JTextField red = thresholdsFields.get(i+1);
            gridPanel.add(metricName(metric.getName()));
            JPanel setValues = new JPanel(new FlowLayout());
            setValues.add(valuePanel(yellow, "Guard Threshold"));
            setValues.add(new JLabel());
            setValues.add(valuePanel(red, "Critic Threshold"));
            gridPanel.add(setValues);
            gridPanel.add(descrPanel(metric.getDescription()));
            if(j!=metrics.size())
                gridPanel.add(new JLabel());
            i+=2;
        }
    }



    private void setSoglieText() {
        int i=0;
       for(TestSmellMetricThresholds metric : metricsList.getArMetrics()) {
           //JTextField yellow = new JTextField(4);
           JTextField yellow = arThresholds.get(i);
           JTextField red = arThresholds.get(i+1);
           yellow.setText("" + metric.getYellowThreshold());
         //  arThresholds.add(yellow);
        //   JTextField red = new JTextField(4);
           red.setText("" + metric.getRedThreshold());
           System.out.println("lol: " + red.getText());
            i+=2;
         //  arThresholds.add(red);
       }

        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getEtMetrics()){
      //      JTextField yellow = new JTextField(4);
            JTextField yellow = etThresholds.get(i);
            JTextField red = etThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
        //    etThresholds.add(yellow);
       //     JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            System.out.println("lol: " + red.getText());

            i+=2;
      //      etThresholds.add(red);
        }

        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getSeMetrics()){
         //   JTextField yellow = new JTextField(4);
            JTextField yellow = seThresholds.get(i);
            JTextField red = seThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //seThresholds.add(yellow);
           // JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            i+=2;
            //seThresholds.add(red);
        }
        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getRoMetrics()){
            //JTextField yellow = new JTextField(4);
            JTextField yellow = roThresholds.get(i);
            JTextField red = roThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //roThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            i+=2;
           // roThresholds.add(red);
        }
        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getFtoMetrics()){
            //JTextField yellow = new JTextField(4);
            JTextField yellow = ftoThresholds.get(i);
            JTextField red = ftoThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //ftoThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            //ftoThresholds.add(red);
            i+=2;
        }

        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getMgMetrics()){
            //JTextField yellow = new JTextField(4);
            JTextField yellow = mgThresholds.get(i);
            JTextField red = mgThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //mgThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            //mgThresholds.add(red);
            i+=2;
        }
        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getGfMetrics()){
            //JTextField yellow = new JTextField(4);

            JTextField yellow = gfThresholds.get(i);
            JTextField red = gfThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
           // gfThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            //gfThresholds.add(red);
            i+=2;
        }
        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getLtMetrics()){
            //JTextField yellow = new JTextField(4);
            JTextField yellow = ltThresholds.get(i);
            JTextField red = ltThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //ltThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            i+=2;
            //ltThresholds.add(red);
        }
        i=0;
        for(TestSmellMetricThresholds metric : metricsList.getItMetrics()){
            //JTextField yellow = new JTextField(4);
            JTextField yellow = itThresholds.get(i);
            JTextField red = itThresholds.get(i+1);
            yellow.setText("" + metric.getYellowThreshold());
            //itThresholds.add(yellow);
            //JTextField red = new JTextField(4);
            red.setText("" + metric.getRedThreshold());
            //itThresholds.add(red);
            i+=2;
        }

    }
}
