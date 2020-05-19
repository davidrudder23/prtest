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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

// TODO Clean code up
// TODO add additional error handling
// TODO test to ensure all alerts are correctly displaying

public class ReportUI extends Application {

    private ProgressBar progressBar = new ProgressBar(0);

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox();

        Label label = new Label("The report is loading this will take a moment... ");
        Button generateReportBtn = new Button("Generate Report");
        Button exportReport = new Button("Export");

        vBox.paddingProperty().setValue(new Insets(5, 5, 5, 5));
        label.paddingProperty().setValue(new Insets(5, 0, 5, 0));

        vBox.alignmentProperty().setValue(Pos.CENTER);

        // TODO use add all it will look cleaner
        vBox.getChildren().addAll(label, progressBar,generateReportBtn,exportReport);

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
            handleGenerateReportButton(label, progressBar, generateReportBtn);
        });

        DirectoryChooser dirChooser = new DirectoryChooser();
        exportReport.setOnAction((event -> {
            dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = dirChooser.showDialog(primaryStage);
            if (file.exists()) {
                try {
                    File excelReport = new File("src/main/resources/ExcelFiles/Ford Weather Report.xls");
                    FileUtils.copyFile(excelReport, new File(file.getPath()+"/Ford Weather Report.xls"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public void handleGenerateReportButton(Label label, ProgressBar progressBar, Button generateReportBtn) {
        loading(label, progressBar, generateReportBtn);
        try {
            Callable<Boolean> genReport = () -> {
                // Returns true when done
                return generateReport();
            };

            FutureTask<Boolean> booleanFutureTask = new FutureTask<>(genReport);
            Thread thread = new Thread(booleanFutureTask);
            thread.start();
            Boolean successful = booleanFutureTask.get();

            // TODO generalize alerts
            if (successful) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The Report was successfully generated. ");
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have tried to generate a new report to soon. ", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.show();
            }
            doneLoading(label, progressBar, generateReportBtn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loading(Label label, ProgressBar progressBar, Button generateReportBtn) {
        label.setManaged(true);
        label.setVisible(true);
        progressBar.setManaged(true);
        progressBar.setVisible(true);
        generateReportBtn.setManaged(false);
        generateReportBtn.setVisible(false);
    }

    public void doneLoading(Label label, ProgressBar progressBar, Button generateReportBtn) {
        label.setManaged(false);
        label.setVisible(false);
        progressBar.setManaged(false);
        progressBar.setVisible(false);
        generateReportBtn.setManaged(true);
        generateReportBtn.setVisible(true);
    }

    public boolean generateReport() {
        // Get all ford data from DealerInformation.csv
        ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();

        // Get all longitude and latitude from USZipCodesFrom2013GovernmentData
        for (FordDealer fordDealer : fordDealers) {
            fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
        }

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
            /*
            RunTime.getRuntime().availableProcessors will return the number of processors a system
            has available based on hardware.
             */
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
                for (FordDealer fordDealer : fordDealers) {

                    Callable<ClimaCell> dailyClimaData = new ClimaCellData(
                            GenerateTime.getISO8601TimeNow(),
                    /*
                     The replace is because some longitudes have white space in front of the string.
                     This causes a %20 in request URL which will generate a bad request.
                   `*/
                            fordDealer.getLonLatZip().getLongitude().replace(" ", ""),
                            fordDealer.getLonLatZip().getLatitude());
                    Future<ClimaCell> submit = executor.submit(dailyClimaData);
                    try {
                        fordDealer.setWeather(submit.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                executor.shutdown();
                TimeTracker.saveCurrentExecutionTime();
            } else {
                return false;
            }
        }
        Excel.generateNewWeatherTemplate(fordDealers);
        return true;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
