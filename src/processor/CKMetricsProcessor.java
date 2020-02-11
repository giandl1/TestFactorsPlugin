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


    public static ClassCKInfo calculate(ClassBean testSuite, TestProjectAnalysis proj) {


        int loc = CKMetrics.getLOC(testSuite);
        int nom = CKMetrics.getNOM(testSuite);
        int wmc = CKMetrics.getWMC(testSuite);
        int rfc = CKMetrics.getRFC(testSuite);
        ClassCKInfo classInfo = new ClassCKInfo(loc, rfc, nom, wmc);
        proj.setLoc(proj.getLoc() + loc);
        proj.setNom(proj.getNom() + nom);
        proj.setTestClassesNumber(proj.getTestClassesNumber() + 1);
        return classInfo;

    }


}
