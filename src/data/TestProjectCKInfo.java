package data;

import java.util.Vector;

public class TestProjectCKInfo {
    private String name;
    private int testClassesNumber;
    private int loc;
    private int nom;
    private int rfc;
    private int wmc;
    private Vector<ClassCKInfo> classesInfo;

    public TestProjectCKInfo(String name, int loc, int nom, int rfc, int wmc, Vector<ClassCKInfo> classesInfo) {
        this.name = name;
        this.loc = loc;
        this.nom = nom;
        this.rfc = rfc;
        this.wmc = wmc;
        this.classesInfo = classesInfo;
        testClassesNumber = classesInfo.size();
    }

    public TestProjectCKInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Vector<ClassCKInfo> getClassesInfo() {
        return classesInfo;
    }

    public void setClassesInfo(Vector<ClassCKInfo> classesInfo) {
        this.classesInfo = classesInfo;
    }

    public int getTestClassesNumber() {
        return testClassesNumber;
    }

    public void setTestClassesNumber(int testClassesNumber) {
        this.testClassesNumber = testClassesNumber;
    }
}
