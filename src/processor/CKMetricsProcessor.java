package processor;



import com.intellij.openapi.project.Project;
import data.ClassCKInfo;
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


    public static void calculate(Vector<PackageBean> packages,Vector<PackageBean> testPackages, TestProjectAnalysis proj){
        Vector<ClassCKInfo> classesInfo = new Vector<ClassCKInfo>();
        TestMutationUtilities utilities = new TestMutationUtilities();

        try {
            int locJUnit = 0;
            int wmcJUnit = 0;
            int rfcJUnit = 0;
            int nomJUnit = 0;
            int numberOfTestClasses = 0;
            for (PackageBean packageBean : testPackages) {
                for (ClassBean classBean : packageBean.getClasses()) {
                    numberOfTestClasses++;
                    ClassBean prodClass = utilities.getProductionClassBy(classBean.getName(), packages);
                    int loc = CKMetrics.getLOC(classBean);
                    int nom = CKMetrics.getNOM(classBean);
                    int wmc = CKMetrics.getWMC(classBean);
                    int rfc = CKMetrics.getRFC(classBean);
                    ClassCKInfo classInfo = new ClassCKInfo(classBean.getName(), classBean.getBelongingPackage(), prodClass.getBelongingPackage()+"." + prodClass.getName(), loc, rfc, nom, wmc);
                    classesInfo.add(classInfo);
                    locJUnit += loc;
                    nomJUnit += nom;
                    wmcJUnit += wmc;
                    rfcJUnit += rfc;
                }
            }
            proj.setLoc(locJUnit);
            proj.setNom(nomJUnit);
            proj.setWmc(wmcJUnit);
            proj.setRfc(rfcJUnit);
            proj.setClassCKInfo(classesInfo);
            proj.setTestClassesNumber(numberOfTestClasses);

            ArrayList<ClassBean> classes = utilities.getClasses(packages);
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getPath() + "\\reports\\ckmetrics";
            String output = "project;test-suite;production-class;loc;nom;wmc;rfc\n";
            output+= proj.getName() + ";" + "null;" + "null;" + proj.getLoc() + ";" + proj.getNom() + ";" + proj.getWmc() + ";" + proj.getRfc() +"\n";
             for(ClassCKInfo ckInfo : proj.getClassCKInfo()){
                 output+=proj.getName() + ";" + ckInfo.getBelongingPackage() + "." + ckInfo.getName() + ";" + ckInfo.getProductionClass() + ";" + ckInfo.getLoc() + ";" + ckInfo.getNom() + ";" +
                         ckInfo.getWmc() + ";" + ckInfo.getRfc() + "\n";
             }
             File out = new File(outputDir);
             out.mkdirs();
             FileUtility.writeFile(output, outputDir + "\\" + fileName);

        }
        catch(Exception e) {
            e.printStackTrace();
        }


    }

}
