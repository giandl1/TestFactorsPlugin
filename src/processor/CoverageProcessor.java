package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import cucumber.api.java.tr.Ve;
import data.ClassCKInfo;
import data.ClassCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.metrics.TestSmellMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.CoberturaHTMLParser;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;


public class CoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");
    public static void calculate(File root, Vector<PackageBean> packages, Vector<PackageBean> testPackages, TestProjectAnalysis proj) {
        try {
            double projectTotalLines = 0;
            double projectCoveredLines = 0;
            double mutatedTotalLines = 0;
            double coveredMutatedLines = 0;
            Vector<ClassCoverageInfo> classCoverageInfo = new Vector<ClassCoverageInfo>();
            TestSmellMetrics testSmellMetrics = new TestSmellMetrics();
            TestMutationUtilities utilities = new TestMutationUtilities();
            double lineCoverage = 0;
            double branchCoverage = 0;
            double assertionDensity = Double.NaN;
            Hashtable<String, Integer> isGreenSuite = new Hashtable<>();

            ArrayList<ClassBean> classes = utilities.getClasses(packages);
            String buildPath = root.getAbsolutePath() + "\\out";
            String destination = root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            String testPath = root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            String cmd = "\"cmd.exe\", \"/c\", C:\\cobertura\\cobertura-instrument.bat --destination " + buildPath
                    + "\\instrumented" + " " + destination + " --datafile " + buildPath + "\\cobertura.ser";
            LOGGER.info("START COBERTURA INSTRUMENT");
            LOGGER.info(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
           LOGGER.info("END COBERTURA INSTRUMENT");
         //   LOGGER.info("" + classes.size());
            for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                if (testSuite!=null) {
                    String s = "";
                    String output = "";


                    Runtime rt = Runtime.getRuntime();
                    cmd = "java -cp C:\\cobertura\\cobertura-2.1.1.jar;C:\\cobertura\\slf4j.jar;C:\\cobertura\\junit\\junit.jar;C:\\cobertura\\junit\\hamcrest.jar;"
                            + buildPath + "\\instrumented;" + destination + ";" + testPath
                            + " -Dnet.sourceforge.cobertura.datafile="
                            + buildPath + "\\cobertura.ser org.junit.runner.JUnitCore " + testSuite.getBelongingPackage() + "." + testSuite.getName();
                    LOGGER.info("START JUNIT TESTS");
                    LOGGER.info(cmd);

                    Process pr = rt.exec(cmd);
                    BufferedReader stdOut = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    while ((s = stdOut.readLine()) != null) {
                        output += s;
                    }
                    LOGGER.info("END JUNIT TESTS");
                    if (output.contains("FAILURE"))
                        isGreenSuite.put(testSuite.getName(), 0);
                    else
                        isGreenSuite.put(testSuite.getName(), 1);

                }
            }


            cmd = "\"cmd.exe\", \"/c\", C:\\cobertura\\cobertura-report.bat --format html --datafile " + buildPath + "\\cobertura.ser" + " --destination " + buildPath + "\\report" + " " + root.getAbsolutePath() + "\\src\\main\\java";
          //  LOGGER.info("START COBERTURA REPORT");
          //  LOGGER.info(cmd);

            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        //    LOGGER.info("END COBERTURA REPORT");

            for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                if (testSuite!=null) {

                    String coberturaPath = buildPath + "\\report\\" + productionClass.getBelongingPackage() + "." + productionClass.getName() + ".html";
                CoberturaHTMLParser parser = new CoberturaHTMLParser(coberturaPath);
                lineCoverage = parser.getLineCoverage();
                branchCoverage = parser.getBranchCoverage();
                ClassCoverageInfo coverageInfo = new ClassCoverageInfo();
                coverageInfo.setBelongingPackage(testSuite.getBelongingPackage());
                coverageInfo.setName(testSuite.getName());
                coverageInfo.setLineCoverage(lineCoverage);
                coverageInfo.setBranchCoverage(branchCoverage);
                coverageInfo.setProductionClass(productionClass.getBelongingPackage() + "." + productionClass.getName());

                if(isGreenSuite.get(testSuite.getName()) == 1) {
                    LOGGER.info("LA SUITE E' GREEN");
                    coverageInfo = MutationCoverageProcessor.calculate(coverageInfo, productionClass, root, testPackages, proj);
                    projectTotalLines += coverageInfo.getTotalLines();
                    projectCoveredLines += coverageInfo.getCoveredLines();
                    mutatedTotalLines += coverageInfo.getMutatedLines();
                    coveredMutatedLines += coverageInfo.getCoveredMutatedLines();
                }
                else{
                    coverageInfo.setMutationCoverage(-1);
                    coverageInfo.setMutatedLines(0);
                    coverageInfo.setCoveredLines(0); //PLACEHOLDER, DA MODIFICARE
                    coverageInfo.setCoveredMutatedLines(0);
                }


            //    LOGGER.info(coverageInfo.toString());
                int asserts = TestSmellMetrics.getNumberOfAsserts(testSuite);
                int t_loc = CKMetrics.getLOC(testSuite);
                double locdouble = (double) t_loc;
           //     LOGGER.info("asserts:" + asserts);
                double assertsnr = (double) asserts;
                double density = (assertsnr/locdouble)*100;
                assertionDensity = (double) Math.round(density)/100;
                coverageInfo.setAssertionDensity(assertionDensity);
                classCoverageInfo.add(coverageInfo);
                }
            }
            //CLEANUP

            FileUtils.deleteDirectory(new File(buildPath + "\\instrumented"));
           FileUtils.deleteQuietly(new File(buildPath + "\\cobertura.ser"));
            proj.setClassCoverageInfo(classCoverageInfo);
            LOGGER.info("project coveredlines: " + projectCoveredLines);
            LOGGER.info("project totallines: " + projectTotalLines);

            double proj_lineCoverage = (double) Math.round((projectCoveredLines/projectTotalLines)*100)/100;
            double proj_mutationCoverage = (double) Math.round((coveredMutatedLines/mutatedTotalLines)*100)/100;

            proj.setLineCoverage(proj_lineCoverage);
            proj.setMutationCoverage(proj_mutationCoverage);
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getPath() + "\\reports\\coverage";
            String output = "project;test-suite;production-class;lineCoverage;branchCoverage;mutationCoverage;assertionDensity\n";
         output+= proj.getName() + ";" + "null;" + "null;" + proj.getLineCoverage() + ";" + "null" + ";" + proj.getMutationCoverage() + ";null"  +"\n";
            for(ClassCoverageInfo coverageInfo : classCoverageInfo){
                output+=proj.getName() + ";" + coverageInfo.getBelongingPackage() + "." + coverageInfo.getName() + ";" + coverageInfo.getProductionClass() + ";" + coverageInfo.getLineCoverage() +
                        ";" + coverageInfo.getBranchCoverage() + ";" + coverageInfo.getMutationCoverage() + ";" + coverageInfo.getAssertionDensity() + "\n";
            }
            File out = new File(outputDir);
            out.mkdirs();
            FileUtility.writeFile(output, outputDir + "\\" + fileName);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

    }
}