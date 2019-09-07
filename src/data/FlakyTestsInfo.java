package data;

import it.unisa.testSmellDiffusion.beans.MethodBean;

import java.util.Hashtable;

public class FlakyTestsInfo {
    private String testSuite;
    private Hashtable<String, Integer> flakyTests;


    public FlakyTestsInfo() {}

    public Hashtable<String, Integer> getFlakyTests() {
        return flakyTests;
    }

    public void setFlakyTests(Hashtable<String, Integer> flakyTests) {
        this.flakyTests = flakyTests;
    }

    public String getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(String testSuite) {
        this.testSuite = testSuite;
    }

    @Override
    public String toString() {
        return "FlakyTestsInfo{" +
                "testSuite='" + testSuite + '\'' +
                ", flakyTests=" + flakyTests +
                '}';
    }
}
