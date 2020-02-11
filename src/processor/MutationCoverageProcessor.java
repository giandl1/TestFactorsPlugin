package processor;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import init.PluginInit;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.utility.CoverageInfo;
import it.unisa.testSmellDiffusion.utility.PitestHTMLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MutationCoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");
    private static String notJbr;

    public static String getJavaLocation() {
        return notJbr;
    }

    public static void setJavaLocation(String notJbr) {
        MutationCoverageProcessor.notJbr = notJbr;
    }

    public static ClassMutationCoverageInfo calculate(ClassBean testSuite, ClassBean productionClass, TestProjectAnalysis proj, boolean isMaven, long timeoutInSeconds) {
        try {
            ClassMutationCoverageInfo mutationInfo = new ClassMutationCoverageInfo();
            double mutationCoverage = -1;
            String mainBuildPath;
            String testBuildPath;
            String mainPath;
            String testPath;
            String reportPath = proj.getPath() + "\\out\\pitreport";

            mainPath = proj.getPath() + "\\src\\main";
            testPath = proj.getPath() + "\\src\\test";

            if (!isMaven) {
                mainBuildPath = proj.getPath() + "\\out\\production\\" + proj.getName();
                testBuildPath = proj.getPath() + "\\out\\test\\" + proj.getName();
            } else {
                mainBuildPath = proj.getPath() + "\\target\\classes\\";
                testBuildPath = proj.getPath() + "\\target\\test-classes\\";
            }
            String cmd = "\"" + notJbr + "\" -cp " + mainBuildPath + ";" + testBuildPath + ";" + proj.getPluginPath() + "\\*; "
                    + "org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir " + reportPath + "\\" + testSuite.getBelongingPackage() + "." + testSuite.getName() + " --targetClasses " + productionClass.getBelongingPackage() + "." + productionClass.getName() + " "
                    + "--targetTests " + testSuite.getBelongingPackage() + "." + testSuite.getName() + " --sourceDirs " + mainPath + "," + testPath;
            Runtime rt = Runtime.getRuntime();
            LOGGER.info("STARTING PITEST");
            System.out.println(cmd);
            LOGGER.info(cmd);
            Process p = rt.exec(cmd);
            p.waitFor(timeoutInSeconds, TimeUnit.SECONDS);



          p.destroy();
            LOGGER.info("ENDING PITEST");

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            String mutationFileName = format.format(new Date());
            System.out.println(testSuite.getBelongingPackage() + "." + testSuite.getName() + "/" + mutationFileName);
            mutationFileName = reportPath + "\\" + testSuite.getBelongingPackage() + "." + testSuite.getName() + "/" + mutationFileName + "/index.html";
            mutationInfo.setReportName(mutationFileName);
            if (new File(mutationFileName).exists()) {
                PitestHTMLParser pitHTML = new PitestHTMLParser(mutationFileName);
                CoverageInfo ci = pitHTML.getCoverageInfo();
                mutationCoverage = (double) Math.round((ci.getMutationCoverage())) / 100;
                mutationInfo.setMutationCoverage(mutationCoverage);
                mutationInfo.setMutatedLines(ci.getNumberOfMutatedLines());
                mutationInfo.setCoveredMutatedLines(ci.getCoveredMutatedLines());
                //   System.out.println(testSuite.getBelongingPackage()+"."+testSuite.getName()+"LINE: "+lineCoverage+" - MUTATION: "+mutationCoverage);
            } else {
                mutationCoverage = Double.NaN;
            }

            // }
            return mutationInfo;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
