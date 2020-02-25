package processor;

import data.ClassTestSmellsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testSmellRules.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.java2d.loops.FillRect;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IndirectTesting.class, SmellynessProcessor.class, AssertionRoulette.class,EagerTest.class,GeneralFixture.class,ResourceOptimistism.class,
MysteryGuest.class,SensitiveEquality.class})

public class SmellynessProcessorTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(IndirectTesting.class);
        PowerMockito.mockStatic(AssertionRoulette.class);
    }

    @Test
    public void calculatenotCritic() throws Exception {
        AssertionRoulette assertionRoulette = mock(AssertionRoulette.class);
        EagerTest eagerTest = mock(EagerTest.class);
        GeneralFixture generalFixture = mock(GeneralFixture.class);
        IndirectTesting indirectTesting = PowerMockito.mock(IndirectTesting.class);
        SensitiveEquality sensitiveEquality = mock(SensitiveEquality.class);
        ResourceOptimistism resourceOptimistism = mock(ResourceOptimistism.class);
        MysteryGuest mysteryGuest = mock(MysteryGuest.class);
        whenNew(AssertionRoulette.class).withAnyArguments().thenReturn(assertionRoulette);
        whenNew(EagerTest.class).withAnyArguments().thenReturn(eagerTest);
        whenNew(GeneralFixture.class).withAnyArguments().thenReturn(generalFixture);
        whenNew(SensitiveEquality.class).withAnyArguments().thenReturn(sensitiveEquality);
        whenNew(ResourceOptimistism.class).withAnyArguments().thenReturn(resourceOptimistism);
        whenNew(MysteryGuest.class).withAnyArguments().thenReturn(mysteryGuest);
        whenNew(IndirectTesting.class).withAnyArguments().thenReturn(indirectTesting);
        File file = new File("resources\\config.ini");
        whenNew(File.class).withAnyArguments().thenReturn(file);
        TestSmellMetric metric = new TestSmellMetric();
        metric.setId("ar1");
        metric.setValue(2);
        Vector<TestSmellMetric> metrics = new Vector<>();
        metrics.add(metric);
        when(assertionRoulette.isAssertionRoulette(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(assertionRoulette.getMetrics()).thenReturn(metrics);

        TestSmellMetric metricET = new TestSmellMetric();
        metricET.setId("et1");
        metricET.setValue(0);
        Vector<TestSmellMetric> metricsET = new Vector<>();
        metricsET.add(metricET);
        when(eagerTest.isEagerTest(any(ClassBean.class), any(ClassBean.class),Mockito.anyDouble())).thenReturn(true);
        when(eagerTest.getMetrics()).thenReturn(metricsET);

        TestSmellMetric metricGF = new TestSmellMetric();
        metricGF.setId("gf1");
        metricGF.setValue(0);
        Vector<TestSmellMetric> metricsGF = new Vector<>();
        metricsGF.add(metricGF);
        when(generalFixture.isGeneralFixture(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(generalFixture.getMetrics()).thenReturn(metricsGF);

        TestSmellMetric metricSE = new TestSmellMetric();
        metricSE.setId("se1");
        metricSE.setValue(0);
        Vector<TestSmellMetric> metricsSE = new Vector<>();
        metricsSE.add(metricSE);
        when(sensitiveEquality.isSensitiveEquality(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(sensitiveEquality.getMetrics()).thenReturn(metricsSE);

        TestSmellMetric metricRO = new TestSmellMetric();
        metricRO.setId("ro1");
        metricRO.setValue(0);
        Vector<TestSmellMetric> metricsRO = new Vector<>();
        metricsRO.add(metricRO);
        when(resourceOptimistism.isResourceOptimistism(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(resourceOptimistism.getMetrics()).thenReturn(metricsRO);

        TestSmellMetric metricMG = new TestSmellMetric();
        metricMG.setId("mg1");
        metricMG.setValue(0);
        Vector<TestSmellMetric> metricsMG = new Vector<>();
        metricsMG.add(metricMG);
        when(mysteryGuest.isMysteryGuest(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(mysteryGuest.getMetrics()).thenReturn(metricsMG);

        TestSmellMetric metricIT = new TestSmellMetric();
        metricIT.setId("it1");
        metricIT.setValue(0);
        Vector<TestSmellMetric> metricsIT = new Vector<>();
        metricsIT.add(metricIT);
        when(indirectTesting.findInvocations(any(Vector.class))).thenReturn(new ArrayList<MethodBean>());
        when(indirectTesting.isIndirectTesting(any(ClassBean.class), any(ClassBean.class) ,Mockito.any(ArrayList.class) ,Mockito.anyDouble())).thenReturn(true);
        when(indirectTesting.getMetrics()).thenReturn(metricsIT);

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
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        ClassTestSmellsInfo data = SmellynessProcessor.calculate(testClass, prodClass, testPackages, proj);
        assertEquals(1, data.getAssertionRoulette());
        assertEquals(2, data.getWeight());
        assertEquals(1, proj.getAffectedClasses());
        assertEquals(true, data.isAffected());
        assertEquals(false, data.isAffectedCritic());
    }

    @Test
    public void calculateCritic() throws Exception {
        AssertionRoulette assertionRoulette = mock(AssertionRoulette.class);
        EagerTest eagerTest = mock(EagerTest.class);
        GeneralFixture generalFixture = mock(GeneralFixture.class);
        IndirectTesting indirectTesting = PowerMockito.mock(IndirectTesting.class);
        SensitiveEquality sensitiveEquality = mock(SensitiveEquality.class);
        ResourceOptimistism resourceOptimistism = mock(ResourceOptimistism.class);
        MysteryGuest mysteryGuest = mock(MysteryGuest.class);
        whenNew(AssertionRoulette.class).withAnyArguments().thenReturn(assertionRoulette);
        whenNew(EagerTest.class).withAnyArguments().thenReturn(eagerTest);
        whenNew(GeneralFixture.class).withAnyArguments().thenReturn(generalFixture);
        File file = new File("resources\\config.ini");
        whenNew(File.class).withAnyArguments().thenReturn(file);
        whenNew(SensitiveEquality.class).withAnyArguments().thenReturn(sensitiveEquality);
        whenNew(ResourceOptimistism.class).withAnyArguments().thenReturn(resourceOptimistism);
        whenNew(MysteryGuest.class).withAnyArguments().thenReturn(mysteryGuest);
        whenNew(IndirectTesting.class).withAnyArguments().thenReturn(indirectTesting);

        TestSmellMetric metric = new TestSmellMetric();
        metric.setId("ar1");
        metric.setValue(2);
        Vector<TestSmellMetric> metrics = new Vector<>();
        metrics.add(metric);
        when(assertionRoulette.isAssertionRoulette(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(assertionRoulette.getMetrics()).thenReturn(metrics);

        TestSmellMetric metricET = new TestSmellMetric();
        metricET.setId("et1");
        metricET.setValue(4);
        Vector<TestSmellMetric> metricsET = new Vector<>();
        metricsET.add(metricET);
        when(eagerTest.isEagerTest(any(ClassBean.class), any(ClassBean.class),Mockito.anyDouble())).thenReturn(true);
        when(eagerTest.getMetrics()).thenReturn(metricsET);

        TestSmellMetric metricGF = new TestSmellMetric();
        metricGF.setId("gf1");
        metricGF.setValue(0);
        Vector<TestSmellMetric> metricsGF = new Vector<>();
        metricsGF.add(metricGF);
        when(generalFixture.isGeneralFixture(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(generalFixture.getMetrics()).thenReturn(metricsGF);

        TestSmellMetric metricSE = new TestSmellMetric();
        metricSE.setId("se1");
        metricSE.setValue(0);
        Vector<TestSmellMetric> metricsSE = new Vector<>();
        metricsSE.add(metricSE);
        when(sensitiveEquality.isSensitiveEquality(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(sensitiveEquality.getMetrics()).thenReturn(metricsSE);

        TestSmellMetric metricRO = new TestSmellMetric();
        metricRO.setId("ro1");
        metricRO.setValue(0);
        Vector<TestSmellMetric> metricsRO = new Vector<>();
        metricsRO.add(metricRO);
        when(resourceOptimistism.isResourceOptimistism(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(resourceOptimistism.getMetrics()).thenReturn(metricsRO);

        TestSmellMetric metricMG = new TestSmellMetric();
        metricMG.setId("mg1");
        metricMG.setValue(0);
        Vector<TestSmellMetric> metricsMG = new Vector<>();
        metricsMG.add(metricMG);
        when(mysteryGuest.isMysteryGuest(any(ClassBean.class), Mockito.anyDouble())).thenReturn(true);
        when(mysteryGuest.getMetrics()).thenReturn(metricsMG);

        TestSmellMetric metricIT = new TestSmellMetric();
        metricIT.setId("it1");
        metricIT.setValue(0);
        Vector<TestSmellMetric> metricsIT = new Vector<>();
        metricsIT.add(metricIT);
        when(indirectTesting.findInvocations(any(Vector.class))).thenReturn(new ArrayList<MethodBean>());
        when(indirectTesting.isIndirectTesting(any(ClassBean.class), any(ClassBean.class) ,Mockito.any(ArrayList.class) ,Mockito.anyDouble())).thenReturn(true);
        when(indirectTesting.getMetrics()).thenReturn(metricsIT);

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
        TestProjectAnalysis proj = new TestProjectAnalysis();
        proj.setName("FlakyTests");
        proj.setPath("resources/FlakyTests");
        Vector<PackageBean> testPackages = new Vector<>();
        testPackages.add(packagetest);
        ClassTestSmellsInfo data = SmellynessProcessor.calculate(testClass, prodClass, testPackages, proj);
        assertEquals(1, data.getAssertionRoulette());
        assertEquals(2, data.getEagerTest());
        assertEquals(3, data.getWeight());
        assertEquals(1, proj.getAffectedClasses());
        assertEquals(true, data.isAffected());
        assertEquals(true, data.isAffectedCritic());
    }
}