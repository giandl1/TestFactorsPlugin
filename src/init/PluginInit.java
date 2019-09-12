package init;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import data.ClassTestSmellsInfo;
import data.TestProjectAnalysis;
import gui.CKFrame;
import gui.ConfigUI;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.apache.commons.io.FileUtils;
import processor.*;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
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
        String buildPath = root.getAbsolutePath() + "\\out";

        File project = new File(srcPath);
        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
        projectAnalysis.setName(proj.getName());
        projectAnalysis.setPath(proj.getBasePath());
        File test = new File(testPath);
        if ((test.isDirectory()) && (!test.isHidden())) {
            try {
                Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
                Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(mainPath);
               CKMetricsProcessor CKProcessor = new CKMetricsProcessor();
               CKProcessor.calculate(packages,testPackages, projectAnalysis);
                JFrame ckShow = new CKFrame(projectAnalysis);
               CoverageProcessor.calculate(root, packages, testPackages,projectAnalysis);
                ArrayList<ClassTestSmellsInfo> classTestSmellsInfos = SmellynessProcessor.calculate(root, packages, testPackages, proj);
                FlakyTestsProcessor.calculate(root, packages, testPackages, projectAnalysis);

                ckShow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ckShow.setVisible(true);


                  } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else JOptionPane.showMessageDialog(null, "STRUTTURA DIRECTORIES NON CORRETTA");


    }


}

