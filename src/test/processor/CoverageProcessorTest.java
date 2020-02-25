package processor;

import com.intellij.ide.ui.EditorOptionsTopHitProvider;
import com.intellij.openapi.application.PathManager;
import data.ClassCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pitest.coverage.execute.CoverageProcess;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CoverageProcessor.class)

public class CoverageProcessorTest {
    @Before
    public void init() {
        PowerMockito.mockStatic(Runtime.class);
    }

    @Test
    public void calculateLineBranch() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        when(mockedProcess.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        File report = new File("resources\\coverage.csv");
        String output="GROUP,PACKAGE,CLASS,INSTRUCTION_MISSED,INSTRUCTION_COVERED,BRANCH_MISSED,BRANCH_COVERED,LINE_MISSED,LINE_COVERED,COMPLEXITY_MISSED,COMPLEXITY_COVERED,METHOD_MISSED,METHOD_COVERED\n" +
                "JaCoCo Coverage Report,azz,Example,6,20,1,1,2,6,1,3,0,3";
        FileUtility.writeFile(output, report.getAbsolutePath());
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
        CoverageProcessor.setNotJbr("placeholder");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<ClassCoverageInfo> covInfos = CoverageProcessor.calculate(prodClasses, testPackages, proj, false, "lib", "resources");
        assertEquals(0.75, covInfos.get(0).getLineCoverage(), 0.001);
        assertEquals(0.5, covInfos.get(0).getBranchCoverage(), 0.001);
        FileUtils.forceDelete(report);    }

    @Test
    public void calculateLineBranch100() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        when(mockedProcess.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        String output="GROUP,PACKAGE,CLASS,INSTRUCTION_MISSED,INSTRUCTION_COVERED,BRANCH_MISSED,BRANCH_COVERED,LINE_MISSED,LINE_COVERED,COMPLEXITY_MISSED,COMPLEXITY_COVERED,METHOD_MISSED,METHOD_COVERED\n" +
                "JaCoCo Coverage Report,azz,Example,0,26,0,2,0,8,1,3,0,3";
        File report = new File("resources\\coverage.csv");
        FileUtility.writeFile(output, report.getAbsolutePath());
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
        Vector<ClassCoverageInfo> covInfos = CoverageProcessor.calculate(prodClasses, testPackages, proj, false, "lib", "resources");
        assertEquals(1.0, covInfos.get(0).getLineCoverage(), 0.001);
        assertEquals(1.0, covInfos.get(0).getBranchCoverage(), 0.001);
        FileUtils.forceDelete(report);
    }

    @Test
    public void calculateNoBranches() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        when(mockedProcess.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        File report = new File("resources\\coverage.csv");
        String output="GROUP,PACKAGE,CLASS,INSTRUCTION_MISSED,INSTRUCTION_COVERED,BRANCH_MISSED,BRANCH_COVERED,LINE_MISSED,LINE_COVERED,COMPLEXITY_MISSED,COMPLEXITY_COVERED,METHOD_MISSED,METHOD_COVERED\n" +
                "JaCoCo Coverage Report,azz,Example,6,20,0,0,2,6,1,3,0,3";
        FileUtility.writeFile(output, report.getAbsolutePath());
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
        CoverageProcessor.setNotJbr("placeholder");
        CoverageProcessor.getNotJbr();
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<ClassCoverageInfo> covInfos = CoverageProcessor.calculate(prodClasses, testPackages, proj, false, "lib", "resources");
        assertEquals(0.75, covInfos.get(0).getLineCoverage(), 0.001);
        assertEquals(-1.0, covInfos.get(0).getBranchCoverage(), 0.001);
        FileUtils.forceDelete(report);
    }
}