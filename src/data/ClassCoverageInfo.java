package data;

public class ClassCoverageInfo {
    private String name;
    private String belongingPackage;
    private String productionClass;
    private double lineCoverage;
    private double branchCoverage;
    private double mutationCoverage;
    private double assertionDensity;
    private double coveredLines;
    private double totalLines;
    private double mutatedLines;
    private double coveredMutatedLines;

    public ClassCoverageInfo(String name, String belongingPackage, String productionClass, double lineCoverage, double branchCoverage, double mutationCoverage, double assertionDensity) {
        this.name = name;
        this.belongingPackage = belongingPackage;
        this.productionClass = productionClass;
        this.lineCoverage = lineCoverage;
        this.branchCoverage = branchCoverage;
        this.mutationCoverage = mutationCoverage;
        this.assertionDensity = assertionDensity;
    }

    public ClassCoverageInfo() {}

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

    public double getLineCoverage() {
        return lineCoverage;
    }

    public void setLineCoverage(double lineCoverage) {
        this.lineCoverage = lineCoverage;
    }

    public double getBranchCoverage() {
        return branchCoverage;
    }

    public void setBranchCoverage(double branchCoverage) {
        this.branchCoverage = branchCoverage;
    }

    public double getMutationCoverage() {
        return mutationCoverage;
    }

    public void setMutationCoverage(double mutationCoverage) {
        this.mutationCoverage = mutationCoverage;
    }

    public double getAssertionDensity() {
        return assertionDensity;
    }

    public void setAssertionDensity(double assertionDensity) {
        this.assertionDensity = assertionDensity;
    }

    public String getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(String productionClass) {
        this.productionClass = productionClass;
    }

    public double getCoveredLines() {
        return coveredLines;
    }

    public void setCoveredLines(double coveredLines) {
        this.coveredLines = coveredLines;
    }

    public double getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(double totalLines) {
        this.totalLines = totalLines;
    }

    public double getMutatedLines() {
        return mutatedLines;
    }

    public void setMutatedLines(double mutatedLines) {
        this.mutatedLines = mutatedLines;
    }

    public double getCoveredMutatedLines() {
        return coveredMutatedLines;
    }

    public void setCoveredMutatedLines(double coveredMutatedLines) {
        this.coveredMutatedLines = coveredMutatedLines;
    }

    @Override
    public String toString() {
        return "ClassCoverageInfo{" +
                "name='" + name + '\'' +
                ", belongingPackage='" + belongingPackage + '\'' +
                ", productionClass='" + productionClass + '\'' +
                ", lineCoverage=" + lineCoverage +
                ", branchCoverage=" + branchCoverage +
                ", mutationCoverage=" + mutationCoverage +
                ", assertionDensity=" + assertionDensity +
                '}';
    }
}
