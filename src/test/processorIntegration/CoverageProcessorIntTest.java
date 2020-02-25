package processorIntegration;


import com.intellij.ide.ui.EditorOptionsTopHitProvider;
import com.intellij.openapi.application.PathManager;
import data.ClassCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.junit.Before;
import org.junit.Test;
import org.pitest.coverage.execute.CoverageProcess;
import processor.CoverageProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import static org.junit.Assert.*;

public class CoverageProcessorIntTest {


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void calculate() throws Exception {
        String mainPath = "resources/test_resources/ProgettoExample/src/main";
        String testPath = "resources/test_resources/ProgettoExample/src/test";
        PackageBean packagez = new PackageBean();
        packagez.setName("test");
        ClassBean prodClass = new ClassBean();
        prodClass.setName("Calculator");
        prodClass.setBelongingPackage("test");
        ArrayList<ClassBean> prodClasses = new ArrayList<>();
        prodClasses.add(prodClass);
        packagez.setClasses(prodClasses);
        PackageBean packagetest = new PackageBean();
        packagetest.setName("test");
        ClassBean testClass = new ClassBean();
        testClass.setName("CalculatorTest");
        testClass.setTextContent("package test;\n" +
                "\n" +
                "\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "import static org.junit.Assert.assertEquals;\n" +
                "\n" +
                "public class CalculatorTest {\n" +
                "\n" +
                "    @Test\n" +
                "   public void add() {\n" +
                "        final long result = new Calculator().add(2, 3);\n" +
                "        assertEquals(5L, result);\n" +
                "    }\n" +
                "\n" +
                "    @Test\n" +
                "   public void subtract() {\n" +
                "        final long result = new Calculator().subtract(10,3);\n" +
                "        assertEquals(7L, result);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}");
        testClass.setBelongingPackage("test");
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr(System.getProperty("java.home") + "\\bin\\java.exe");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("ProgettoExample");
        proj.setPath("resources/test_resources/ProgettoExample");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<ClassCoverageInfo> covInfos = CoverageProcessor.calculate(prodClasses, testPackages, proj, false, "lib", System.getProperty("user.home") + "\\.temevi");
        assertEquals(0.6, covInfos.get(0).getLineCoverage(), 0.001);
        assertEquals(-1.0, covInfos.get(0).getBranchCoverage(), 0.001);

    }
}

