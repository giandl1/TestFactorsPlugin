package data;

public class ClassTestSmellsInfo {
    private int assertionRoulette;
    private double arMetric;
    private int eagerTest;
    private double etMetric;
    private int lazyTest;
    private double ltMetric;
    private int mysteryGuest;
    private double mgMetric;
    private int sensitiveEquality;
    private double seMetric;
    private int resourceOptimism;
    private double roMetric;
    private int forTestersOnly;
    private double ftoMetric;
    private int indirectTesting;
    private double itMetric;
    private int generalFixture;
    private double gfMetric;

    public ClassTestSmellsInfo(int assertionRoulette, int eagerTest, int lazyTest, int mysteryGuest, int sensitiveEquality, int resourceOptimism, int forTestersOnly, int indirectTesting, int generalFixture) {

        this.assertionRoulette = assertionRoulette;
        this.eagerTest = eagerTest;
        this.lazyTest = lazyTest;
        this.mysteryGuest = mysteryGuest;
        this.sensitiveEquality = sensitiveEquality;
        this.resourceOptimism = resourceOptimism;
        this.forTestersOnly = forTestersOnly;
        this.indirectTesting = indirectTesting;
        this.generalFixture = generalFixture;
    }

    public ClassTestSmellsInfo() {}





    public int getAssertionRoulette() {
        return assertionRoulette;
    }

    public void setAssertionRoulette(int assertionRoulette) {
        this.assertionRoulette = assertionRoulette;
    }

    public int getEagerTest() {
        return eagerTest;
    }

    public void setEagerTest(int eagerTest) {
        this.eagerTest = eagerTest;
    }

    public int getLazyTest() {
        return lazyTest;
    }

    public void setLazyTest(int lazyTest) {
        this.lazyTest = lazyTest;
    }

    public int getMysteryGuest() {
        return mysteryGuest;
    }

    public void setMysteryGuest(int mysteryGuest) {
        this.mysteryGuest = mysteryGuest;
    }

    public int getSensitiveEquality() {
        return sensitiveEquality;
    }

    public void setSensitiveEquality(int sensitiveEquality) {
        this.sensitiveEquality = sensitiveEquality;
    }

    public int getResourceOptimism() {
        return resourceOptimism;
    }

    public void setResourceOptimism(int resourceOptimism) {
        this.resourceOptimism = resourceOptimism;
    }

    public int getForTestersOnly() {
        return forTestersOnly;
    }

    public void setForTestersOnly(int forTestersOnly) {
        this.forTestersOnly = forTestersOnly;
    }

    public int getIndirectTesting() {
        return indirectTesting;
    }

    public void setIndirectTesting(int indirectTesting) {
        this.indirectTesting = indirectTesting;
    }

    public int getGeneralFixture() {
        return generalFixture;
    }

    public void setGeneralFixture(int generalFixture) {
        this.generalFixture = generalFixture;
    }

    public double getArMetric() {
        return arMetric;
    }

    public void setArMetric(double arMetric) {
        this.arMetric = arMetric;
    }

    public double getEtMetric() {
        return etMetric;
    }

    public void setEtMetric(double etMetric) {
        this.etMetric = etMetric;
    }

    public double getLtMetric() {
        return ltMetric;
    }

    public void setLtMetric(double ltMetric) {
        this.ltMetric = ltMetric;
    }

    public double getMgMetric() {
        return mgMetric;
    }

    public void setMgMetric(double mgMetric) {
        this.mgMetric = mgMetric;
    }

    public double getSeMetric() {
        return seMetric;
    }

    public void setSeMetric(double seMetric) {
        this.seMetric = seMetric;
    }

    public double getRoMetric() {
        return roMetric;
    }

    public void setRoMetric(double roMetric) {
        this.roMetric = roMetric;
    }

    public double getFtoMetric() {
        return ftoMetric;
    }

    public void setFtoMetric(double ftoMetric) {
        this.ftoMetric = ftoMetric;
    }

    public double getItMetric() {
        return itMetric;
    }

    public void setItMetric(double itMetric) {
        this.itMetric = itMetric;
    }

    public double getGfMetric() {
        return gfMetric;
    }

    public void setGfMetric(double gfMetric) {
        this.gfMetric = gfMetric;
    }
}
