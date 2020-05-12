package Ford;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class FordDealerInformation {
    private final static InputStream fordDealershipInformation = FordDealerInformation.class.getClassLoader().getResourceAsStream("DealerInformation.csv");
    private final static URL fordDealerLocationInformation = FordDealerInformation.class.getClassLoader().getResource("Hartford CMT Routing V 49.csv");

    public static ArrayList<FordDealer> loadFordData() {
        ArrayList<FordDealer> fordDealers = new ArrayList<>();
        if (fordDealershipInformation == null) throw new IllegalArgumentException();
        BufferedReader br;
        try {
            // Must have charsetName set to UTF-8 to remove ï»¿ from beginning of .csv file
            br = new BufferedReader(new InputStreamReader(fordDealershipInformation, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                System.out.println(split[7]);
                fordDealers.add(new FordDealer(
                        split[0],
                        split[1],
                        split[2],
                        split[3],
                        split[4],
                        split[5],
                        split[6],
                        split[7] // ZipCode
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fordDealers;
    }

    /*
    This method was utilized to aggregate needed data from two different files matching on a dealer code
    it will not be utilized in production but could be useful at a later date and will be left here for that
    purpose.

     */
    public static void appendZipCodeToDI(){
        try {
            if (fordDealershipInformation == null || fordDealerLocationInformation == null) throw new IllegalArgumentException();
            FileInputStream fileInputStream = new FileInputStream(String.valueOf(fordDealerLocationInformation.getFile()).replaceAll("%20", " "));
            BufferedReader info = new BufferedReader(new InputStreamReader(fileInputStream));
            BufferedReader br = new BufferedReader(new InputStreamReader(fordDealershipInformation, "UTF-8"));

            PrintWriter writer = new PrintWriter("DealerInformation.csv", "UTF-8");

            String line, temp;
            String[] split, tSplit;
            while ((line = br.readLine()) != null){
                // match on Dealer code column 2 in DealerInformation
                split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                while ((temp = info.readLine()) != null){
                    // Match on dealer code column 3 in Hartford CMT Routing V 49.csv
                    tSplit = temp.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (tSplit[2].equals(split[1]) ){
                        //&& tSplit[8].equals(split[4]) && tSplit[9].equals(split[5])
                        System.out.println("found");
                        writer.println(split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+","+split[6]+","+tSplit[10]);
                        writer.flush();
                        fileInputStream.getChannel().position(0);
                        break;
                    }
                }

            }

            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}