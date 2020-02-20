package processor;

import data.FlakyTestsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FlakyTestsProcessor.class)

public class FlakyTestsProcessorTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(Runtime.class);
    }


    @Test
    public void calculate1Flaky() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        InputStream is = new ByteArrayInputStream(("JUnit version 4.12\n" +
                "..\n" +
                "Time: 0\n" +
                "\n" +
                "OK (2 tests)").getBytes());
        InputStream is2 = new ByteArrayInputStream(("JUnit version 4.12\n" +
                ".E.\n" +
                "Time: 0\n" +
                "There was 1 failure:\n" +
                "1) flaky(azz.ExampleTest)\n" +
                "java.lang.AssertionError: expected:<2> but was:<0>\n" +
                "        at org.junit.Assert.fail(Assert.java:88)\n" +
                "        at org.junit.Assert.failNotEquals(Assert.java:834)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:645)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:631)\n" +
                "        at azz.ExampleTest.flaky(ExampleTest.java:21)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "        at java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)\n" +
                "        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)\n" +
                "        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)\n" +
                "        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)\n" +
                "        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:128)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:27)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:137)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:115)\n" +
                "        at org.junit.runner.JUnitCore.runMain(JUnitCore.java:77)\n" +
                "        at org.junit.runner.JUnitCore.main(JUnitCore.java:36)\n" +
                "\n" +
                "FAILURES!!!\n" +
                "Tests run: 2,  Failures: 1").getBytes());
        when(mockedProcess.getInputStream()).thenAnswer(new Answer() {
            private int count=0;
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                is.reset(); is2.reset();
                count++;
                if(count==3){
                    return is2;
                }
                return is;
            }
        });
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
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
        CoverageProcessor.setNotJbr("placeholder");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<PackageBean> mainPackages = new Vector<>();
        mainPackages.add(packagez);
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        proj.setPluginPath("lib");
        FlakyTestsProcessor.setJavaLocation("placeholder");
        Vector<FlakyTestsInfo> flaky = FlakyTestsProcessor.calculate(mainPackages, testPackages, proj, false, 10);
        assertEquals(1, flaky.get(0).getFlakyMethods().size());
    }

    @Test
    public void calculate0Flaky() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        InputStream is = new ByteArrayInputStream(("JUnit version 4.12\n" +
                "..\n" +
                "Time: 0\n" +
                "\n" +
                "OK (2 tests)").getBytes());

        when(mockedProcess.getInputStream()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                is.reset();
                return is;
            }
        });
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
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
                "}");
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
        methods.add(method);
        testClass.setMethods(methods);
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr("placeholder");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<PackageBean> mainPackages = new Vector<>();
        mainPackages.add(packagez);
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        proj.setPluginPath("lib");
        FlakyTestsProcessor.setJavaLocation("placeholder");
        Vector<FlakyTestsInfo> flaky = FlakyTestsProcessor.calculate(mainPackages, testPackages, proj, false, 10);
        assertEquals(0, flaky.get(0).getFlakyMethods().size());
    }

    @Test
    public void calculate2Flaky() throws Exception {
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        Process mockedProcess = PowerMockito.mock(Process.class);
        when(mockRuntime.exec(Mockito.anyString())).thenReturn(mockedProcess);
        InputStream is = new ByteArrayInputStream(("JUnit version 4.12\n" +
                "...\n" +
                "Time: 0,01\n" +
                "\n" +
                "OK (3 tests)").getBytes());
        InputStream is2 = new ByteArrayInputStream(("JUnit version 4.12\n" +
                ".E..\n" +
                "Time: 0,01\n" +
                "There was 1 failure:\n" +
                "1) flaky2(azz.ExampleTest)\n" +
                "java.lang.AssertionError: expected:<2> but was:<1>\n" +
                "        at org.junit.Assert.fail(Assert.java:88)\n" +
                "        at org.junit.Assert.failNotEquals(Assert.java:834)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:645)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:631)\n" +
                "        at azz.ExampleTest.flaky2(ExampleTest.java:28)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "        at java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)\n" +
                "        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)\n" +
                "        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)\n" +
                "        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)\n" +
                "        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:128)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:27)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:137)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:115)\n" +
                "        at org.junit.runner.JUnitCore.runMain(JUnitCore.java:77)\n" +
                "        at org.junit.runner.JUnitCore.main(JUnitCore.java:36)\n" +
                "\n" +
                "FAILURES!!!\n" +
                "Tests run: 3,  Failures: 1").getBytes());

        InputStream is3 = new ByteArrayInputStream(("JUnit version 4.12\n" +
                ".E.E.\n" +
                "Time: 0\n" +
                "There were 2 failures:\n" +
                "1) flaky2(azz.ExampleTest)\n" +
                "java.lang.AssertionError: expected:<2> but was:<3>\n" +
                "        at org.junit.Assert.fail(Assert.java:88)\n" +
                "        at org.junit.Assert.failNotEquals(Assert.java:834)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:645)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:631)\n" +
                "        at azz.ExampleTest.flaky2(ExampleTest.java:28)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "        at java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)\n" +
                "        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)\n" +
                "        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)\n" +
                "        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)\n" +
                "        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:128)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:27)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:137)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:115)\n" +
                "        at org.junit.runner.JUnitCore.runMain(JUnitCore.java:77)\n" +
                "        at org.junit.runner.JUnitCore.main(JUnitCore.java:36)\n" +
                "2) flaky(azz.ExampleTest)\n" +
                "java.lang.AssertionError: expected:<2> but was:<0>\n" +
                "        at org.junit.Assert.fail(Assert.java:88)\n" +
                "        at org.junit.Assert.failNotEquals(Assert.java:834)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:645)\n" +
                "        at org.junit.Assert.assertEquals(Assert.java:631)\n" +
                "        at azz.ExampleTest.flaky(ExampleTest.java:21)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "        at java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)\n" +
                "        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)\n" +
                "        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)\n" +
                "        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)\n" +
                "        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)\n" +
                "        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:128)\n" +
                "        at org.junit.runners.Suite.runChild(Suite.java:27)\n" +
                "        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n" +
                "        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n" +
                "        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n" +
                "        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n" +
                "        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n" +
                "        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:137)\n" +
                "        at org.junit.runner.JUnitCore.run(JUnitCore.java:115)\n" +
                "        at org.junit.runner.JUnitCore.runMain(JUnitCore.java:77)\n" +
                "        at org.junit.runner.JUnitCore.main(JUnitCore.java:36)\n" +
                "\n" +
                "FAILURES!!!\n" +
                "Tests run: 3,  Failures: 2").getBytes());

        when(mockedProcess.getInputStream()).thenAnswer(new Answer() {
            private int count=0;
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                is.reset(); is2.reset(); is3.reset();
                count++;
                if(count==3){
                    return is2;
                }
                else if(count==8)
                    return is3;
                return is;
            }
        });
        when(mockedProcess.getErrorStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
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
                "    public int flaky2(int y){\n" +
                "        Random r = new Random();\n" +
                "        int random = r.nextInt(y);\n" +
                "        return random;\n" +
                "    }" + "\n" +
                "}");
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
        method = new MethodBean();
        method.setName("flaky2");
        method.setTextContent("    public int flaky2(int y){\n" +
                        "        Random r = new Random();\n" +
                        "        int random = r.nextInt(y);\n" +
                        "        return random;\n" +
                        "    }");
        methods.add(method);
        testClass.setMethods(methods);
        ArrayList<ClassBean> testClasses = new ArrayList<>();
        testClasses.add(testClass);
        packagetest.setClasses(testClasses);
        CoverageProcessor.setNotJbr("placeholder");
        TestProjectAnalysis proj = new TestProjectAnalysis();
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        Vector<PackageBean> mainPackages = new Vector<>();
        mainPackages.add(packagez);
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        proj.setPluginPath("lib");
        FlakyTestsProcessor.setJavaLocation("placeholder");
        Vector<FlakyTestsInfo> flaky = FlakyTestsProcessor.calculate(mainPackages, testPackages, proj, false, 10);
        assertEquals(2, flaky.get(0).getFlakyMethods().size());
    }
}