package config;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import gui.ConfigUI;

import javax.swing.*;

public class PluginConfig extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
       // TestSmellMetricsThresholdsList list = new ConfigFileHandler().readThresholds(new File("C:\\Users\\Psycho\\IdeaProjects\\ProgettoExample\\default_config.ini"));
        JFrame frame = new ConfigUI(e.getData(PlatformDataKeys.PROJECT).getBasePath());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
