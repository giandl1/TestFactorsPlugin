package processor;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import static org.junit.Assert.*;

public class CoverageProcessorTest {


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
        testClass.setBelongingPackage("azz");
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr(System.getProperty("java.home")+"\\bin\\java.exe");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<ClassCoverageInfo> covInfos = CoverageProcessor.calculate(prodClasses, testPackages, proj, false, "lib");
        assertEquals(0.75, covInfos.get(0).getLineCoverage(), 0.001);
        assertEquals(0.5, covInfos.get(0).getBranchCoverage(), 0.001);

    }
}