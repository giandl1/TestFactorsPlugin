package data;

public class ClassCKInfo {
    private String name;
    private String belongingPackage;
    private String productionClass;
    private int loc;
    private int rfc;
    private int nom;
    private int wmc;

    public ClassCKInfo(String name, String belongingPackage, String productionClass, int loc, int rfc, int nom, int wmc) {
        this.name = name;
        this.belongingPackage = belongingPackage;
        this.loc = loc;
        this.rfc = rfc;
        this.nom = nom;
        this.wmc = wmc;
        this.productionClass = productionClass;
    }

    public ClassCKInfo(){

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

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getRfc() {
        return rfc;
    }

    public void setRfc(int rfc) {
        this.rfc = rfc;
    }

    public int getNom() {
        return nom;
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public String getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(String productionClass) {
        this.productionClass = productionClass;
    }

    @Override
    public String toString() {
        return "ClassCKInfo{" +
                "name='" + name + '\'' +
                ", belongingPackage='" + belongingPackage + '\'' +
                ", productionClass='" + productionClass + '\'' +
                ", loc=" + loc +
                ", rfc=" + rfc +
                ", nom=" + nom +
                ", wmc=" + wmc +
                '}';
    }
}
