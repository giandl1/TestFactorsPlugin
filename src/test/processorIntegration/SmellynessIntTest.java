package processorIntegration;

import data.ClassTestSmellsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import org.junit.Test;
import processor.CoverageProcessor;
import processor.SmellynessProcessor;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Vector;

public class SmellynessIntTest {
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
        MethodBean method = new MethodBean();
        method.setName("add");
        method.setTextContent("public void add() {\n" +
                "        final long result = new Calculator().add(2, 3);\n" +
                "        assertEquals(5L, result);\n" +
                "    }");
        testClass.addMethod(method);
        method = new MethodBean();
        method.setName("subtract");
        method.setTextContent("public void subtract() {\n" +
                "        final long result = new Calculator().subtract(10,3);\n" +
                "        assertEquals(7L, result);\n" +
                "    }");
        testClass.addMethod(method);
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("ProgettoExample");
        proj.setPath("resources/test_resources/ProgettoExample");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<PackageBean> packages = new Vector<>();
        packages.add(packagez);
        ClassTestSmellsInfo info = SmellynessProcessor.calculate(testClass, prodClass, packages, proj);
        assertEquals(1, info.getAssertionRoulette());
        assertEquals(false, info.isAffectedCritic());
        assertEquals(true, info.isAffected());
    }
}