package data;

public class TestClassAnalysis {
    private String name;
    private String belongingPackage;
    private String productionClass;
    private ClassCKInfo ckMetrics;
    private ClassCoverageInfo coverage;
    private ClassTestSmellsInfo smells;
    private FlakyTestsInfo flakyTests;

    public TestClassAnalysis(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(String belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public String getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(String productionClass) {
        this.productionClass = productionClass;
    }

    public ClassCKInfo getCkMetrics() {
        return ckMetrics;
    }

    public void setCkMetrics(ClassCKInfo ckMetrics) {
        this.ckMetrics = ckMetrics;
    }

    public ClassCoverageInfo getCoverage() {
        return coverage;
    }

    public void setCoverage(ClassCoverageInfo coverage) {
        this.coverage = coverage;
    }

    public ClassTestSmellsInfo getSmells() {
        return smells;
    }

    public void setSmells(ClassTestSmellsInfo smells) {
        this.smells = smells;
    }

    public FlakyTestsInfo getFlakyTests() {
        return flakyTests;
    }

    public void setFlakyTests(FlakyTestsInfo flakyTests) {
        this.flakyTests = flakyTests;
    }

    @Override
    public String toString() {
        return name;
    }
}
