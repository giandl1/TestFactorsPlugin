package processor;

import data.ClassCoverageInfo;
import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.junit.Test;
import org.pitest.mutationtest.tooling.MutationCoverage;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class MutationCoverageProcessorTest {

    @Test
    public void calculate() throws Exception {
        String mainPath = "resources/FlakyTests/src/main";
        String testPath = "resources/FlakyTests/src/test";
        PackageBean packagez = new PackageBean();
        packagez.setName("azz");
        ClassBean prodClass = new ClassBean();
        prodClass.setName("Example");
        prodClass.setBelongingPackage("azz");
        ArrayList<ClassBean> prodClasses = new ArrayList<>();
        prodClasses.add(prodClass);
        packagez.setClasses(prodClasses);
        PackageBean packagetest = new PackageBean();
        packagetest.setName("azz");
        ClassBean testClass = new ClassBean();
        testClass.setName("ExampleTest");
        testClass.setBelongingPackage("azz");
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr(System.getProperty("java.home") + "\\bin\\java.exe");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        proj.setName("FlakyTests");
        proj.setPath("resources/NotFlakyTests");
        proj.setPluginPath("lib");
        MutationCoverageProcessor.setJavaLocation(System.getProperty("java.home") + "\\bin\\java.exe");
        ClassMutationCoverageInfo covInfos = MutationCoverageProcessor.calculate(testClass, prodClass, proj, false, 10);
        assertEquals(0.6, covInfos.getMutationCoverage(), 0.001);

    }
}