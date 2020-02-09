package processor;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import init.PluginInit;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.utility.CoverageInfo;
import it.unisa.testSmellDiffusion.utility.PitestHTMLParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MutationCoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");

    public static ClassMutationCoverageInfo calculate(ClassBean testSuite, ClassBean productionClass, File root, TestProjectAnalysis proj, boolean isMaven, long timeoutInSeconds) {
        try {
            ClassMutationCoverageInfo mutationInfo = new ClassMutationCoverageInfo();
            double mutationCoverage = -1;
            String mainBuildPath;
            String testBuildPath;
            String mainPath;
            String testPath;
            String reportPath = root.getAbsolutePath() + "\\out\\pitreport";
            String javaLocation = PluginInit.getJAVALOCATION();
            String[] location = javaLocation.split(".exe");
            String notJbr = null;
            for (String maro : location)
                if (!maro.toLowerCase().contains("jetbrains"))
                    notJbr = maro;
            notJbr += ".exe";
            mainPath = root.getAbsolutePath() + "\\src\\main";
            testPath = root.getAbsolutePath() + "\\src\\test";

            if (!isMaven) {
                mainBuildPath = root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
                testBuildPath = root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            } else {
                mainBuildPath = root.getAbsolutePath() + "\\target\\classes\\";
                testBuildPath = root.getAbsolutePath() + "\\target\\test-classes\\";
            }
            String cmd = "\"" + notJbr + "\" -cp " + mainBuildPath + ";" + testBuildPath + ";" + PathManager.getPluginsPath() + "\\TestFactorsPlugin\\lib\\*" + "\" "
                    + "org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir " + reportPath + "\\" + testSuite.getBelongingPackage() + "." + testSuite.getName() + " --targetClasses " + productionClass.getBelongingPackage() + "." + productionClass.getName() + " "
                    + "--targetTests " + testSuite.getBelongingPackage() + "." + testSuite.getName() + " --sourceDirs " + mainPath + "," + testPath;
            Runtime rt = Runtime.getRuntime();
            LOGGER.info("STARTING PITEST");
            LOGGER.info(cmd);
            Process p = rt.exec(cmd);
            p.waitFor(timeoutInSeconds, TimeUnit.SECONDS);
         /*   String output = "";
            String s="";
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdOut.readLine()) != null) {
                output += s;
            }
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            output = "";
            while ((s = stdErr.readLine()) != null) {
                output += s;
            }
*/

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
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
