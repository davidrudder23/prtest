import ClimaCell.Model.PrecipitationAccumulation;
import ClimaCell.WeatherCodes;
import Ford.FordDealer;
import Time.GenerateTime;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.*;

public class Excel {

    public static void generateNewWeatherTemplate(ArrayList<FordDealer> fordDealers) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Setting custom colors
        HSSFPalette palette = workbook.getCustomPalette();

        palette.setColorAtIndex(RED.getIndex(),
                (byte) 255,  //RGB red (0-255)
                (byte) 0,    //RGB green
                (byte) 0     //RGB blue
        );

        palette.setColorAtIndex(LAVENDER.getIndex(),
                (byte) 255,
                (byte) 153,
                (byte) 255
        );

        palette.setColorAtIndex(OLIVE_GREEN.getIndex(),
                (byte) 146,
                (byte) 208,
                (byte) 80
        );

        palette.setColorAtIndex(YELLOW.getIndex(),
                (byte) 255,
                (byte) 0,
                (byte) 0
        );

        //Check if the name is safe to use
        String weatherReportName = WorkbookUtil.createSafeSheetName("Weather Report");
        Sheet weatherReport = workbook.createSheet(weatherReportName);

        String weatherReportDataName = WorkbookUtil.createSafeSheetName("Weather Report Data");
        Sheet weatherReportData = workbook.createSheet(weatherReportDataName);

        try{
            setupWeatherReportSheet(workbook, weatherReport, fordDealers);
            setupWeatherReportDataSheet(workbook, weatherReportData, fordDealers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try (OutputStream fileOut = new FileOutputStream("Ford Weather Report.xls")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
        There is a chance that this will throw an error if for some reason one of
     */
    private static void setupWeatherReportDataSheet(HSSFWorkbook workbook, Sheet sheet, ArrayList<FordDealer> fordDealers) throws ExecutionException, InterruptedException {
        CellStyle cellStyle = workbook.createCellStyle();

        // TODO Create header for Weather Report Data with column names
        for (int i = 1; i < fordDealers.size() + 1; i++) {
            FordDealer fordDealer = fordDealers.get(i - 1);
            Row row = sheet.createRow(i);
            createCell(
                    row,
                    0,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getRoute(),
                    cellStyle);
            createCell(
                    row,
                    1,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getZipCode(),
                    cellStyle);
            createCell(
                    row,
                    2,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getName(),
                    cellStyle);
            createCell(
                    row,
                    3,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getCity(),
                    cellStyle);
            createCell(
                    row,
                    4,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    fordDealer.getStateCode(),
                    cellStyle);

            if (fordDealer.getWeather() != null) {
                //WeatherCode{value='cloudy'}
                createCell(
                        row,
                        5,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        fordDealer.getWeather().getWeatherCode().getValue(),
                        cellStyle
                );

                //temp=[Temp{observationTime='2020-05-13T09:00:00Z', min=Min{value=31.55, units='F'}, max=null}, Temp{observationTime='2020-05-12T19:00:00Z', min=null, max=Max{value=52.04, units='F'}}]
                //min=Min{value=31.55, units='F'}
                createCell(
                        row,
                        6,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        fordDealer.getWeather().getTemp().get(0).getMin().getValue().toString(),
                        cellStyle
                );
                //max=Max{value=52.04, units='F'}
                createCell(
                        row,
                        7,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        fordDealer.getWeather().getTemp().get(1).getMax().getValue().toString(),
                        cellStyle
                );

                //precipitation=[Precipitation{observationTime='2020-05-12T10:00:00Z', max=Max_{value=0.0, units='in/hr'}}]
                createCell(
                        row,
                        6,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        fordDealer.getWeather().getPrecipitation().get(0).getMax().getValue().toString(),
                        cellStyle
                );

                //precipitation_accumulation=PrecipitationAccumulation{value=0.0, units='in'}
                createCell(
                        row,
                        7,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        fordDealer.getWeather().getPrecipitationAccumulation().getValue().toString(),
                        cellStyle
                );
            }else {
                createCell(
                        row,
                        5,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        "There was an issue requesting this data from ClimaCell weather API",
                        cellStyle
                );
            }
        }

        // TODO extract to a func
        for (int i = 0; i < fordDealers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void setupWeatherReportSheet(HSSFWorkbook workbook, Sheet sheet, ArrayList<FordDealer> fordDealers) throws ExecutionException, InterruptedException {

        // TODO Determine why CellStyle not correctly applying individual styles and setting all to H.center, V.center
        CellStyle cellStyle = workbook.createCellStyle();
        for (int i = 0; i < fordDealers.size(); i++) {
            FordDealer fordDealer = fordDealers.get(i);
            Row row = sheet.createRow(i);
            Short fillColor = determineFillColor(fordDealer);
            cellStyle.setFillForegroundColor(fillColor);
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
            createCell(
                    row,
                    9,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    weatherReportInformation(fillColor),
                    cellStyle);
        }

        for (int i = 0; i < fordDealers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void createCell(Row row, int col, HorizontalAlignment HA, VerticalAlignment VA, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cellStyle.setAlignment(HA);
        cellStyle.setVerticalAlignment(VA);
        cell.setCellStyle(cellStyle);
    }

    private static Short determineFillColor(FordDealer fordDealer) throws ExecutionException, InterruptedException {
        // ensures weather has been initialized
        if (fordDealer.getWeather() == null) {
            return OLIVE_GREEN.getIndex();
        }
        String value = fordDealer.getWeather().getWeatherCode().getValue();
        PrecipitationAccumulation precipitationAccumulation = fordDealer.getWeather().getPrecipitationAccumulation();

        /*
         Uses reverse lookup to find constant that matches
         Additionally, these statements should not be merged to reduce amount because ClimaCell returns
         a different code for each of these conditions. If future change of color code or determination
         needs to be done it can be more effectively done here.
        */
        switch (WeatherCodes.get(value)) {
            case RAIN_HEAVY:
                return LAVENDER.getIndex();
            case FREEZING_RAIN:
                return LAVENDER.getIndex();
            case RAIN:
                return OLIVE_GREEN.getIndex();
            case RAIN_LIGHT:
                return OLIVE_GREEN.getIndex();
            case FREEZING_RAIN_HEAVY:
                return YELLOW.getIndex();
            case FREEZING_RAIN_LIGHT:
                return OLIVE_GREEN.getIndex();
            case FREEZING_DRIZZLE:
                return OLIVE_GREEN.getIndex();
            case DRIZZLE:
                return OLIVE_GREEN.getIndex();
            case ICE_PELLETS_HEAVY:
                return YELLOW.getIndex();
            case SNOW:
                return determineFillColorBasedOnAccAmount(precipitationAccumulation.getValue());
            case SNOW_HEAVY:
                return determineFillColorBasedOnAccAmount(precipitationAccumulation.getValue());
            case SNOW_LIGHT:
                return OLIVE_GREEN.getIndex();
            case FLURRIES:
                return OLIVE_GREEN.getIndex();
            case THUNDERSTORM:
                return OLIVE_GREEN.getIndex();
            case FOG_LIGHT:
                return OLIVE_GREEN.getIndex();
            case FOG:
                return OLIVE_GREEN.getIndex();
            case CLOUDY:
                return OLIVE_GREEN.getIndex();
            case MOSTLY_CLOUDY:
                return OLIVE_GREEN.getIndex();
            case PARTLY_CLOUDY:
                return OLIVE_GREEN.getIndex();
            case MOSTLY_CLEAR:
                return OLIVE_GREEN.getIndex();
            case CLEAR:
                return OLIVE_GREEN.getIndex();
            default:
                return OLIVE_GREEN.getIndex();
        }

    }

    private static Short determineFillColorBasedOnAccAmount(Double percipAccum) {
        if (percipAccum < 2) {
            return OLIVE_GREEN.getIndex();
        } else if (percipAccum < 4) {
            return LAVENDER.getIndex();
        } else if (percipAccum < 7) {
            return YELLOW.getIndex();
        } else if (percipAccum >= 7) {
            return RED.getIndex();
        }
        return -1;
    }

//    Olive green  = No current weather delays reported / routes on schedule
//    Light purple = Imminent weather concerns / subject to change as the storm progresses or exits
//          yellow = weather related delays with known degree of delay at this time ( e.g. 1 hr, 2 hrs, ETC.)
//          red    = Route Interruptions, states of emergency, closures, anticipated resumption of routes when
//                   known or when available

    private static String weatherReportInformation(Short colorIndex) {
        if (OLIVE_GREEN.getIndex() == colorIndex) {
            return "No current weather delays reported / routes on schedule";
        } else if (LAVENDER.getIndex() == colorIndex) {
            return "Imminent weather concerns / subject to change as the storm progresses or exits";
        } else if (YELLOW.getIndex() == colorIndex) {
            return "weather related delays with known degree of delay at this time ( e.g. 1 hr, 2 hrs, ETC.)";
        } else if (RED.getIndex() == colorIndex) {
            return "Route Interruptions, states of emergency, closures, anticipated resumption of routes when / known or when available";
        }
        return "There was an issue generating this information. ";
    }
}