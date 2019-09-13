package processor;

import data.*;
import it.unisa.testSmellDiffusion.utility.FileUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportProcessor {

    public static void saveReport(TestProjectAnalysis proj){
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getPath() + "\\reports";
            String output=proj.getName() + ";" + proj.getLoc() +";" + proj.getNom() + ";" + proj.getRfc() + ";" + proj.getWmc() + ";" + proj.getTestClassesNumber() + "\n";
            output += "testsuite;production;loc;nom;wmc;rfc;lc;bc;mc;ar;arm;et;;etm;lt;ltm;mg;mgm;se;sem;ro;rom;fto;ftom;it;itm;gf;gfm\n";
            for(TestClassAnalysis info : proj.getClassAnalysis()){
                ClassCKInfo ckInfo = info.getCkMetrics();
                ClassCoverageInfo covInfo = info.getCoverage();
                ClassTestSmellsInfo smellsInfo = info.getSmells();
                output+=info.getBelongingPackage() + "." + info.getName() + ";" + info.getProductionClass() + ";" + ckInfo.getLoc() + ";" + ckInfo.getNom() + ";" + ckInfo.getWmc() + ";" + ckInfo.getRfc() + ";" +
                        covInfo.getLineCoverage() + ";" + covInfo.getBranchCoverage() + ";" + covInfo.getMutationCoverage() + ";" + smellsInfo.getAssertionRoulette() + ";" + smellsInfo.getArMetric() + ";" +
                        smellsInfo.getEagerTest() + ";" + smellsInfo.getEtMetric() + ";" + smellsInfo.getLazyTest() + ";" + smellsInfo.getLtMetric() + ";" + smellsInfo.getMysteryGuest() + ";" +
                        smellsInfo.getMgMetric() + ";" + smellsInfo.getSensitiveEquality() + ";" + smellsInfo.getSeMetric() + ";" + smellsInfo.getResourceOptimism() + ";" + smellsInfo.getRoMetric() + ";" +
                        smellsInfo.getForTestersOnly() + ";" + smellsInfo.getFtoMetric() + ";" + smellsInfo.getIndirectTesting() + ";" + smellsInfo.getItMetric() + ";" + smellsInfo.getGeneralFixture() + ";" +
                        smellsInfo.getGfMetric() + "\n";
            }
            File out = new File(outputDir);
            out.mkdirs();
            FileUtility.writeFile(output, outputDir + "\\" + fileName);
    }
}
