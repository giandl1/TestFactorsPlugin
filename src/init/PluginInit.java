package init;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiDocumentManager;
import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import data.*;
import gui.AnalysisResultsUI;
import gui.PluginInitGUI;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import processor.*;
import processor.CKMetricsProcessor;
import utils.CommandOutput;
import utils.VectorFind;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class PluginInit extends AnAction {
    private static final Logger LOGGER = Logger.getInstance("global");
   /* boolean ok;


    @Override
    public void actionPerformed(AnActionEvent e) {
        ok=false;
        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();

        new Thread() {
            public void run() {
                JFrame frame = new JFrame("Performing analysis");
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.add(swingProgressBar(), BorderLayout.CENTER);
                frame.setPreferredSize(new Dimension(400,150));
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                Project proj = e.getData(PlatformDataKeys.PROJECT);
                String projectFolder = proj.getBasePath();
                File root = new File(projectFolder);
                String srcPath = root.getAbsolutePath() + "/src";
                String mainPath = srcPath + "/main";
                String testPath = srcPath + "/test";
                boolean isMaven = false;
                for (File file : root.listFiles()) {
                    if (file.isFile() && file.getName().equalsIgnoreCase("pom.xml"))
                        isMaven = true;
                }

                File project = new File(srcPath);
                projectAnalysis.setName(proj.getName());
                projectAnalysis.setPath(proj.getBasePath());
                Vector<TestClassAnalysis> classAnalysis = new Vector<>();
                File test = new File(testPath);
                if ((test.isDirectory()) && (!test.isHidden())) {
                    try {
                        Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
                        Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(mainPath);
                        CKMetricsProcessor CKProcessor = new CKMetricsProcessor();
                        //  Vector<ClassCKInfo> ckInfos = CKProcessor.calculate(packages,testPackages, projectAnalysis);
                        ArrayList<ClassBean> classes = new TestMutationUtilities().getClasses(packages);
                   //     Vector<ClassCoverageInfo> coverageInfos = CoverageProcessor.calculate(root, classes, testPackages, projectAnalysis, isMaven);
                        //    Vector<ClassTestSmellsInfo> classTestSmellsInfos = SmellynessProcessor.calculate(root, packages, testPackages, proj);
                          Vector<FlakyTestsInfo> flakyInfos = FlakyTestsProcessor.calculate(root, packages, testPackages, projectAnalysis);
                 //     for (ClassBean productionClass : classes) {
                     //     ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                     //     if (testSuite != null) {
                     //         MutationCoverageProcessor.calculate(productionClass, root, testSuite, projectAnalysis, isMaven);
                     //     }
                  //    }


                                TestClassAnalysis analysis = new TestClassAnalysis();
                                analysis.setName(testSuite.getName());
                                analysis.setBelongingPackage(testSuite.getBelongingPackage());
                                analysis.setProductionClass(productionClass.getBelongingPackage() + "." + productionClass.getName());
                                analysis.setCkMetrics(CKProcessor.calculate(testSuite, projectAnalysis));
                                analysis.setCoverage(VectorFind.findCoverageInfo(coverageInfos, analysis.getName()));
                                analysis.setSmells(SmellynessProcessor.calculate(testSuite, productionClass, packages, projectAnalysis));
                                //  analysis.setFlakyTests(VectorFind.findFlakyInfo(flakyInfos, analysis.getName()));
                                classAnalysis.add(analysis);
                            }
                        }
                        projectAnalysis.setClassAnalysis(classAnalysis);
                        ReportProcessor.saveReport(projectAnalysis);
                        ok=true;
                        if(ok) {
                            LOGGER.info("OPENING FRAME");
                            frame.setVisible(false);
                            JFrame ckShow = new AnalysisResultsUI(projectAnalysis);
                            ckShow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            ckShow.setVisible(true);
                        }

                    } catch (Exception ex) {
                        LOGGER.info(ex.toString());
                    }
                }
                else JOptionPane.showMessageDialog(null, "STRUTTURA DIRECTORIES NON CORRETTA");
            }
        }.start();


*/


    @Override
    public void actionPerformed(AnActionEvent e) {
        String userDir = System.getProperty("user.home");

        String pluginFolder = userDir + "\\.temevi";
        File config = new File(pluginFolder + "\\default_config.ini");
        File jacocoProp = new File(pluginFolder + "\\jacoco-agent.properties");
        if (!config.exists()) {
            String output = "[NONDA]" +
                    "\nname=Number of Non-Documented Assertions " +
                    "\ndescription=Number of assert statements without a description " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=ASSERTION_ROULETTE " +
                    "\n[APCMC]" +
                    "\nname=Average Production Class Methods Calls " +
                    "\ndescription=Number of production class' methods calls in the test suite, divided by the number of test methods " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=EAGER_TEST " +
                    "\n[MEXR] " +
                    "\nname=Methods using External Resources " +
                    "\ndescription=Number of external resources uses made by test methods" +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=MYSTERY_GUEST " +
                    "\n[NEXEA] " +
                    "\nname=Number of EXternal resources Existence Assumptions " +
                    "\ndescription=Number of assumptions made in test methods about the existence of external resources (e.g. Files, Database) " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=RESOURCE_OPTIMISM " +
                    "\n[GFMR]" +
                    "\nname=General Fixture Methods Rate " +
                    "\ndescription=The rate of test methods not using all the set-up variables defined " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=GENERAL_FIXTURE " +
                    "\n[MTOOR] " +
                    "\nname=Methods Testing Other Objects Rate " +
                    "\ndescription=The rate of methods testing objects which are different from the production class " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=INDIRECT_TESTING " +
                    "\n[TSEC] " +
                    "\nname=toString invocations in Equality Checks " +
                    "\ndescription=The number of toString invocations in equality checks " +
                    "\ndetectionThreshold=1.0 " +
                    "\nguardThreshold=3.0 " +
                    "\nbelongingSmells=SENSITIVE_EQUALITY";
            File plugin = new File(pluginFolder);
            plugin.mkdirs();
            FileUtility.writeFile(output, pluginFolder + "\\" + "default_config.ini");

        }
        if (!jacocoProp.exists()) {
            pluginFolder= pluginFolder.replace("\\", "\\\\");
            String output = "destfile = " + pluginFolder + "\\\\jacoco.exec";
            FileUtility.writeFile(output, pluginFolder + "\\" + "jacoco-agent.properties");
        }
        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
        Project proj = e.getData(PlatformDataKeys.PROJECT);
        String projectFolder = proj.getBasePath();
        File root = new File(projectFolder);
        String srcPath = root.getAbsolutePath() + "/src";
        String mainPath = srcPath + "/main";
        String testPath = srcPath + "/test";
        boolean isMaven = false;
        for (File file : root.listFiles()) {
            if (file.isFile() && file.getName().equalsIgnoreCase("pom.xml"))
                isMaven = true;
        }

        File project = new File(srcPath);
        projectAnalysis.setName(proj.getName());
        projectAnalysis.setPath(proj.getBasePath());
        Vector<TestClassAnalysis> classAnalysis = new Vector<>();
        File test = new File(testPath);
        if ((test.isDirectory()) && (!test.isHidden())) {
            try {
                Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
                Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(mainPath);
                PluginInitGUI initGUI = new PluginInitGUI(packages, testPackages, root, proj);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


}




