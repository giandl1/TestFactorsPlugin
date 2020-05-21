package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;
import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.utility.CoverageInfo;
import it.unisa.testSmellDiffusion.utility.PitestHTMLParser;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MutationCoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");


    public static ClassMutationCoverageInfo calculate(ClassBean testSuite, ClassBean productionClass, TestProjectAnalysis proj, long timeoutInSeconds) {
        try {
            ClassMutationCoverageInfo mutationInfo = new ClassMutationCoverageInfo();
            double mutationCoverage = -1;
            String mainBuildPath;
            String testBuildPath;
            String mainPath;
            String testPath;
            String javaPath = proj.getJavaPath();
            boolean isMaven = proj.isMaven();
            String reportPath = proj.getConfigPath() + "/pitreport";
            mainPath = proj.getPath() + "/src/main";
            testPath = proj.getPath() + "/src/test";
            ArrayList<String> librariesPaths = proj.getLibrariesPaths();
            if (!isMaven) {
                mainBuildPath = proj.getPath() + "/out/production/" + proj.getName();
                testBuildPath = proj.getPath() + "/out/test/" + proj.getName();
            } else {
                mainBuildPath = proj.getPath() + "/target/classes/";
                testBuildPath = proj.getPath() + "/target/test-classes/";
            }
            String cmd;
            String sep;
            String libPaths = "";
            if (SystemInfo.getOsNameAndVersion().toLowerCase().contains("windows")) {
                sep = ";";
                javaPath = "\"" + javaPath + "\"";
            } else
                sep = ":";
            for (String path : librariesPaths)
                libPaths += path + sep;
            libPaths = libPaths.replaceAll("!/", "");
           /* cmd = "\"" + javaPath + "\" -cp " + proj.getPluginPath() + "/pitest-1.4.9.jar;" + proj.getPluginPath() + "/pitest-command-line-1.4.9.jar;"
                    + proj.getPluginPath() + "/pitest-entry-1.4.9.jar;" +  mainBuildPath + ";" + testBuildPath + ";";
            for(String path : librariesPaths)
                cmd+=path + ";";
            cmd+=  " org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir " + reportPath + "/" + testSuite.getBelongingPackage() + "." + testSuite.getName()
                    + " --targetClasses " + productionClass.getBelongingPackage() + "." + productionClass.getName() + " "
                    + "--targetTests " + testSuite.getBelongingPackage() + "." + testSuite.getName() + " --sourceDirs " + mainPath + "," + testPath;
            cmd = cmd.replaceAll("!/", "");
            */

            String[] cmdargs = new String[]{
                    javaPath,
                    "-cp",
                    proj.getPluginPath() + "/pitest-1.4.9.jar" + sep + proj.getPluginPath() + "/pitest-command-line-1.4.9.jar" + sep
                            + proj.getPluginPath() + "/pitest-entry-1.4.9.jar" + sep + mainBuildPath + sep + testBuildPath + sep + libPaths,
                    "org.pitest.mutationtest.commandline.MutationCoverageReport",
                    "--reportDir",
                    reportPath + "/" + testSuite.getBelongingPackage() + "." + testSuite.getName(),
                    "--targetClasses",
                    productionClass.getBelongingPackage() + "." + productionClass.getName(),
                    "--targetTests",
                    testSuite.getBelongingPackage() + "." + testSuite.getName(),
                    "--sourceDirs",
                    mainPath + "," + testPath
            };
            Runtime rt = Runtime.getRuntime();
            // LOGGER.info("STARTING PITEST");
            Process p;

            p = rt.exec(cmdargs);
            long time = System.currentTimeMillis();
            p.waitFor(timeoutInSeconds, TimeUnit.SECONDS);
            p.destroyForcibly();
            // LOGGER.info("ENDING PITEST");

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            String mutationFileName = format.format(new Date());
            mutationFileName = reportPath + "/" + testSuite.getBelongingPackage() + "." + testSuite.getName() + "/" + mutationFileName + "/index.html";
            mutationInfo.setReportName(mutationFileName);
            if (new File(mutationFileName).exists()) {
                PitestHTMLParser pitHTML = new PitestHTMLParser(mutationFileName);
                CoverageInfo ci = pitHTML.getCoverageInfo();
                mutationCoverage = (double) Math.round((ci.getMutationCoverage())) / 100;
                mutationInfo.setMutationCoverage(mutationCoverage);
                mutationInfo.setMutatedLines(ci.getNumberOfMutatedLines());
                mutationInfo.setCoveredMutatedLines(ci.getCoveredMutatedLines());
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
