package config;

public class TestSmellMetricThresholds {
    private String name;
    private String description;
    private String id;
    private double yellowThreshold;
    private double redThreshold;
    private double value;

    public TestSmellMetricThresholds(String name, String description, double value) {
        this.name = name;
        this.description = description;
        this.value=value;

    }

    public TestSmellMetricThresholds() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getYellowThreshold() {
        return yellowThreshold;
    }

    public void setYellowThreshold(double yellowThreshold) {
        this.yellowThreshold = yellowThreshold;
    }

    public double getRedThreshold() {
        return redThreshold;
    }

    public void setRedThreshold(double redThreshold) {
        this.redThreshold = redThreshold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
