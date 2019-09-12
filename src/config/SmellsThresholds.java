package config;

public class SmellsThresholds {
    private int sogliaAR;
    private int sogliaET;
    private int sogliaGF;
    private int sogliaSE;
    private int sogliaRO;
    private int sogliaLT;
    private int sogliaMG;
    private int sogliaIT;
    private int sogliaFTO;

    public SmellsThresholds(int sogliaAR, int sogliaET, int sogliaGF, int sogliaSE, int sogliaRO, int sogliaLT, int sogliaMG, int sogliaIT, int sogliaFTO) {
        this.sogliaAR = sogliaAR;
        this.sogliaET = sogliaET;
        this.sogliaGF = sogliaGF;
        this.sogliaSE = sogliaSE;
        this.sogliaRO = sogliaRO;
        this.sogliaLT = sogliaLT;
        this.sogliaMG = sogliaMG;
        this.sogliaIT = sogliaIT;
        this.sogliaFTO = sogliaFTO;
    }

    public SmellsThresholds(){}

    public int getSogliaAR() {
        return sogliaAR;
    }

    public void setSogliaAR(int sogliaAR) {
        this.sogliaAR = sogliaAR;
    }

    public int getSogliaET() {
        return sogliaET;
    }

    public void setSogliaET(int sogliaET) {
        this.sogliaET = sogliaET;
    }

    public int getSogliaGF() {
        return sogliaGF;
    }

    public void setSogliaGF(int sogliaGF) {
        this.sogliaGF = sogliaGF;
    }

    public int getSogliaSE() {
        return sogliaSE;
    }

    public void setSogliaSE(int sogliaSE) {
        this.sogliaSE = sogliaSE;
    }

    public int getSogliaRO() {
        return sogliaRO;
    }

    public void setSogliaRO(int sogliaRO) {
        this.sogliaRO = sogliaRO;
    }

    public int getSogliaLT() {
        return sogliaLT;
    }

    public void setSogliaLT(int sogliaLT) {
        this.sogliaLT = sogliaLT;
    }

    public int getSogliaMG() {
        return sogliaMG;
    }

    public void setSogliaMG(int sogliaMG) {
        this.sogliaMG = sogliaMG;
    }

    public int getSogliaIT() {
        return sogliaIT;
    }

    public void setSogliaIT(int sogliaIT) {
        this.sogliaIT = sogliaIT;
    }

    public int getSogliaFTO() {
        return sogliaFTO;
    }

    public void setSogliaFTO(int sogliaFTO) {
        this.sogliaFTO = sogliaFTO;
    }
}
