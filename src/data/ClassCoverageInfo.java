package data;

public class ClassCoverageInfo {
    private String name;
    private String belongingPackage;
    private double lineCoverage;
    private double branchCoverage;

    public ClassCoverageInfo(String name, String belongingPackage, double lineCoverage, double branchCoverage) {
        this.name = name;
        this.belongingPackage = belongingPackage;
        this.lineCoverage = lineCoverage;
        this.branchCoverage = branchCoverage;
    }

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

    @Override
    public String toString() {
        return "ClassCoverageInfo{" +
                "name='" + name + '\'' +
                ", belongingPackage='" + belongingPackage + '\'' +
                ", lineCoverage=" + lineCoverage +
                ", branchCoverage=" + branchCoverage +
                '}';
    }
}
