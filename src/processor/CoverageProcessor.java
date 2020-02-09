package processor;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.trilead.ssh2.StreamGobbler;
import data.ClassCKInfo;
import data.ClassCoverageInfo;
import data.TestProjectAnalysis;
import init.PluginInit;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.metrics.TestSmellMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.CoberturaHTMLParser;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.apache.commons.io.FileUtils;
import utils.CommandOutput;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class CoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");

    public static Vector<ClassCoverageInfo> calculate(File root, ArrayList<ClassBean> classes, Vector<PackageBean> testPackages, TestProjectAnalysis proj, boolean isMaven) {
        try {
            double projectTotalLines = 0;
            double projectCoveredLines = 0;
            double projectTotalBranches = 0;
            double projectCoveredBranches = 0;
            String configDir = System.getProperty("user.home") + "\\.temevi";
            String pluginPath = PathManager.getPluginsPath() + "\\TestFactorsPlugin\\lib";
            String jacocoCli = pluginPath + "\\jacococli.jar";
            String jacocoAgent = pluginPath + "\\jacocoagent.jar";
            String notJbr = PluginInit.getJAVALOCATION();
            Vector<ClassCoverageInfo> classCoverageInfo = new Vector<ClassCoverageInfo>();
            TestSmellMetrics testSmellMetrics = new TestSmellMetrics();
            TestMutationUtilities utilities = new TestMutationUtilities();
            double lineCoverage = -1.0d;
            double branchCoverage = -1.0d;
            double assertionDensity = Double.NaN;
            Hashtable<String, Integer> isGreenSuite = new Hashtable<>();

            //  String buildPath = root.getAbsolutePath() + "\\out";
            //   String destination = root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            //    String testPath = root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            File buildPath = new File(root.getAbsolutePath() + "\\target");
            String destination = buildPath.getAbsolutePath() + "\\classes";
            String testPath = buildPath.getAbsolutePath() + "\\test-classes";
            String cmd = "java -jar " + jacocoCli + " instrument " + destination + " --dest " + buildPath + "\\instrumented";

            LOGGER.info("START COBERTURA INSTRUMENT");
            LOGGER.info(cmd);
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmd);
            String s;
            String output = "";
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdOut.readLine()) != null) {
                output += s;
            }
            p.waitFor();
            LOGGER.info("END COBERTURA INSTRUMENT");
            LOGGER.info(System.getProperty("user.dir"));
            //   LOGGER.info("" + classes.size());
            for (PackageBean packageBean : testPackages) {
                for (ClassBean testSuite : packageBean.getClasses()) {

                    //   ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                    //  if (testSuite != null) {


                    cmd = "\"" + notJbr + "\" -cp " + jacocoAgent + ";" + configDir + ";" + pluginPath + "\\*;"
                            + buildPath + "\\instrumented;" + destination + ";" + testPath +
                            " org.junit.runner.JUnitCore " + testSuite.getBelongingPackage() + "." + testSuite.getName();
                    LOGGER.info("START JUNIT TESTS");
                    LOGGER.info(cmd);
                    p = rt.exec(cmd);
                    output = "";
                    stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((s = stdOut.readLine()) != null) {
                        output += s;
                    }
                    LOGGER.info(output);
                    BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    output = "";
                    while ((s = stdErr.readLine()) != null) {
                        output += s;
                    }


                    p.waitFor();
                    LOGGER.info("END JUNIT TESTS");

                }
            }


            cmd = "java -jar C:\\jacoco\\lib\\jacococli.jar report " + configDir + "\\jacoco.exec" + " --classfiles " + destination + " --csv " + configDir + "\\coverage.csv";
            LOGGER.info("START COBERTURA REPORT");
            LOGGER.info(cmd);
            rt = Runtime.getRuntime();
            p = rt.exec(cmd);
            output = "";
            stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdOut.readLine()) != null) {
                output += s;
            }
            p.waitFor();
            LOGGER.info("END COBERTURA REPORT");

            cmd = "java -jar C:\\jacoco\\lib\\jacococli.jar report " + configDir + "\\jacoco.exec" + " --classfiles " + destination + " --html " + configDir + "\\htmlCoverage";
            LOGGER.info("START COBERTURA REPORT");
            LOGGER.info(cmd);
            rt = Runtime.getRuntime();
            p = rt.exec(cmd);
            output = "";
            stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdOut.readLine()) != null) {
                output += s;
            }
            p.waitFor();
            LOGGER.info("END COBERTURA REPORT");

            for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                if (testSuite != null) {

                    String line = "";
                    String cvsSplitBy = ",";
                    String reportPath = configDir + "\\coverage.csv";
                    File file = new File(reportPath);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    br.readLine();
                    line = br.readLine();
                    String[] data = line.split(cvsSplitBy);
                    while ((line = br.readLine()) != null) {
                        data = line.split(cvsSplitBy);
                        if (data[1].equalsIgnoreCase(productionClass.getBelongingPackage()) && data[2].equalsIgnoreCase(productionClass.getName())) {
                            double coveredLines = Double.parseDouble(data[8]);
                            double missedLines = Double.parseDouble(data[7]);
                            double totalLines = coveredLines + missedLines;
                            double cov = coveredLines / totalLines;
                            lineCoverage = Math.round(cov * 100);
                            lineCoverage = lineCoverage / 100;
                            double coveredBranches = Double.parseDouble(data[6]);
                            double missedBranches = Double.parseDouble(data[5]);
                            double totalBranches = coveredBranches + missedBranches;
                            if(totalBranches!=0) {
                                double branchCov = coveredBranches / totalBranches;
                                branchCoverage = Math.round(branchCov * 100);
                                branchCoverage = branchCoverage / 100;
                                projectTotalLines += totalLines;
                                projectCoveredLines += coveredLines;
                                projectTotalBranches += totalBranches;
                                projectCoveredBranches += coveredBranches;
                            }
                            else
                                branchCoverage=-1.0d;
                        }
                    }

                    ClassCoverageInfo coverageInfo = new ClassCoverageInfo();
                    //      coverageInfo.setBelongingPackage(testSuite.getBelongingPackage());
                    coverageInfo.setName(testSuite.getName());
                    coverageInfo.setLineCoverage(lineCoverage);
                    coverageInfo.setBranchCoverage(branchCoverage);
                    //     coverageInfo.setProductionClass(productionClass.getBelongingPackage() + "." + productionClass.getName());

                       /* if (isGreenSuite.get(testSuite.getName()) == 1) {
                            LOGGER.info("LA SUITE E' GREEN");
                            coverageInfo = MutationCoverageProcessor.calculate(coverageInfo, productionClass, root, testPackages, proj);
                            projectTotalLines += coverageInfo.getTotalLines();
                            projectCoveredLines += coverageInfo.getCoveredLines();
                        //    mutatedTotalLines += coverageInfo.getMutatedLines();
                        //    coveredMutatedLines += coverageInfo.getCoveredMutatedLines();
                        } else {*/

                    //   }


                    //    LOGGER.info(coverageInfo.toString());
                    int asserts = TestSmellMetrics.getNumberOfAsserts(testSuite);
                    int t_loc = CKMetrics.getLOC(testSuite);
                    double locdouble = (double) t_loc;
                    //     LOGGER.info("asserts:" + asserts);
                    double assertsnr = (double) asserts;
                    double density = (assertsnr / locdouble) * 100;
                    assertionDensity = (double) Math.round(density) / 100;
                    coverageInfo.setAssertionDensity(assertionDensity);
                    classCoverageInfo.add(coverageInfo);
                }
            }

            //CLEANUP

            FileUtils.deleteDirectory(new File(buildPath + "\\instrumented"));



            LOGGER.info("project coveredlines: " + projectCoveredLines);
            LOGGER.info("project totallines: " + projectTotalLines);
            double projectLineCov = projectCoveredLines / projectTotalLines;
            double projectLineCov100 = Math.round(projectLineCov * 100);
            projectLineCov100 = projectLineCov100 / 100;
            proj.setLineCoverage(projectLineCov100);

            double projectBranchCov = projectCoveredBranches / projectTotalBranches;
            double projectBranchCov100 = Math.round(projectBranchCov * 100);
            projectBranchCov100 = projectBranchCov100 / 100;
            proj.setBranchCoverage(projectBranchCov100);
            return classCoverageInfo;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }

    }
}