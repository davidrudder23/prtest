import ClimaCell.WeatherCodes;
import Ford.FordDealer;
import Time.GenerateTime;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static org.apache.poi.ss.usermodel.IndexedColors.*;

public class Excel {

    public static void generateNewWeatherTemplate(ArrayList<FordDealer> fordDealers) {
        Workbook workbook = new HSSFWorkbook();

        //Check if the name is safe to use
        String safeName = WorkbookUtil.createSafeSheetName("Weather Report");
        Sheet sheetOne = workbook.createSheet(safeName);

        // TODO Determine why CellStyle not correctly applying individual styles and setting all to H.center, V.center
        CellStyle cellStyle = workbook.createCellStyle();

        for (int i = 0; i < fordDealers.size(); i++) {
            FordDealer fordDealer = fordDealers.get(i);
            Row row = sheetOne.createRow(i);
//            cellStyle.setFillBackgroundColor(LIGHT_GREEN.getIndex());
            cellStyle.setFillForegroundColor(OLIVE_GREEN.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            row.setRowStyle(cellStyle);
            createCell(
                    row,
                    0,
                    HorizontalAlignment.RIGHT,
                    VerticalAlignment.CENTER,
                    GenerateTime.getMMDDYYYYTimeNow(),
                    cellStyle
            );
            createCell(
                    row,
                    1,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    "F",
                    cellStyle);
            createCell(
                    row,
                    2,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getRoute(),
                    cellStyle);
            createCell(
                    row,
                    3,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getZipCode(),
                    cellStyle);
            createCell(
                    row,
                    4,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getName(),
                    cellStyle);
            createCell(
                    row,
                    5,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getCity(),
                    cellStyle);
            createCell(
                    row,
                    6,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getStateCode(),
                    cellStyle);
            createCell(
                    row,
                    7,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getActualTime(),
                    cellStyle);
            createCell(
                    row,
                    8,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getExpectedTime(),
                    cellStyle);
            // TODO write function to determine weather Status
            createCell(
                    row,
                    9,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    "The storm impact information will be here",
                    cellStyle);

>>>>>>> af605dc38d60e31b3a711e96a7630db721a3142f
        }

        for (int i = 0; i < fordDealers.size(); i++) {
            sheetOne.autoSizeColumn(i);
        }

        try (OutputStream fileOut = new FileOutputStream("Ford Weather Report.xls")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createCell(Row row, int col, HorizontalAlignment HA, VerticalAlignment VA, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cellStyle.setAlignment(HA);
        cellStyle.setVerticalAlignment(VA);
        cell.setCellStyle(cellStyle);
    }



    private static Short determineFillColor(FordDealer fordDealer){
        String weatherCode = fordDealer.getWeather().getWeatherCode().getValue();
        fordDealer.getWeather().getPrecipitationAccumulation();

        /*
        Color table:
        Olive green  = No current weather delays reported / routes on schedule
        Light purple = Iminent weather concerns / subject to change as the storm progresses or exits
              yellow = weather related delays with known degree of delay at this time ( e.g. 1 hr, 2 hrs, ETC.)
              red    = Route Interruptions, states of emergency, closures, anticipated resumption of routes when
              known or when available

         */

        // Uses reverse lookup to find contant that matches
        switch (WeatherCodes.get(weatherCode)){
            case RAIN_HEAVY: return 0;
            case FREEZING_RAIN: return 0;
            case FREEZING_RAIN_HEAVY: return 0;
            case ICE_PELLETS_HEAVY: return 0;
            case SNOW: return 0;
            case SNOW_HEAVY:return 0;
            default: return OLIVE_GREEN.getIndex();
        }

    }
}
