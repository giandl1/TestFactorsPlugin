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
    private int affectedClasses;
    private Vector<TestClassAnalysis> classAnalysis;

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

    public Vector<TestClassAnalysis> getClassAnalysis() {
        return classAnalysis;
    }

    public void setClassAnalysis(Vector<TestClassAnalysis> classAnalysis) {
        this.classAnalysis = classAnalysis;
    }

    public int getAffectedClasses() {
        return affectedClasses;
    }

    public void setAffectedClasses(int affectedClasses) {
        this.affectedClasses = affectedClasses;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
