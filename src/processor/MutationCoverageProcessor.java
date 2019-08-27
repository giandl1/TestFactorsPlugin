package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import data.ClassCoverageInfo;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.CoverageInfo;
import it.unisa.testSmellDiffusion.utility.PitestHTMLParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class MutationCoverageProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");
    public static ClassCoverageInfo calculate(ClassCoverageInfo classCoverageInfo, ClassBean productionClass, File root, Vector<PackageBean> testPackages, Project proj) {
        try{
            double mutationCoverage=-1;
            double lineCoverage=-1;
            String reportPath = root.getAbsolutePath() + "\\out\\pitreport";

            String mainBuildPath=root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            String testBuildPath=root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            String mainPath=root.getAbsolutePath() + "\\src\\main\\java";
            String testPath=root.getAbsolutePath() + "\\src\\test\\java";
            TestMutationUtilities utilities = new TestMutationUtilities();
           // for (ClassBean productionClass : classes) {
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                String cmd = "java -cp \""+mainBuildPath+";"+testBuildPath+";C:/jar_files/*\" "
                        + "org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir " + reportPath + "\\" + testSuite.getBelongingPackage()+"."+testSuite.getName()+" --targetClasses "+productionClass.getBelongingPackage()+"."+productionClass.getName()+" "
                        + "--targetTests "+testSuite.getBelongingPackage()+"."+testSuite.getName()+" --sourceDirs "+mainPath+","+testPath;
                Runtime rt = Runtime.getRuntime();
                LOGGER.info("STARTING PITEST");
                Process pr = rt.exec(cmd);
                pr.waitFor();
                LOGGER.info("ENDING PITEST");

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                String mutationFileName = format.format(new Date());
                System.out.println(testSuite.getBelongingPackage()+"."+testSuite.getName()+"/"+mutationFileName);
                mutationFileName = reportPath + "\\" +testSuite.getBelongingPackage()+"."+testSuite.getName()+"/"+mutationFileName+"/index.html";
                if (new File(mutationFileName).exists()){
                    PitestHTMLParser pitHTML = new PitestHTMLParser(mutationFileName);
                    CoverageInfo ci = pitHTML.getCoverageInfo();
                    lineCoverage = Math.round(ci.getLineCoverage());
                    mutationCoverage = Math.round(ci.getMutationCoverage());
                    classCoverageInfo.setMutationCoverage(mutationCoverage);
                    classCoverageInfo.setCoveredLines(ci.getCoveredLines());
                    classCoverageInfo.setTotalLines(testSuite.getLOC());
                    classCoverageInfo.setMutatedLines(ci.getNumberOfMutatedLines());
                    classCoverageInfo.setCoveredMutatedLines(ci.getCoveredMutatedLines());
                 //   System.out.println(testSuite.getBelongingPackage()+"."+testSuite.getName()+"LINE: "+lineCoverage+" - MUTATION: "+mutationCoverage);
                    LOGGER.info(testSuite.getBelongingPackage()+"."+testSuite.getName()+"LINE: "+lineCoverage+" - MUTATION: "+mutationCoverage);
                }
                else{
                    lineCoverage = Double.NaN;
                    mutationCoverage = Double.NaN;
                }

           // }
            return classCoverageInfo;


            } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
