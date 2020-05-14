package Location;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;

//TODO Create a 'cache' for most usedZipCodes

public class LonLatLocator {
    private final static URL UsZipCodes = LonLatLocator.class.getClassLoader().getResource("USZipCodesfrom2013GovernmentData.txt");

    /**
     * Currently return a single Location.LonLatZip. Eventually the goal
     * is to return an array of all desired values based on
     * the current value.
     *
     * @param zipCode
     * @return
     */

    public static LonLatZip getWithZipCode(String zipCode) {
        LonLatZip lonLatZip = new LonLatZip();
        if (UsZipCodes == null) throw new IllegalArgumentException("File not found");
        BufferedReader br;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(String.valueOf(UsZipCodes.getFile()).replaceAll("%20", " "));
            br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = StringUtils.split(line, ",");
                if (split[0].equals(zipCode)) {
                    lonLatZip = new LonLatZip(split[0], split[1], split[2]);
                }
            }
            fileInputStream.getChannel().position(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lonLatZip;
    }
}
