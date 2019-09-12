package gui;

import com.intellij.ui.components.JBScrollPane;
import config.ConfigUtils;
import config.SmellsThresholds;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConfigUI extends JFrame{
    private JTextField sogliaAR1;
    private JTextField sogliaAR2;
    private JTextField sogliaET1;
    private JTextField sogliaET2;
    private JTextField sogliaGF1;
    private JTextField sogliaGF2;
    private JTextField sogliaLT1;
    private JTextField sogliaLT2;
    private JTextField sogliaRO1;
    private JTextField sogliaRO2;
    private JTextField sogliaMG1;
    private JTextField sogliaMG2;
    private JTextField sogliaSE1;
    private JTextField sogliaSE2;
    private JTextField sogliaFTO1;
    private JTextField sogliaFTO2;
    private JTextField sogliaIT1;
    private JTextField sogliaIT2;
    private JButton save;
    private JButton restore;
    private JButton exit;
    private File default_conf;
    private File conf;
    private String projdir;
    private SmellsThresholds thresholds;


    public ConfigUI(String projdir) throws HeadlessException {
        sogliaAR1 = new JTextField(2);
        sogliaAR2 = new JTextField(2);
        sogliaET1 = new JTextField(2);
        sogliaET2 = new JTextField(2);
        sogliaGF1 = new JTextField(2);
        sogliaGF2 = new JTextField(2);
        sogliaLT1 = new JTextField(2);
        sogliaLT2 = new JTextField(2);
        sogliaRO1 = new JTextField(2);
        sogliaRO2 = new JTextField(2);
        sogliaMG1 = new JTextField(2);
        sogliaMG2 = new JTextField(2);
        sogliaSE1 = new JTextField(2);
        sogliaSE2 = new JTextField(2);
        sogliaFTO1 = new JTextField(2);
        sogliaFTO2= new JTextField(2);
        sogliaIT1= new JTextField(2);
        sogliaIT2 = new JTextField(2);
        this.projdir=projdir;
        default_conf = new File(projdir + "\\default_config.ini");
        conf = new File(projdir + "\\config.ini");
        if(!default_conf.exists()) {
            thresholds = new SmellsThresholds(1,1,1,1,1,1,1,1,1);
            new ConfigUtils().writeThresholds(new File(projdir + "\\default_config.ini"), thresholds);
        }
        if(default_conf.exists() && !conf.exists()){
            thresholds = new ConfigUtils().readThresholds(default_conf);
        }
        else if(conf.exists()){
            thresholds = new ConfigUtils().readThresholds(conf);
        }
        setSoglieText();
        save = new JButton("Save");
        restore = new JButton("Restore default");
        exit = new JButton("Exit");
        setPreferredSize(new Dimension(1680,600));
        add(createPanel());
        add(buttonPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }


    public JScrollPane createPanel(){
        Border margin = new EmptyBorder(10,40,10,10);
        JPanel extPanel = new JPanel();
        extPanel.setBorder(margin);
        extPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel gridPanel = new JPanel(new GridLayout(3,3));
        JScrollPane scrollPane = new JBScrollPane(extPanel);

        JPanel arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,100);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Assertion Roulette"));
        arPanel.add(valuePanel(sogliaAR1, "Max nr. of non-documented assertions"));
        arPanel.add(descrPanel("AR Smell is detected when more than max assertion <br/>statements without a message are found"));
        arPanel.add(valuePanel(sogliaAR2, "Soglia AR2"));
        arPanel.add(descrPanel("Descrizione soglia AR2"));
        gridPanel.add(arPanel);
     //   gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Eager Test"));
        arPanel.add(valuePanel(sogliaET1, "Soglia ET1"));
        arPanel.add(descrPanel("Descrizione soglia ET1 lunghezza variabile"));
        arPanel.add(valuePanel(sogliaET2, "Soglia ET2"));
        arPanel.add(descrPanel("Descrizione soglia ET2"));
        gridPanel.add(arPanel);
      //  gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Mystery Guest"));
        arPanel.add(valuePanel(sogliaMG1, "Max nr. of external resources in a test"));
        arPanel.add(descrPanel("MG Smell is detected when the number of external resources<br/> used in test methods is higher than the max allowed"));
        arPanel.add(valuePanel(sogliaMG2, "Soglia MG2"));
        arPanel.add(descrPanel("Descrizione soglia MG2"));
        gridPanel.add(arPanel);


       /* gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));*/

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,100);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Resource Optimism"));
        arPanel.add(valuePanel(sogliaRO1, "Max nr. of external resources existence assumptions"));
        arPanel.add(descrPanel("This smell occurs when a test method makes an optimistic assumption<br/>that more than max external resource utilized by the test method exists"));
        arPanel.add(valuePanel(sogliaRO2, "Soglia RO2"));
        arPanel.add(descrPanel("Descrizione soglia RO2"));
        gridPanel.add(arPanel);
      //  gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Sensitive Equality"));
        arPanel.add(valuePanel(sogliaSE1, "Max nr. of toString invocations"));
        arPanel.add(descrPanel("SE Smell is detected if there are more than<br/> max toString invocations within test methods"));
        arPanel.add(valuePanel(sogliaSE2, "Soglia SE2"));
        arPanel.add(descrPanel("Descrizione soglia SE2"));
        gridPanel.add(arPanel);
     //   gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("General Fixture"));
        arPanel.add(valuePanel(sogliaGF1, "Max nr. of non-used setup fields"));
        arPanel.add(descrPanel("GF Smell occurs if the number of unused fields instantiated<br/> within the setUp method is higher than the max allowed"));
        arPanel.add(valuePanel(sogliaGF2, "Soglia GF2"));
        arPanel.add(descrPanel("Descrizione soglia GF2"));
        gridPanel.add(arPanel);

       /* gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));*/

        arPanel = new JPanel(new GridLayout(5,1));
        arPanel.add(titlePanel("For Testers Only"));
        margin = new EmptyBorder(0,50,0,100);
        arPanel.setBorder(margin);
        arPanel.add(valuePanel(sogliaFTO1, "Soglia FTO1"));
        arPanel.add(descrPanel("Descrizione soglia FTO1 lunghezza variabile"));
        arPanel.add(valuePanel(sogliaFTO2, "Soglia FTO2"));
        arPanel.add(descrPanel("Descrizione soglia FTO2"));
        gridPanel.add(arPanel);
      //  gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Lazy Test"));
        arPanel.add(valuePanel(sogliaLT1, "PLACEHOLDER"));
        arPanel.add(descrPanel("PLACEHOLDER"));
        arPanel.add(valuePanel(sogliaLT2, "Soglia LT2"));
        arPanel.add(descrPanel("Descrizione soglia LT2"));
        gridPanel.add(arPanel);
      //  gridPanel.add(new JLabel(""));

        arPanel = new JPanel(new GridLayout(5,1));
        margin = new EmptyBorder(0,50,0,0);
        arPanel.setBorder(margin);
        arPanel.add(titlePanel("Indirect Testing"));
        arPanel.add(valuePanel(sogliaIT1, "Soglia IT1"));
        arPanel.add(descrPanel("Descrizione soglia IT1 lunghezza variabile"));
        arPanel.add(valuePanel(sogliaIT2, "Soglia IT2"));
        arPanel.add(descrPanel("Descrizione soglia IT2"));
        gridPanel.add(arPanel);



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
                    thresholds.setSogliaAR(Integer.parseInt(sogliaAR1.getText()));
                    thresholds.setSogliaMG(Integer.parseInt(sogliaMG1.getText()));
                    thresholds.setSogliaIT(Integer.parseInt(sogliaIT1.getText()));
                    thresholds.setSogliaSE(Integer.parseInt(sogliaSE1.getText()));
                    thresholds.setSogliaFTO(Integer.parseInt(sogliaFTO1.getText()));
                    thresholds.setSogliaLT(Integer.parseInt(sogliaLT1.getText()));
                    thresholds.setSogliaGF(Integer.parseInt(sogliaGF1.getText()));
                    thresholds.setSogliaET(Integer.parseInt(sogliaET1.getText()));
                    thresholds.setSogliaRO(Integer.parseInt(sogliaRO1.getText()));

                    new ConfigUtils().writeThresholds(new File(projdir + "\\config.ini"), thresholds);
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
                    thresholds = new ConfigUtils().readThresholds(default_conf);
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
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
    //    label.setBorder(border);
        titlePanel.add(label);
        return titlePanel;
    }

    private JPanel descrPanel(String description){
        JPanel descr = new JPanel(new FlowLayout(FlowLayout.LEFT));

         descr.add(new JLabel("<html>" + description + "</html>"));
       // descr.setForeground(Color.YELLOW);
        return descr;
    }

    private void setSoglieText(){
        sogliaAR1.setText("" +thresholds.getSogliaAR());
        sogliaET1.setText("" + thresholds.getSogliaET());
        sogliaMG1.setText("" + thresholds.getSogliaMG());
        sogliaSE1.setText("" + thresholds.getSogliaSE());
        sogliaFTO1.setText("" + thresholds.getSogliaFTO());
        sogliaLT1.setText("" + thresholds.getSogliaLT());
        sogliaGF1.setText("" + thresholds.getSogliaGF());
        sogliaIT1.setText("" + thresholds.getSogliaIT());
        sogliaRO1.setText("" + thresholds.getSogliaRO());

    }
}
