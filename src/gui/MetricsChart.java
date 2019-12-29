package gui;

import com.intellij.openapi.diagnostic.Logger;
import config.TestSmellMetricThresholds;
import data.TestSmellsMetrics;
import it.unisa.testSmellDiffusion.testSmellRules.TestSmellMetric;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.lines.SeriesLines;
import storage.AnalysisHistoryHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class MetricsChart {
    private static final Logger LOGGER = Logger.getInstance("global");

    private XYChart chart;
    private Vector<TestSmellMetric> metrics;
    private XChartPanel<XYChart> panel;
    private Vector<TestSmellMetricThresholds> thresholds;
    private String className;
    private String path;
    private int year;
    private int month;

    public MetricsChart(Vector<TestSmellMetric> metrics, Vector<TestSmellMetricThresholds> thresholds, String className, String path, int month, int year){
        this.metrics=metrics;
        this.thresholds=thresholds;
        this.className = className;
        this.path = path;
        this.year=year;
        this.month=month;

        panel = new XChartPanel<XYChart>(getChart());

    }

    public XYChart getChart() {
         chart = new XYChart(600,400);
        chart.setTitle("Metrics Evolution");
        chart.setXAxisTitle("Time");
        chart.setYAxisTitle("Metric value");
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setXAxisTicksVisible(false);
        double execs = 1;


        for(TestSmellMetric metric : metrics) {
            ArrayList<Double> xData = new ArrayList<>();
            ArrayList<Double> storic = new AnalysisHistoryHandler().getStoricValues(className, metric.getId(), path + "\\reports", month, year);
            if (storic != null) {
                metric.setStoricValues(storic);
                execs = storic.size();

            for (double i = 0; i <= execs; i++) {
                xData.add(i);
            }
            chart.getStyler().setXAxisMax(execs);
            ArrayList<Double> yData = new ArrayList<>();
            yData.add(0.0);
                for (Double value : storic)
                    yData.add(value);
                String toShow;
                if(metric.getId().equalsIgnoreCase("ar1")){
                    toShow="NONDA";
                }
                else
                    toShow="APCMC";
                chart.addSeries(toShow, xData, yData);


              //  yData.add(metric.getValue());
               // chart.addSeries(metric.getId(), xData, yData);
            }

            double threshold = TestSmellsMetrics.getMetricThreshold(metric.getId(), thresholds, false);
            double criticThreshold = TestSmellsMetrics.getMetricThreshold(metric.getId(),thresholds,true);
            XYSeries series = chart.addSeries("Threshold " + metric.getId(), new double[] {0.0, execs}, new double[]{threshold, threshold} );
            series.setLineColor(Color.YELLOW);
            series.setLineStyle(SeriesLines.DASH_DASH);
            XYSeries seriesCritic = chart.addSeries("Guard Threshold " + metric.getId(), new double[] {0.0, execs}, new double[]{criticThreshold, criticThreshold} );
            seriesCritic.setLineColor(Color.RED);
            seriesCritic.setLineStyle(SeriesLines.DASH_DASH);

        }


        return chart;
    }

    public void setChart(XYChart chart) {
        this.chart = chart;
    }


    public XChartPanel<XYChart> getPanel() {
        return panel;
    }

    public void setPanel(XChartPanel<XYChart> panel) {
        this.panel = panel;
    }
}
