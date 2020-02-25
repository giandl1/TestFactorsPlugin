package processor;

import data.ClassCoverageInfo;
import data.ClassMutationCoverageInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pitest.mutationtest.tooling.MutationCoverage;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MutationCoverageProcessor.class)

public class MutationCoverageProcessorTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(Runtime.class);
    }


    @Test
    public void calculate() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        when(mockedProcess.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        String mainPath = "resources/FlakyTests/src/main";
        String testPath = "resources/FlakyTests/src/test";
        String reportPath="resources";
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
        MutationCoverageProcessor.setJavaLocation("placerholder");
        MutationCoverageProcessor.getJavaLocation();
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        proj.setName("FlakyTests");
        proj.setPath("resources/NotFlakyTests");
        proj.setPluginPath("lib");
        MutationCoverageProcessor.setJavaLocation(System.getProperty("java.home") + "\\bin\\java.exe");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String mutationFileName = format.format(new Date());
        String mutationFilePath = reportPath + "\\" + "azz" + "." + "ExampleTest" + "\\" + mutationFileName;
        mutationFileName = mutationFilePath + "\\index.html";
        String output="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Pit Test Coverage Report</h1>\n" +
                "\n" +
                "<h3>Project Summary</h3>\n" +
                "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>Number of Classes</th>\n" +
                "            <th>Line Coverage</th>\n" +
                "            <th>Mutation Coverage</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td>1</td>\n" +
                "            <td>67% <div class=\"coverage_bar\"><div class=\"coverage_complete width-67\"></div><div class=\"coverage_legend\">4/6</div></div></td>\n" +
                "            <td>60% <div class=\"coverage_bar\"><div class=\"coverage_complete width-60\"></div><div class=\"coverage_legend\">3/5</div></div></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "<h3>Breakdown by Package</h3>\n" +
                "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>Name</th>\n" +
                "            <th>Number of Classes</th>\n" +
                "            <th>Line Coverage</th>\n" +
                "            <th>Mutation Coverage</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td><a href=\"./azz/index.html\">azz</a></td>\n" +
                "            <td>1</td>\n" +
                "            <td><div class=\"coverage_percentage\">67% </div><div class=\"coverage_bar\"><div class=\"coverage_complete width-67\"></div><div class=\"coverage_legend\">4/6</div></div></td>\n" +
                "            <td><div class=\"coverage_percentage\">60% </div><div class=\"coverage_bar\"><div class=\"coverage_complete width-60\"></div><div class=\"coverage_legend\">3/5</div></div></td>\n" +
                "        </tr>\n" +
                "\n" +
                "     </tbody>\n" +
                "</table>\n" +
                "<br/>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<hr/>\n" +
                "\n" +
                "Report generated by <a href='http://pitest.org'>PIT</a> 1.4.9\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        File file = new File(mutationFilePath);
        file.mkdirs();
        FileUtility.writeFile(output, mutationFileName);
        ClassMutationCoverageInfo covInfos = MutationCoverageProcessor.calculate(testClass, prodClass, proj, false, reportPath,10);
        assertEquals(0.6, covInfos.getMutationCoverage(), 0.001);

    }

    @Test
    public void calculateNoReport() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        when(mockedProcess.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        String mainPath = "resources/FlakyTests/src/main";
        String testPath = "resources/FlakyTests/src/test";
        String reportPath="noreporthere";
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
        ClassMutationCoverageInfo covInfos = MutationCoverageProcessor.calculate(testClass, prodClass, proj, false, reportPath,10);
        assertEquals(-1.0, covInfos.getMutationCoverage(), 0.001);

    }
}