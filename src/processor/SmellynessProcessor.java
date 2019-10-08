package processor;

import com.intellij.openapi.diagnostic.Logger;
import config.ConfigUtils;
import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import data.ClassTestSmellsInfo;
import data.TestProjectAnalysis;
import data.TestSmellsMetrics;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.testSmellRules.*;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

public class SmellynessProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");

    public static ClassTestSmellsInfo calculate(ClassBean testSuite, ClassBean productionClass, Vector<PackageBean> packages, TestProjectAnalysis project) {
        try {
            boolean isAffected=false;
            boolean isCritic=false;
            TestMutationUtilities utilities = new TestMutationUtilities();
            Collection<MethodBean> methodsInTheProject = IndirectTesting.findInvocations(packages);
            AssertionRoulette assertionRoulette = new AssertionRoulette();
            EagerTest eagerTest = new EagerTest();
            LazyTest lazyTest = new LazyTest();
            MysteryGuest mysteryGuest = new MysteryGuest();
            SensitiveEquality sensitiveEquality = new SensitiveEquality();
            ResourceOptimistism resourceOptimism = new ResourceOptimistism();
            ForTestersOnly forTestersOnly = new ForTestersOnly();
            IndirectTesting indirectTesting = new IndirectTesting();
            GeneralFixture generalFixture = new GeneralFixture();
            ClassTestSmellsInfo  classTestSmellsInfo = new ClassTestSmellsInfo();
            TestSmellMetricsThresholdsList metricsList = new TestSmellMetricsThresholdsList();
           File default_conf = new File(project.getPath() + "\\default_config.ini");
           File conf = new File(project.getPath() + "\\config.ini");
       /*if(!default_conf.exists()) {
            thresholds = new SmellsThresholds(1,1,1,1,1,1,1,1,1);
            new ConfigUtils().writeThresholds(new File(projdir + "\\default_config.ini"), thresholds);
        }*/
            if(conf.exists())
                metricsList = new ConfigUtils().readThresholds(conf);

            else
                metricsList = new ConfigUtils().readThresholds(default_conf);

        //    classTestSmellsInfo.set

                boolean isAssertionRoulette;
                boolean isEagerTest;
            boolean isLazyTest;
            boolean isMysteryGuest;
            boolean isSensitiveEquality;
            boolean isResourceOptimistism;
            boolean isForTestersOnly;
            boolean isIndirectTesting;
            boolean isGeneralFixture;
                String testSuiteName = "NO-TEST";
            TestSmellsMetrics metrics = new TestSmellsMetrics();
                if (testSuite != null) {
                    isAssertionRoulette = assertionRoulette.isAssertionRoulette(testSuite, metricsList.getArMetrics().get(0).getYellowThreshold());
                    if (isAssertionRoulette) {
                        LOGGER.info("Is AR");
                        LOGGER.info("" + metricsList.getArMetrics().get(0).getYellowThreshold());
                        classTestSmellsInfo.setAssertionRoulette(1);
                        isAffected=true;
                        for(TestSmellMetric metric : assertionRoulette.getMetrics())
                            if(metric.getValue() > metricsList.getArMetricThreshold(metric.getId(), true))
                                isCritic=true;
                    }
                    metrics.setArMetrics(assertionRoulette.getMetrics());

                    isEagerTest = eagerTest.isEagerTest(testSuite, productionClass, metricsList.getEtMetrics().get(0).getYellowThreshold());
                    if (isEagerTest) {
                        LOGGER.info("Is ET");

                        classTestSmellsInfo.setEagerTest(1);
                        isAffected=true;
                        for(TestSmellMetric metric : eagerTest.getMetrics())
                            if(metric.getValue() > metricsList.getEtMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setEtMetrics(eagerTest.getMetrics());

                 /*   isLazyTest = lazyTest.isLazyTest(testSuite, productionClass, metricsList.getLtMetrics().get(0).getYellowThreshold()) ? 1 : 0;
                    if (isLazyTest == 1) {
                        classTestSmellsInfo.setLazyTest(1);
                        isAffected=true;

                    }*/
                    isMysteryGuest = mysteryGuest.isMysteryGuest(testSuite, metricsList.getMgMetrics().get(0).getYellowThreshold());
                    if (isMysteryGuest) {
                        LOGGER.info("Is MG");

                        classTestSmellsInfo.setMysteryGuest(1);
                        isAffected=true;
                        for(TestSmellMetric metric : mysteryGuest.getMetrics())
                            if(metric.getValue() > metricsList.getMgMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setMgMetrics(mysteryGuest.getMetrics());

                    isSensitiveEquality = sensitiveEquality.isSensitiveEquality(testSuite, metricsList.getSeMetrics().get(0).getYellowThreshold());
                    if (isSensitiveEquality) {
                        LOGGER.info("Is SE");

                        classTestSmellsInfo.setMysteryGuest(1);
                        isAffected=true;
                        for(TestSmellMetric metric : sensitiveEquality.getMetrics())
                            if(metric.getValue() > metricsList.getSeMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setSeMetrics(sensitiveEquality.getMetrics());

                    isResourceOptimistism = resourceOptimism.isResourceOptimistism(testSuite, metricsList.getRoMetrics().get(0).getYellowThreshold());
                    if (isResourceOptimistism) {
                        LOGGER.info("Is RO");

                        classTestSmellsInfo.setResourceOptimism(1);
                        isAffected=true;
                        for(TestSmellMetric metric : resourceOptimism.getMetrics())
                            if(metric.getValue() > metricsList.getRoMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setRoMetrics(resourceOptimism.getMetrics());

                  /*  isForTestersOnly = forTestersOnly.isForTestersOnly(testSuite, productionClass, methodsInTheProject) ? 1 : 0;
                    if (isForTestersOnly == 1) {
                        classTestSmellsInfo.setForTestersOnly(1);
                        isAffected=true;

                    }*/

                    isIndirectTesting = indirectTesting.isIndirectTesting(testSuite, productionClass, methodsInTheProject, metricsList.getItMetrics().get(0).getYellowThreshold());
                    if (isIndirectTesting) {
                        LOGGER.info("Is IT");

                        classTestSmellsInfo.setIndirectTesting(1);
                        isAffected=true;
                        for(TestSmellMetric metric : indirectTesting.getMetrics())
                            if(metric.getValue() > metricsList.getItMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setItMetrics(indirectTesting.getMetrics());

                    isGeneralFixture = generalFixture.isGeneralFixture(testSuite, metricsList.getGfMetrics().get(0).getYellowThreshold());
                    if (isGeneralFixture) {
                        LOGGER.info("Is GF");

                        classTestSmellsInfo.setGeneralFixture(1);
                        isAffected=true;
                        for(TestSmellMetric metric : generalFixture.getMetrics())
                            if(metric.getValue() > metricsList.getMgMetricThreshold(metric.getId(), true))
                                isCritic=true;

                    }
                    metrics.setGfMetrics(generalFixture.getMetrics());

                LOGGER.info(classTestSmellsInfo.toString());

            }
            /*String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
            String outputDir = proj.getBasePath() + "\\reports\\smellyness";
            String output = "project;test-suite;production-class;ar;et;lt;mg;se;ro;fto;it;dc\n";
            for(ClassTestSmellsInfo smellsInfo : classTestSmellsInfos){
                output+=proj.getName() + ";" + smellsInfo.getBelongingPackage() + "." + smellsInfo.getName() + ";" + smellsInfo.getProductionClass() + ";" + smellsInfo.getAssertionRoulette() + ";" +
                        smellsInfo.getEagerTest()+ ";" + smellsInfo.getLazyTest()+ ";" + smellsInfo.getMysteryGuest()+ ";" + smellsInfo.getSensitiveEquality()+ ";" + smellsInfo.getResourceOptimism()+ ";" +
                        smellsInfo.getForTestersOnly()+ ";" + smellsInfo.getIndirectTesting()+ ";" + smellsInfo.getDuplicateCode() + "\n";
            }
            File out = new File(outputDir);
            out.mkdirs();
            FileUtility.writeFile(output, outputDir + "\\" + fileName); */
            if(isAffected) {
                project.setAffectedClasses(project.getAffectedClasses() + 1);
                classTestSmellsInfo.setAffected(true);
                classTestSmellsInfo.setWeight(2);
            }
            if(isCritic) {
                classTestSmellsInfo.setAffectedCritic(true);
                classTestSmellsInfo.setWeight(3);
            }
            classTestSmellsInfo.setMetrics(metrics);
            return classTestSmellsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
