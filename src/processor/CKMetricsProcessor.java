package processor;



import com.intellij.openapi.project.Project;
import data.ClassCKInfo;
import data.TestProjectCKInfo;
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


    public static TestProjectCKInfo calculate(Vector<PackageBean> packages,Vector<PackageBean> testPackages ,Project proj){
        TestProjectCKInfo projectCKInfo = new TestProjectCKInfo();
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
            projectCKInfo.setName(proj.getName());
            projectCKInfo.setLoc(locJUnit);
            projectCKInfo.setNom(nomJUnit);
            projectCKInfo.setWmc(wmcJUnit);
            projectCKInfo.setRfc(rfcJUnit);
            projectCKInfo.setClassesInfo(classesInfo);
            projectCKInfo.setTestClassesNumber(numberOfTestClasses);

            ArrayList<ClassBean> classes = utilities.getClasses(packages);
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getBasePath() + "\\reports\\ckmetrics";
            String output = "project;test-suite;production-class;loc;nom;wmc;rfc\n";
            output+= proj.getName() + ";" + "null;" + "null;" + projectCKInfo.getLoc() + ";" + projectCKInfo.getNom() + ";" + projectCKInfo.getWmc() + ";" + projectCKInfo.getRfc() +"\n";
             for(ClassCKInfo ckInfo : projectCKInfo.getClassesInfo()){
                 output+=proj.getName() + ";" + ckInfo.getBelongingPackage() + "." + ckInfo.getName() + ";" + ckInfo.getProductionClass() + ";" + ckInfo.getLoc() + ";" + ckInfo.getNom() + ";" +
                         ckInfo.getWmc() + ";" + ckInfo.getRfc() + "\n";
             }
             File out = new File(outputDir);
             out.mkdirs();
             FileUtility.writeFile(output, outputDir + "\\" + fileName);

            return projectCKInfo;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
