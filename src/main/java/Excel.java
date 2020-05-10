import Ford.FordDealer;
import Time.GenerateTime;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
            FordDealer fordDealer = fordDealers.get(i);
            Row row = sheetOne.createRow(i);

            // TODO Style cells
            row.createCell(0)s.setCellValue(GenerateTime.getMMDDYYYYTimeNow());
            row.createCell(1).setCellValue("F"); // F for Ford? See excel sheet example
            row.createCell(2).setCellValue(fordDealer.getRoute());
            row.createCell(3).setCellValue(fordDealer.getZipCode());
            row.createCell(4).setCellValue(fordDealer.getName());
            row.createCell(5).setCellValue(fordDealer.getCity());
            row.createCell(6).setCellValue(fordDealer.getStateCode());
            row.createCell(7).setCellValue(fordDealer.getActualTime());
            row.createCell(8).setCellValue(fordDealer.getExpectedTime());
            row.createCell(9).setCellValue("The storm impact information will be here");
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
