package processor;

import data.ClassCKInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class CKMetricsProcessorTest {

    @Before
    public void setUp() throws Exception {

    }

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
        testClass.setTextContent("package azz;\n" +
                "\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "import static org.junit.Assert.*;\n" +
                "\n" +
                "public class ExampleTest {\n" +
                "\n" +
                "    @Test\n" +
                "    public void flakynot() {\n" +
                "        Example e = new Example();\n" +
                "        int azz = e.notFlaky(3,5,1);\n" +
                "        assertEquals(8,azz);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @Test\n" +
                "    public void flaky() {\n" +
                "        Example e = new Example();\n" +
                "        int numero = e.Flaky(3);\n" +
                "        assertEquals(1,numero);\n" +
                "    }\n" +
                "}");
        testClass.setLOC(12);
        testClass.setBelongingPackage("azz");
        MethodBean method = new MethodBean();
        method.setName("flakynot");
        method.setTextContent("public void flakynot() {\n" +
                "        Example e = new Example();\n" +
                "        int azz = e.notFlaky(3,5,1);\n" +
                "        assertEquals(8,azz);\n" +
                "\n" +
                "    }\n");
        Vector<MethodBean> methods = new Vector<>();
        method.addMethodCalls(method);
        method.addMethodCalls(method);
        methods.add(method);
        method = new MethodBean();
        method.setName("flaky");
        method.setTextContent("\n" +
                "    public void flaky() {\n" +
                "        Example e = new Example();\n" +
                "        int numero = e.Flaky(3);\n" +
                "        assertEquals(1,numero);\n" +
                "    }\n");
        method.addMethodCalls(method);
        method.addMethodCalls(method);
        methods.add(method);
        testClass.setMethods(methods);
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr(System.getProperty("java.home") + "\\bin\\java.exe");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        ClassCKInfo classInfo = CKMetricsProcessor.calculate(testClass, proj);
        assertEquals(12, classInfo.getLoc());
        assertEquals(2, classInfo.getNom());
        assertEquals(2, classInfo.getWmc());
        assertEquals(4, classInfo.getRfc());
        assertEquals(12, proj.getLoc());
        assertEquals(2, proj.getNom());


    }
}