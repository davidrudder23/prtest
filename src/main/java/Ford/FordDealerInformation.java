package Ford;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FordDealerInformation {
    private final static InputStream fordDealershipInformation = FordDealerInformation.class.getClassLoader().getResourceAsStream("DealerInformation.csv");

    public static ArrayList<FordDealer> loadFordData() {
        ArrayList<FordDealer> fordDealers = new ArrayList<>();
        if (fordDealershipInformation == null) throw new IllegalArgumentException();
        BufferedReader br;
        try {
            // Must have charsetName set to UTF-8 to remove ï»¿ from beginning of .csv file
            br = new BufferedReader(new InputStreamReader(fordDealershipInformation, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null){
                String[] split = StringUtils.split(line, ",");
                fordDealers.add(new FordDealer(
                        split[0],
                        split[1],
                        split[2],
                        split[3],
                        split[4],
                        split[5],
                        split[6]
                        ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fordDealers;
    }
}

//Q3-15,00716,RILEY FORD INC,CHAZY,NY,10:00 AM,10:00 AM