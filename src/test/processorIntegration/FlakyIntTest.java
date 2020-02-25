package processorIntegration;

import data.FlakyTestsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.junit.Test;
import processor.FlakyTestsProcessor;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class FlakyIntTest {

    @Test
    public void calculate() throws Exception {
        String mainPath = "resources/test_resources/FlakyTests/src/main";
        String testPath = "resources/test_resources/FlakyTests/src/test";
        PackageBean packagez = new PackageBean();
        packagez.setName("expackage");
        ClassBean prodClass = new ClassBean();
        prodClass.setName("Example");
        prodClass.setBelongingPackage("expackage");
        ArrayList<ClassBean> prodClasses = new ArrayList<>();
        prodClasses.add(prodClass);
        packagez.setClasses(prodClasses);
        PackageBean packagetest = new PackageBean();
        packagetest.setName("expackage");
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
        testClass.setBelongingPackage("expackage");
        MethodBean method = new MethodBean();
        method.setName("flakynot");
        method.setTextContent("public void flakynot() {\n" +
                "        Example e = new Example();\n" +
                "        int azz = e.notFlaky(3,5,1);\n" +
                "        assertEquals(8,azz);\n" +
                "\n" +
                "    }\n");
        Vector<MethodBean> methods = new Vector<>();
        methods.add(method);
        method = new MethodBean();
        method.setName("flaky");
        method.setTextContent("\n" +
                "    public void flaky() {\n" +
                "        Example e = new Example();\n" +
                "        int numero = e.Flaky(3);\n" +
                "        assertEquals(1,numero);\n" +
                "    }\n");

        methods.add(method);
        testClass.setMethods(methods);
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<PackageBean> mainPackages = new Vector<>();
        mainPackages.add(packagez);
        proj.setName("FlakyTests");
        proj.setPath("resources/test_resources/FlakyTests");
        proj.setPluginPath("lib");
        FlakyTestsProcessor.setJavaLocation(System.getProperty("java.home")+"\\bin\\java.exe");
        Vector<FlakyTestsInfo> flaky = FlakyTestsProcessor.calculate(mainPackages, testPackages, proj, false, 10);
        assertEquals(1, flaky.get(0).getFlakyMethods().size());
    }
}