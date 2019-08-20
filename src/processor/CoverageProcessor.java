package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import data.ClassCoverageInfo;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.CoberturaHTMLParser;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


public class CoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");
    public static void calculate(File root, Vector<PackageBean> packages, Vector<PackageBean> testPackages, Project proj) {
        try {
            TestMutationUtilities utilities = new TestMutationUtilities();
            double lineCoverage = 0;
            double branchCoverage = 0;
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
                String coberturaPath = buildPath + "\\report\\" + productionClass.getBelongingPackage() + "." + productionClass.getName() + ".html";
                CoberturaHTMLParser parser = new CoberturaHTMLParser(coberturaPath);
                lineCoverage = parser.getLineCoverage();
                branchCoverage = parser.getBranchCoverage();
                ClassCoverageInfo coverageInfo = new ClassCoverageInfo(productionClass.getName(), productionClass.getBelongingPackage(), lineCoverage, branchCoverage);
                LOGGER.info(coverageInfo.toString());
            }
            //CLEANUP

            FileUtils.deleteDirectory(new File(buildPath + "\\instrumented"));
            FileUtils.deleteQuietly(new File(buildPath + "\\cobertura.ser"));

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

    }
}