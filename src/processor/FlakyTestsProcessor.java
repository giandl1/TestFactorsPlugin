package processor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import data.FlakyTestsInfo;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.main.Flaky;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

public class FlakyTestsProcessor {
    private static final Logger LOGGER = Logger.getInstance("global");

    public static FlakyTestsInfo calculate(File root, Vector<PackageBean> packages, Vector<PackageBean> testPackages, TestProjectAnalysis proj){
        try {
            String buildPath = root.getAbsolutePath() + "\\out";
            String destination = root.getAbsolutePath() + "\\out\\production\\" + proj.getName();
            String testPath = root.getAbsolutePath() + "\\out\\test\\" + proj.getName();
            TestMutationUtilities utilities = new TestMutationUtilities();
            ArrayList<ClassBean> classes = utilities.getClasses(packages);
            Vector<FlakyTestsInfo> flakyTests = new Vector<>();
            Hashtable<String, Integer> passedTests = new Hashtable<>();
            int j=0;
            for (ClassBean productionClass : classes) {
                j++;
                ClassBean testSuite = TestMutationUtilities.getTestClassBy(productionClass.getName(), testPackages);
                if (testSuite != null) {
                    String cmd = "java -cp C:/jar_files/*;" + destination + ";" + testPath + " org.junit.runner.JUnitCore " + testSuite.getBelongingPackage() + "." + testSuite.getName();
                    //    LOGGER.info(cmd);
                    Collection<MethodBean> methods = testSuite.getMethods();
                    FlakyTestsInfo info = new FlakyTestsInfo();
                    Hashtable<String, Integer> flaky = new Hashtable();
                    info.setTestSuite(testSuite.getName());
                    Runtime rt = Runtime.getRuntime();
                    //  LOGGER.info("STARTING FIRST RUN TESTS, CLASS nr." + j);
                    Process pr = rt.exec(cmd);
                    String s;
                    String output = "";
                    BufferedReader stdOut = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    while ((s = stdOut.readLine()) != null) {
                        output += s;
                    }
                    for (MethodBean method : methods) {
                        flaky.put(method.getName(), 0);
                        if (output.contains(method.getName()))
                            passedTests.put(method.getName(), 0);
                        else
                            passedTests.put(method.getName(), 1);
                    }


                    //  LOGGER.info("FIRST RUN TESTS END, CLASS nr." + j);
                    for (int i = 0; i < 9; i++) {
                        rt = Runtime.getRuntime();
                        //     LOGGER.info("RUN TEST START: " + i+2);

                        pr = rt.exec(cmd);
                        s = "";
                        output = "";
                        stdOut = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                        while ((s = stdOut.readLine()) != null) {
                            output += s;
                        }
                        for (MethodBean method : methods) {
                            int isFlaky = flaky.get(method.getName());
                            if (isFlaky == 0) {
                                int passed = passedTests.get(method.getName());
                                if (output.contains(method.getName()) && passed == 1)
                                    flaky.replace(method.getName(), 1);
                                else if (!output.contains(method.getName()) && passed == 0)
                                    flaky.replace(method.getName(), 1);
                            }
                        }
                        //   LOGGER.info("RUN TEST END: " + i+2);

                    }


                    info.setFlakyTests(flaky);
                    flakyTests.add(info);
                    //   LOGGER.info(flaky.toString());
                }
            }
            FlakyTestsInfo info = new FlakyTestsInfo();
            return info;

        } catch(Exception e){
            LOGGER.info(e.toString());
            return null;
        }
    }
}
