package data;

import java.util.Vector;

public class ProjectCoverageInfo {
    private String name;
    private double lineCoverage;
    private double mutationCoverage;
    private Vector<ClassCoverageInfo> classCoverageInfo;

    public ProjectCoverageInfo(String name, int testClassesNumber, double lineCoverage, double mutationCoverage, Vector<ClassCoverageInfo> classCoverageInfo) {
        this.name = name;
        this.lineCoverage = lineCoverage;
        this.mutationCoverage = mutationCoverage;
        this.classCoverageInfo = classCoverageInfo;
    }

    public ProjectCoverageInfo() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public double getLineCoverage() {
        return lineCoverage;
    }

    public void setLineCoverage(double lineCoverage) {
        this.lineCoverage = lineCoverage;
    }

    public double getMutationCoverage() {
        return mutationCoverage;
    }

    public void setMutationCoverage(double mutationCoverage) {
        this.mutationCoverage = mutationCoverage;
    }

    public Vector<ClassCoverageInfo> getClassCoverageInfo() {
        return classCoverageInfo;
    }

    public void setClassCoverageInfo(Vector<ClassCoverageInfo> classCoverageInfo) {
        this.classCoverageInfo = classCoverageInfo;
    }
}
