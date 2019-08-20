package data;

public class ClassTestSmellsInfo {
    private String name;
    private String belongingPackage;
    private int assertionRoulette;
    private int eagerTest;
    private int lazyTest;
    private int mysteryGuest;
    private int sensitiveEquality;
    private int resourceOptimism;
    private int forTestersOnly;
    private int indirectTesting;
    private int duplicateCode;

    public ClassTestSmellsInfo(String name, String belongingPackage, int assertionRoulette, int eagerTest, int lazyTest, int mysteryGuest, int sensitiveEquality, int resourceOptimism, int forTestersOnly, int indirectTesting, int duplicateCode) {
        this.name = name;
        this.belongingPackage = belongingPackage;
        this.assertionRoulette = assertionRoulette;
        this.eagerTest = eagerTest;
        this.lazyTest = lazyTest;
        this.mysteryGuest = mysteryGuest;
        this.sensitiveEquality = sensitiveEquality;
        this.resourceOptimism = resourceOptimism;
        this.forTestersOnly = forTestersOnly;
        this.indirectTesting = indirectTesting;
        this.duplicateCode = duplicateCode;
    }

    public ClassTestSmellsInfo() {}

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

    public int getDuplicateCode() {
        return duplicateCode;
    }

    public void setDuplicateCode(int duplicateCode) {
        this.duplicateCode = duplicateCode;
    }

    @Override
    public String toString() {
        return "ClassTestSmellsInfo{" +
                "name='" + name + '\'' +
                ", belongingPackage='" + belongingPackage + '\'' +
                ", assertionRoulette=" + assertionRoulette +
                ", eagerTest=" + eagerTest +
                ", lazyTest=" + lazyTest +
                ", mysteryGuest=" + mysteryGuest +
                ", sensitiveEquality=" + sensitiveEquality +
                ", resourceOptimism=" + resourceOptimism +
                ", forTestersOnly=" + forTestersOnly +
                ", indirectTesting=" + indirectTesting +
                ", duplicateCode=" + duplicateCode +
                '}';
    }
}
