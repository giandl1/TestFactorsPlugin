package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import data.ClassCoverageInfo;
import data.ClassMutationCoverageInfo;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
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
    public static ClassMutationCoverageInfo calculate(ClassBean testSuite, ClassBean productionClass, File root, TestProjectAnalysis proj) {
        try{
            ClassMutationCoverageInfo mutationInfo = new ClassMutationCoverageInfo();
            double mutationCoverage=-1;
            double lineCoverage=-1;
            String reportPath = root.getAbsolutePath() + "\\out\\pitreport";

            String mainBuildPath=root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            String testBuildPath=root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            String mainPath=root.getAbsolutePath() + "\\src\\main";
            String testPath=root.getAbsolutePath() + "\\src\\test";
                String cmd = "java -cp \""+mainBuildPath+";"+testBuildPath+";C:/jar_files/*\" "
                        + "org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir " + reportPath + "\\" + testSuite.getBelongingPackage()+"."+testSuite.getName()+" --targetClasses "+productionClass.getBelongingPackage()+"."+productionClass.getName()+" "
                        + "--targetTests "+testSuite.getBelongingPackage()+"."+testSuite.getName()+" --sourceDirs "+mainPath+","+testPath;
                Runtime rt = Runtime.getRuntime();
                LOGGER.info("STARTING PITEST");
                LOGGER.info(cmd);
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
                    mutationCoverage = (double) Math.round((ci.getMutationCoverage()))/100;
                    mutationInfo.setMutationCoverage(mutationCoverage);
                    mutationInfo.setMutatedLines(ci.getNumberOfMutatedLines());
                    mutationInfo.setCoveredMutatedLines(ci.getCoveredMutatedLines());
                 //   System.out.println(testSuite.getBelongingPackage()+"."+testSuite.getName()+"LINE: "+lineCoverage+" - MUTATION: "+mutationCoverage);
                    LOGGER.info(testSuite.getBelongingPackage()+"."+testSuite.getName()+"LINE: "+lineCoverage+" - MUTATION: "+mutationCoverage);
                }
                else{
                    lineCoverage = Double.NaN;
                    mutationCoverage = Double.NaN;
                }

           // }
            return mutationInfo;


            } catch(Exception e){
LOGGER.info(e.getMessage());            return null;
        }
    }
}
