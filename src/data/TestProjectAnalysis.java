package data;

import java.util.Vector;

public class TestProjectAnalysis {
    private String name;
    private String path;
    private int testClassesNumber;
    private double lineCoverage;
    private double mutationCoverage;
    private int loc;
    private int nom;
    private int rfc;
    private int wmc;
    private Vector<ClassCKInfo> classCKInfo;
    private Vector<ClassCoverageInfo> classCoverageInfo;
    private Vector<ClassTestSmellsInfo> classSmellsInfo;
    private Vector<FlakyTestsInfo> flakyTestsInfo;

    public TestProjectAnalysis() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTestClassesNumber() {
        return testClassesNumber;
    }

    public void setTestClassesNumber(int testClassesNumber) {
        this.testClassesNumber = testClassesNumber;
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

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getNom() {
        return nom;
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public int getRfc() {
        return rfc;
    }

    public void setRfc(int rfc) {
        this.rfc = rfc;
    }

    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public Vector<ClassCKInfo> getClassCKInfo() {
        return classCKInfo;
    }

    public void setClassCKInfo(Vector<ClassCKInfo> classCKInfo) {
        this.classCKInfo = classCKInfo;
    }

    public Vector<ClassCoverageInfo> getClassCoverageInfo() {
        return classCoverageInfo;
    }

    public void setClassCoverageInfo(Vector<ClassCoverageInfo> classCoverageInfo) {
        this.classCoverageInfo = classCoverageInfo;
    }

    public Vector<ClassTestSmellsInfo> getClassSmellsInfo() {
        return classSmellsInfo;
    }

    public void setClassSmellsInfo(Vector<ClassTestSmellsInfo> classSmellsInfo) {
        this.classSmellsInfo = classSmellsInfo;
    }

    public Vector<FlakyTestsInfo> getFlakyTestsInfo() {
        return flakyTestsInfo;
    }

    public void setFlakyTestsInfo(Vector<FlakyTestsInfo> flakyTestsInfo) {
        this.flakyTestsInfo = flakyTestsInfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
