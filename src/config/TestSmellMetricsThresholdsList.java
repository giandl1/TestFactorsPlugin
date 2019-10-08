package config;

import java.util.Vector;

public class TestSmellMetricsThresholdsList {
    private Vector<TestSmellMetricThresholds> arMetrics;
    private Vector<TestSmellMetricThresholds> etMetrics;
    private Vector<TestSmellMetricThresholds> ltMetrics;
    private Vector<TestSmellMetricThresholds> mgMetrics;
    private Vector<TestSmellMetricThresholds> seMetrics;
    private Vector<TestSmellMetricThresholds> roMetrics;
    private Vector<TestSmellMetricThresholds> ftoMetrics;
    private Vector<TestSmellMetricThresholds> itMetrics;
    private Vector<TestSmellMetricThresholds> gfMetrics;

    public Vector<TestSmellMetricThresholds> getArMetrics() {
        return arMetrics;
    }

    public void setArMetrics(Vector<TestSmellMetricThresholds> arMetrics) {
        this.arMetrics = arMetrics;
    }

    public Vector<TestSmellMetricThresholds> getEtMetrics() {
        return etMetrics;
    }

    public void setEtMetrics(Vector<TestSmellMetricThresholds> etMetrics) {
        this.etMetrics = etMetrics;
    }

    public Vector<TestSmellMetricThresholds> getLtMetrics() {
        return ltMetrics;
    }

    public void setLtMetrics(Vector<TestSmellMetricThresholds> ltMetrics) {
        this.ltMetrics = ltMetrics;
    }

    public Vector<TestSmellMetricThresholds> getMgMetrics() {
        return mgMetrics;
    }

    public void setMgMetrics(Vector<TestSmellMetricThresholds> mgMetrics) {
        this.mgMetrics = mgMetrics;
    }

    public Vector<TestSmellMetricThresholds> getSeMetrics() {
        return seMetrics;
    }

    public void setSeMetrics(Vector<TestSmellMetricThresholds> seMetrics) {
        this.seMetrics = seMetrics;
    }

    public Vector<TestSmellMetricThresholds> getRoMetrics() {
        return roMetrics;
    }

    public void setRoMetrics(Vector<TestSmellMetricThresholds> roMetrics) {
        this.roMetrics = roMetrics;
    }

    public Vector<TestSmellMetricThresholds> getFtoMetrics() {
        return ftoMetrics;
    }

    public void setFtoMetrics(Vector<TestSmellMetricThresholds> ftoMetrics) {
        this.ftoMetrics = ftoMetrics;
    }

    public Vector<TestSmellMetricThresholds> getItMetrics() {
        return itMetrics;
    }

    public void setItMetrics(Vector<TestSmellMetricThresholds> itMetrics) {
        this.itMetrics = itMetrics;
    }

    public Vector<TestSmellMetricThresholds> getGfMetrics() {
        return gfMetrics;
    }

    public void setGfMetrics(Vector<TestSmellMetricThresholds> gfMetrics) {
        this.gfMetrics = gfMetrics;
    }

    public double getArMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : arMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
                        }
        return Double.NaN;
    }

    public double getEtMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : etMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getLtMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : ltMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getRoMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : roMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getSeMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : seMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getGfMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : gfMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getFtoMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : ftoMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getItMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : itMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }

    public double getMgMetricThreshold(String id, boolean critic){
        for(TestSmellMetricThresholds thresholds : mgMetrics){
            if(thresholds.getId().equalsIgnoreCase(id)){
                if(critic) return thresholds.getRedThreshold();
                return thresholds.getYellowThreshold();
            }
        }
        return Double.NaN;
    }



}
