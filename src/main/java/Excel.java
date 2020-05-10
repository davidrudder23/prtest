import Ford.FordDealer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Excel {

    public static void generateNewWeatherTemplate(ArrayList<FordDealer> fordDealers){
        //Create new excel workbook
        Workbook workbook = new HSSFWorkbook();

        //Check if the name is safe to use
        String safeName = WorkbookUtil.createSafeSheetName("Weather Report");
        Sheet sheetOne = workbook.createSheet(safeName);

        for (int i = 0; i < fordDealers.size(); i++) {
            sheetOne.createRow(i);

        }


        // write workbook out to file? with name "Ford Weather Report"
        try (OutputStream fileOut = new FileOutputStream("Ford Weather Report.xls")){
            workbook.write(fileOut);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
