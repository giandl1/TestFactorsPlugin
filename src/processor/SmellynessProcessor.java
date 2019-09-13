package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import config.SmellsThresholds;
import data.ClassCKInfo;
import data.ClassTestSmellsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import it.unisa.testSmellDiffusion.testSmellRules.*;
import it.unisa.testSmellDiffusion.utility.FileUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

public class SmellynessProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");

    public static ClassTestSmellsInfo calculate(ClassBean testSuite, ClassBean productionClass, Vector<PackageBean> packages, SmellsThresholds thresholds, TestProjectAnalysis project) {
        try {
            boolean isAffected=false;
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

                ClassTestSmellsInfo classTestSmellsInfo = new ClassTestSmellsInfo();
                double isAssertionRoulette = Double.NaN;
                double isEagerTest = Double.NaN;
                double isLazyTest = Double.NaN;
                double isMysteryGuest = Double.NaN;
                double isSensitiveEquality = Double.NaN;
                double isResourceOptimistism = Double.NaN;
                double isForTestersOnly = Double.NaN;
                double isIndirectTesting = Double.NaN;
                double isGeneralFixture = Double.NaN;
                String testSuiteName = "NO-TEST";
                if (testSuite != null) {

                    isAssertionRoulette = assertionRoulette.isAssertionRoulette(testSuite) ? 1 : 0;
                    if (isAssertionRoulette == 1) {
                        classTestSmellsInfo.setAssertionRoulette(1);
                        isAffected=true;
                    }

                    isEagerTest = eagerTest.isEagerTest(testSuite, productionClass) ? 1 : 0;
                    if (isEagerTest == 1) {
                        classTestSmellsInfo.setEagerTest(1);
                        isAffected=true;

                    }

                    isLazyTest = lazyTest.isLazyTest(testSuite, productionClass) ? 1 : 0;
                    if (isLazyTest == 1) {
                        classTestSmellsInfo.setLazyTest(1);
                        isAffected=true;

                    }
                    isMysteryGuest = mysteryGuest.isMysteryGuest(testSuite) ? 1 : 0;
                    if (isMysteryGuest == 1) {
                        classTestSmellsInfo.setMysteryGuest(1);
                        isAffected=true;

                    }

                    isSensitiveEquality = sensitiveEquality.isSensitiveEquality(testSuite) ? 1 : 0;
                    if (isSensitiveEquality == 1) {
                        classTestSmellsInfo.setMysteryGuest(1);
                        isAffected=true;

                    }

                    isResourceOptimistism = resourceOptimism.isResourceOptimistism(testSuite) ? 1 : 0;
                    if (isResourceOptimistism == 1) {
                        classTestSmellsInfo.setResourceOptimism(1);
                        isAffected=true;

                    }

                    isForTestersOnly = forTestersOnly.isForTestersOnly(testSuite, productionClass, methodsInTheProject) ? 1 : 0;
                    if (isForTestersOnly == 1) {
                        classTestSmellsInfo.setForTestersOnly(1);
                        isAffected=true;

                    }

                    isIndirectTesting = indirectTesting.isIndirectTesting(testSuite, productionClass, methodsInTheProject) ? 1 : 0;
                    if (isIndirectTesting == 1) {
                        classTestSmellsInfo.setIndirectTesting(1);
                        isAffected=true;

                    }
                    isGeneralFixture = generalFixture.isGeneralFixture(testSuite) ? 1 : 0;
                    if (isGeneralFixture == 1) {
                        classTestSmellsInfo.setGeneralFixture(1);
                        isAffected=true;

                    }


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
            if(isAffected)
                project.setAffectedClasses(project.getAffectedClasses()+1);
            return classTestSmellsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
