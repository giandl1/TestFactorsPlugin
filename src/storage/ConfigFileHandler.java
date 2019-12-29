package storage;

import config.SmellsThresholds;
import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.util.Set;
import java.util.Vector;

public class ConfigFileHandler {
    public TestSmellMetricsThresholdsList readThresholds(File file) {
        try {
            SmellsThresholds thresholds = new SmellsThresholds();
            Ini ini = new Ini(file);
            Vector<TestSmellMetricThresholds> arMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> etMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> ltMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> mgMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> seMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> roMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> ftoMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> itMetrics = new Vector<>();
            Vector<TestSmellMetricThresholds> gfMetrics = new Vector<>();
            TestSmellMetricsThresholdsList list = new TestSmellMetricsThresholdsList();
            for (String sectionName : ini.keySet()) {
                Profile.Section section = ini.get(sectionName);
                if (sectionName.startsWith("AssertionRoulette")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    arMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("EagerTest")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    etMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("GeneralFixture")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    gfMetrics.add(arMetric);
                }

               else if (sectionName.startsWith("SensitiveEquality")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    seMetrics.add(arMetric);
                }

               else if (sectionName.startsWith("ResourceOptimism")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    roMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("LazyTest")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    ltMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("ForTestersOnly")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    ftoMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("MysteryGuest")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    mgMetrics.add(arMetric);
                }

                else if (sectionName.startsWith("IndirectTesting")) {
                    TestSmellMetricThresholds arMetric = new TestSmellMetricThresholds();
                    Set<String> strings = section.keySet();
                    String[] metricValues = strings.toArray(new String[strings.size()]);
                    String id = section.get(metricValues[0]);
                    String name = section.get(metricValues[1]);
                    String description = section.get(metricValues[2]);
                    double yellow = Double.parseDouble(section.get(metricValues[3]));
                    double red = Double.parseDouble(section.get(metricValues[4]));
                    arMetric.setId(id);
                    arMetric.setName(name);
                    arMetric.setDescription(description);
                    arMetric.setYellowThreshold(yellow);
                    arMetric.setRedThreshold(red);
                    itMetrics.add(arMetric);
                }



            }
            list.setArMetrics(arMetrics);
            list.setFtoMetrics(ftoMetrics);
            list.setRoMetrics(roMetrics);
            list.setMgMetrics(mgMetrics);
            list.setLtMetrics(ltMetrics);
            list.setItMetrics(itMetrics);
            list.setSeMetrics(seMetrics);
            list.setGfMetrics(gfMetrics);
            list.setEtMetrics(etMetrics);
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    public void writeThresholds(File file, TestSmellMetricsThresholdsList list){
        String output="";
        int i = 1;
        for(TestSmellMetricThresholds metric : list.getArMetrics()){
            output+="[AssertionRoulette" + i + "]\n";
            output+="id=" + metric.getId() + "\n";
            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getEtMetrics()){
            output+="[EagerTest" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }
        i=1;
        for(TestSmellMetricThresholds metric : list.getMgMetrics()){
            output+="[MysteryGuest" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getSeMetrics()){

            output+="[SensitiveEquality" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getRoMetrics()){

            output+="[ResourceOptimism" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getGfMetrics()){

            output+="[GeneralFixture" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getLtMetrics()){

            output+="[LazyTest" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getFtoMetrics()){

            output+="[ForTestersOnly" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }

        i=1;
        for(TestSmellMetricThresholds metric : list.getItMetrics()){

            output+="[IndirectTesting" + i + "]\n";
            output+="id=" + metric.getId() + "\n";

            output+="name=" + metric.getName() + "\n";
            output+="descr=" + metric.getDescription() + "\n";
            output+="yellow=" + metric.getYellowThreshold() + "\n";
            output+="red=" + metric.getRedThreshold() + "\n\n";
            i++;
        }
        FileUtility.writeFile(output, file.getAbsolutePath());

    }
}
