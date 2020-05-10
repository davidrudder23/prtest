package Location;

import Ford.FordDealer;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class LonLatLocator {
    private final static InputStream UsZipCodes = LonLatLocator.class.getClassLoader().getResourceAsStream("USZipCodesfrom2013GovernmentData.txt");

    /**
     * Currently return a single Location.LonLatZip. Eventually the goal
     * is to return an array of all desired values based on
     * the current value.
     *
     * @param zipCode
     * @return
     */
    public static LonLatZip getWithZipCode(String zipCode){
        LonLatZip lonLatZip = new LonLatZip();
        if(UsZipCodes == null) throw new IllegalArgumentException("File not found");
        BufferedReader br;
        try{
            br = new BufferedReader(new InputStreamReader(UsZipCodes));
            String line;
            while ((line = br.readLine()) != null){
                String[] split = StringUtils.split(line, ",");
                if (split[0].equals(zipCode)){
                    lonLatZip.setZipCode(split[0]);
                    lonLatZip.setLatitude(split[1]);
                    lonLatZip.setLongitude(split[2]);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lonLatZip;
    }
}
