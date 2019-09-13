package processor;



import com.intellij.openapi.project.Project;
import data.ClassCKInfo;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class CKMetricsProcessor {


    public static ClassCKInfo calculate(ClassBean testSuite, TestProjectAnalysis proj){
        TestMutationUtilities utilities = new TestMutationUtilities();

        try {
                    int loc = CKMetrics.getLOC(testSuite);
                    int nom = CKMetrics.getNOM(testSuite);
                    int wmc = CKMetrics.getWMC(testSuite);
                    int rfc = CKMetrics.getRFC(testSuite);
                    ClassCKInfo classInfo = new ClassCKInfo(loc, rfc, nom, wmc);
                    proj.setLoc(proj.getLoc()+loc);
                    proj.setNom(proj.getNom()+nom);
                    proj.setWmc(proj.getWmc()+wmc);
                    proj.setRfc(proj.getRfc()+rfc);
                    proj.setTestClassesNumber(proj.getTestClassesNumber()+1);

          /* String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getPath() + "\\reports\\ckmetrics";
            String output = "project;test-suite;production-class;loc;nom;wmc;rfc\n";
            output+= proj.getName() + ";" + "null;" + "null;" + proj.getLoc() + ";" + proj.getNom() + ";" + proj.getWmc() + ";" + proj.getRfc() +"\n";
             for(ClassCKInfo ckInfo : classesInfo){
                 output+=proj.getName() + ";" + ckInfo.getBelongingPackage() + "." + ckInfo.getName() + ";" + ckInfo.getProductionClass() + ";" + ckInfo.getLoc() + ";" + ckInfo.getNom() + ";" +
                         ckInfo.getWmc() + ";" + ckInfo.getRfc() + "\n";
             }
             File out = new File(outputDir);
             out.mkdirs();
             FileUtility.writeFile(output, outputDir + "\\" + fileName);*/
             return classInfo;

        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
