package UI;

import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Excel.Excel;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;
import Time.TimeTracker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.*;

// TODO Add CSS

public class ReportUI extends Application {

    private static ProgressBar progressBar = new ProgressBar();

    public static boolean generateReport() {
        long lastExecutionTime = TimeTracker.readLastExecutionTime();
        if (lastExecutionTime == -1) {
            System.err.println("There was an error reading ExecutionTime.txt ");
        } else {
            /*
            Checking if at minimum an hour has passed since the last time this program
            attempted to generate the weather utilizing the ClimaCell API. This tracks
            solely for the purpose of not running the application when the rate limit
            has already been reached for an hour. 100 calls/hr x2 since there is 2 keys
             */
            if ((GenerateTime.getUnixTime() - lastExecutionTime) > 3600) {
                // Get all ford data from DealerInformation.csv
                ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();

                // Get all longitude and latitude from USZipCodesFrom2013GovernmentData
                for (FordDealer fordDealer : fordDealers) {
                    fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
                }

                /*
                RunTime.getRuntime().availableProcessors will return the number of processors a system
                has available based on hardware.
                 */
                ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (int i = 0; i < fordDealers.size(); i++) {
                    FordDealer fordDealer = fordDealers.get(i);
//                    progressBar.setProgress(i/fordDealers.size());
                    /*
                    TODO increment progress bar here
                     */

                    Callable<ClimaCell> dailyClimaData = new ClimaCellData(
                            GenerateTime.getISO8601TimeNow(),
                             /*
                             The replace is because some longitudes have white space in front of the string.
                             This causes a %20 in request URL which will generate a bad request.
                             */
                            fordDealer.getLonLatZip().getLongitude().replace(" ", ""),
                            fordDealer.getLonLatZip().getLatitude());
                    Future<ClimaCell> submit = executorService.submit(dailyClimaData);
                    try {
                        fordDealer.setWeather(submit.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeTracker.saveCurrentExecutionTime();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Excel.generateNewWeatherTemplate(fordDealers);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox();

        Label label = new Label("The report is loading this will take a moment... ");
        Button generateReportBtn = new Button("Generate Report");
        Button exportReport = new Button("Export");

        vBox.paddingProperty().setValue(new Insets(5, 5, 5, 5));
        label.paddingProperty().setValue(new Insets(5, 0, 5, 0));

        vBox.alignmentProperty().setValue(Pos.CENTER);

        vBox.getChildren().addAll(label, progressBar, generateReportBtn, exportReport);

        VBox.setMargin(label, new Insets(2));
        VBox.setMargin(progressBar, new Insets(0, 5, 5, 5));
        VBox.setMargin(generateReportBtn, new Insets(5, 0, 5, 0));
        VBox.setMargin(exportReport, new Insets(5, 0, 5, 0));

        Scene scene = new Scene(vBox);
        primaryStage.setTitle("Ford Weather Application");
        primaryStage.setScene(scene);
        primaryStage.setWidth(250);
        primaryStage.setHeight(150);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();

        vBox.managedProperty().bind(label.visibleProperty());
        vBox.managedProperty().bind(progressBar.visibleProperty());
        vBox.managedProperty().bind(generateReportBtn.visibleProperty());
        vBox.managedProperty().bind(exportReport.visibleProperty());

        label.setVisible(false);
        progressBar.setVisible(false);

        label.setManaged(false);
        progressBar.setManaged(false);

        generateReportBtn.setOnAction((event) -> {
            handleGenerateReportButton(label, progressBar, generateReportBtn, exportReport);
        });

        exportReport.setOnAction(event -> {
            handleExportButton(primaryStage, event);
        });

    }

    private void handleExportButton(Stage primaryStage, ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = dirChooser.showDialog(primaryStage);
        if (file != null) {
            if (file.exists()) {
                try {
                    URL resource = ReportUI.class.getClassLoader().getResource("ExcelFiles/Ford Weather Report.xls");
                    assert resource != null;
                    File excelReport = new File(resource.toURI());
                    if (excelReport == null) {
                        Platform.runLater(()->alertBox(AlertType.WARNING, "The report you are trying to export does no exist. You must first generate a new report."));
                    } else {
                        FileUtils.copyFile(excelReport, new File(file.getPath() + "/Ford Weather Report.xls"));
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleGenerateReportButton(Label label, ProgressBar progressBar, Button reportBtn, Button generateReportBtn) {


        Thread thread = new Thread(() -> {
            loading(label, progressBar, reportBtn, generateReportBtn);
            boolean result = generateReport();
            if (result){
                Platform.runLater(() -> alertBox(AlertType.CONFIRMATION, "The weather report was successfully generated. "));
            }else {
                Platform.runLater(() -> alertBox(AlertType.ERROR, "It has been to soon since you generated a report. "));
            }
            doneLoading(label, progressBar, reportBtn, generateReportBtn);
        });
        // Don't allow this thread to prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

    private void loading(Label label, ProgressBar progressBar, Button reportBtn, Button generateReportBtn) {
        label.setManaged(true);
        label.setVisible(true);
        progressBar.setManaged(true);
        progressBar.setVisible(true);
        reportBtn.setManaged(false);
        reportBtn.setVisible(false);
        generateReportBtn.setManaged(false);
        generateReportBtn.setVisible(false);
    }

    private void doneLoading(Label label, ProgressBar progressBar, Button reportBtn, Button generateReportBtn) {
        label.setManaged(false);
        label.setVisible(false);
        progressBar.setManaged(false);
        progressBar.setVisible(false);
        reportBtn.setVisible(true);
        reportBtn.setManaged(true);
        generateReportBtn.setManaged(true);
        generateReportBtn.setVisible(true);
    }

    private void alertBox(AlertType alertType, String info) {
        Alert alert = new Alert(alertType, info, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.show();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        ReportUI.progressBar = progressBar;
    }
}
