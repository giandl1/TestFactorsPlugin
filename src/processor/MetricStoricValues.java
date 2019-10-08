package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MetricStoricValues {

    public ArrayList<Double> getStoricValues(String className, String id, String path, int month, int year) {
        File reportPath = new File(path);
        ArrayList<Double> values = new ArrayList<>();
        File[] files;
        files = reportPath.listFiles();
        ArrayList<File> filtered = new ArrayList<>();
        for(File file: files){
            if(file.isFile()) {
                String fileName = file.getName();
                String fileMonthS = fileName.substring(4, 6);
                if (fileMonthS.startsWith("0"))
                    fileMonthS = fileMonthS.substring(1, 2);
                int fileMonth = Integer.parseInt(fileMonthS);
                int fileYear = Integer.parseInt(fileName.substring(0, 4));
                if ((fileYear == year && fileMonth >= month) || (fileYear > year))
                    filtered.add(file);
            }
        }
        if(filtered.size() == 0)
            return null;

      /*  for(File file : files){
            String name = file.getName();
            int fileMonth = Integer.parseInt(name.substring(4,5));
            int fileYear = Integer.parseInt(name.substring(0,3));
            if(fileYear==year && fileMonth==month)
                filtered.add(file);
        }*/

        for (File file : filtered) {
            String line = "";
            String cvsSplitBy = ";";
            try {

                BufferedReader br = new BufferedReader(new FileReader(file));
                br.readLine();
                line=br.readLine();
                String [] data = line.split(cvsSplitBy);
                boolean metricFound=false;
                int i=0;
                int pos=-1;
                for(String header : data){
                    if(header.equalsIgnoreCase(id)) {
                        metricFound = true;
                            pos=i;
                    }
                    i++;
                }
                if(metricFound) {
                    while ((line = br.readLine()) != null) {
                         data = line.split(cvsSplitBy);
                        if (data[0].equalsIgnoreCase(className)) {
                            values.add(Double.parseDouble(data[pos]));

                        }


                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return values;
    }
}