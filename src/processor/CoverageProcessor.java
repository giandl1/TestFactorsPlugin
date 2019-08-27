package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import cucumber.api.java.tr.Ve;
import data.ClassCKInfo;
import data.ClassCoverageInfo;
import data.ProjectCoverageInfo;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.metrics.TestSmellMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.CoberturaHTMLParser;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class CoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");
    public static ProjectCoverageInfo calculate(File root, Vector<PackageBean> packages, Vector<PackageBean> testPackages, Project proj) {
        try {
            ProjectCoverageInfo projectCoverageInfo = new ProjectCoverageInfo();
            projectCoverageInfo.setName(proj.getName());
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

            ArrayList<ClassBean> classes = utilities.getClasses(packages);
            String s;
            String buildPath = root.getAbsolutePath() + "\\out";
            String destination = root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            String testPath = root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            String cmd = "\"cmd.exe\", \"/c\", C:\\cobertura\\cobertura-instrument.bat --destination " + buildPath
                    + "\\instrumented" + " " + destination + " --datafile " + buildPath + "\\cobertura.ser";
            LOGGER.info("START COBERTURA INSTRUMENT");
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            LOGGER.info("END COBERTURA INSTRUMENT");
            LOGGER.info("" + classes.size());
            for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);




                Runtime rt = Runtime.getRuntime();
                cmd = "java -cp C:\\cobertura\\cobertura-2.1.1.jar;C:\\cobertura\\slf4j.jar;C:\\cobertura\\junit\\junit.jar;C:\\cobertura\\junit\\hamcrest.jar;"
                        + buildPath + "\\instrumented;" + destination + ";" + testPath
                        + " -Dnet.sourceforge.cobertura.datafile="
                        + buildPath + "\\cobertura.ser org.junit.runner.JUnitCore " + testSuite.getBelongingPackage() + "." + testSuite.getName();
                LOGGER.info("START JUNIT TESTS");
                LOGGER.info(cmd);

                Process pr = rt.exec(cmd);
                pr.waitFor();
                LOGGER.info("END JUNIT TESTS");

            }


            cmd = "\"cmd.exe\", \"/c\", C:\\cobertura\\cobertura-report.bat --format html --datafile " + buildPath + "\\cobertura.ser" + " --destination " + buildPath + "\\report" + " " + root.getAbsolutePath() + "\\src\\main\\java";
            LOGGER.info("START COBERTURA REPORT");
            LOGGER.info(cmd);

            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            LOGGER.info("END COBERTURA REPORT");

            for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                String coberturaPath = buildPath + "\\report\\" + productionClass.getBelongingPackage() + "." + productionClass.getName() + ".html";
                CoberturaHTMLParser parser = new CoberturaHTMLParser(coberturaPath);
                lineCoverage = parser.getLineCoverage();
                branchCoverage = parser.getBranchCoverage();
                ClassCoverageInfo coverageInfo = new ClassCoverageInfo();
                coverageInfo.setBelongingPackage(productionClass.getBelongingPackage());
                coverageInfo.setName(productionClass.getName());
                coverageInfo.setLineCoverage(lineCoverage);
                coverageInfo.setBranchCoverage(branchCoverage);
                coverageInfo = MutationCoverageProcessor.calculate(coverageInfo,productionClass,root, testPackages, proj);
                coverageInfo.setProductionClass(productionClass.getBelongingPackage() + "." + productionClass.getName());
                projectTotalLines+=coverageInfo.getTotalLines();
                projectCoveredLines+=coverageInfo.getCoveredLines();
                mutatedTotalLines+=coverageInfo.getMutatedLines();
                coveredMutatedLines+=coverageInfo.getCoveredMutatedLines();

                LOGGER.info(coverageInfo.toString());
                int asserts = TestSmellMetrics.getNumberOfAsserts(testSuite);
                int t_loc = CKMetrics.getLOC(testSuite);
                LOGGER.info("asserts:" + asserts);
                assertionDensity = Math.round(((double) asserts/t_loc) * 100);
                coverageInfo.setAssertionDensity(assertionDensity);
                classCoverageInfo.add(coverageInfo);
            }
            //CLEANUP

            FileUtils.deleteDirectory(new File(buildPath + "\\instrumented"));
            FileUtils.deleteQuietly(new File(buildPath + "\\cobertura.ser"));
            projectCoverageInfo.setClassCoverageInfo(classCoverageInfo);
            double proj_lineCoverage = (projectCoveredLines/projectTotalLines)*100;
            double proj_mutationCoverage = Math.round((coveredMutatedLines/mutatedTotalLines)*100);

            projectCoverageInfo.setLineCoverage(proj_lineCoverage);
            projectCoverageInfo.setMutationCoverage(proj_mutationCoverage);
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getBasePath() + "\\reports\\coverage";
            String output = "project;test-suite;production-class;lineCoverage;branchCoverage;mutationCoverage;assertionDensity\n";
         output+= proj.getName() + ";" + "null;" + "null;" + projectCoverageInfo.getLineCoverage() + ";" + "null" + ";" + "null" + ";" + projectCoverageInfo.getMutationCoverage() +"\n";
            for(ClassCoverageInfo coverageInfo : classCoverageInfo){
                output+=proj.getName() + ";" + coverageInfo.getBelongingPackage() + "." + coverageInfo.getName() + ";" + coverageInfo.getProductionClass() + ";" + coverageInfo.getLineCoverage() +
                        ";" + coverageInfo.getBranchCoverage() + ";" + coverageInfo.getMutationCoverage() + ";" + coverageInfo.getAssertionDensity() + "\n";
            }
            File out = new File(outputDir);
            out.mkdirs();
            FileUtility.writeFile(output, outputDir + "\\" + fileName);
            return projectCoverageInfo;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }

    }
}