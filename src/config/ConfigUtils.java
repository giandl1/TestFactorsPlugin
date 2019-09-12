package config;

import it.unisa.testSmellDiffusion.utility.FileUtility;
import org.ini4j.Ini;

import java.io.File;

public class ConfigUtils {
    public SmellsThresholds readThresholds(File file){
        try {
            SmellsThresholds thresholds = new SmellsThresholds();
            Ini ini = new Ini(file);
            thresholds.setSogliaAR(ini.get("config","sogliaAR", int.class));
            thresholds.setSogliaET(ini.get("config","sogliaET", int.class));
            thresholds.setSogliaMG(ini.get("config","sogliaMG", int.class));
            thresholds.setSogliaSE(ini.get("config","sogliaSE", int.class));
            thresholds.setSogliaLT(ini.get("config","sogliaLT", int.class));
            thresholds.setSogliaRO(ini.get("config","sogliaRO", int.class));
            thresholds.setSogliaFTO(ini.get("config","sogliaFTO", int.class));
            thresholds.setSogliaIT(ini.get("config","sogliaIT", int.class));
            thresholds.setSogliaGF(ini.get("config","sogliaGF", int.class));
            return thresholds;

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void writeThresholds(File file, SmellsThresholds thresholds){
        String output="[config]\n";
        output+="sogliaAR=" + thresholds.getSogliaAR() + "\n";
        output+="sogliaET=" + thresholds.getSogliaET() + "\n";
        output+="sogliaFTO=" + thresholds.getSogliaFTO() + "\n";
        output+="sogliaMG=" + thresholds.getSogliaMG() + "\n";
        output+="sogliaSE=" + thresholds.getSogliaSE() + "\n";
        output+="sogliaGF=" + thresholds.getSogliaGF() + "\n";
        output+="sogliaRO=" + thresholds.getSogliaRO() + "\n";
        output+="sogliaIT=" + thresholds.getSogliaIT() + "\n";
        output+="sogliaLT=" + thresholds.getSogliaLT() + "\n";
        FileUtility.writeFile(output, file.getAbsolutePath());

    }
}
