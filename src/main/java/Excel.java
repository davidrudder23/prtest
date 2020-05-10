import ClimaCell.Model.WeatherCode;
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

import static ClimaCell.WeatherCodes.*;

public class Excel {

    public static void generateNewWeatherTemplate(ArrayList<FordDealer> fordDealers) {
        Workbook workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        //Check if the name is safe to use
        String safeName = WorkbookUtil.createSafeSheetName("Weather Report");
        Sheet sheetOne = workbook.createSheet(safeName);

        // TODO Determine why CellStyle not correctly applying individual styles and setting all to H.center, V.center
        CellStyle cellStyle = workbook.createCellStyle();
        for (int i = 0; i < fordDealers.size(); i++) {
            FordDealer fordDealer = fordDealers.get(i);
            Row row = sheetOne.createRow(i);
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

    private static String determineFillColor(FordDealer fordDealer){
        String weatherCode = fordDealer.getWeather().getWeatherCode().getValue();
        fordDealer.getWeather().getPrecipitationAccumulation();

        // Uses reverse lookup to find contant that matches
        switch (WeatherCodes.get(weatherCode)){
            case RAIN_HEAVY: return "";
            case FREEZING_RAIN: return "";
            case FREEZING_RAIN_HEAVY: return "";
            case ICE_PELLETS_HEAVY: return "";
            case SNOW: return "";
            case SNOW_HEAVY:return "";
            default:
                throw new IllegalStateException("Unexpected value: " + weatherCode);
        }

    }
}
