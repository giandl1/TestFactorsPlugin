package processorIntegration;

import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import org.junit.Test;
import processor.MutationCoverageProcessor;

import java.util.ArrayList;
import java.util.Vector;
import static org.junit.Assert.*;

public class MutationCoverageIntTest {

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
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("ProgettoExample");
        proj.setPath("resources/test_resources/ProgettoExample");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        proj.setPluginPath("lib");
        MutationCoverageProcessor.setJavaLocation(System.getProperty("java.home") + "\\bin\\java.exe");
        ClassMutationCoverageInfo covInfos = MutationCoverageProcessor.calculate(testClass, prodClass, proj, false, proj.getPath() + "\\out\\pitreport", 10);
        assertEquals(0.5, covInfos.getMutationCoverage(), 0.001);

    }
}