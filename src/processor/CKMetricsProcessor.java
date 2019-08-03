package processor;



import com.intellij.openapi.project.Project;
import data.ClassCKInfo;
import data.TestProjectCKInfo;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import java.util.Vector;

public class CKMetricsProcessor {


    public static TestProjectCKInfo calculate(Vector<PackageBean> testPackages ,Project proj){
        TestProjectCKInfo projectCKInfo = new TestProjectCKInfo();
        Vector<ClassCKInfo> classesInfo = new Vector<ClassCKInfo>();

        try {
            int locJUnit = 0;
            int wmcJUnit = 0;
            int rfcJUnit = 0;
            int nomJUnit = 0;
            int numberOfTestClasses = 0;
            for (PackageBean packageBean : testPackages) {
                for (ClassBean classBean : packageBean.getClasses()) {
                    numberOfTestClasses++;
                    int loc = CKMetrics.getLOC(classBean);
                    int nom = CKMetrics.getNOM(classBean);
                    int wmc = CKMetrics.getWMC(classBean);
                    int rfc = CKMetrics.getRFC(classBean);
                    ClassCKInfo classInfo = new ClassCKInfo(classBean.getName(), classBean.getBelongingPackage(), loc, rfc, nom, wmc);
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
            return projectCKInfo;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
