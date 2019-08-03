package init;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import data.TestProjectCKInfo;
import gui.CKFrame;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import processor.CKMetricsProcessor;


import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class PluginInit extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project proj = e.getData(PlatformDataKeys.PROJECT);
        String projectFolder = proj.getBasePath();
        File root = new File(projectFolder);
        String srcPath = root.getAbsolutePath() + "/src";
        String mainPath = srcPath + "/main";
        String testPath = srcPath + "/test";
        File project = new File(srcPath);
        File test = new File(testPath);
        if ((test.isDirectory()) && (!test.isHidden())) {
            try {
                Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
                CKMetricsProcessor CKProcessor = new CKMetricsProcessor();
                TestProjectCKInfo projectCKInfo = CKProcessor.calculate(testPackages, proj);
                JFrame ckShow = new CKFrame(projectCKInfo);
                ckShow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ckShow.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else JOptionPane.showMessageDialog(null, "STRUTTURA DIRECTORIES NON CORRETTA");


    }


}

