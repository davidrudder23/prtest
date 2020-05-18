import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;
import Time.TimeTracker;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class main extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO determine how to run fx on a separate thread

        VBox vBox = new VBox();

        Label label = new Label("The report is loading this will take a moment... ");
        ProgressBar progressBar = new ProgressBar(0);
        Button generateReportBtn = new Button(" Generate Report & Export ");

        vBox.paddingProperty().setValue(new Insets(5,5,5,5));
        label.paddingProperty().setValue(new Insets(5,0,5,0));

        vBox.alignmentProperty().setValue(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.getChildren().add(progressBar);
        vBox.getChildren().add(generateReportBtn);

        vBox.setMargin(label, new Insets(2));
        vBox.setMargin(progressBar, new Insets(0, 5,5,5));
        vBox.setMargin(generateReportBtn, new Insets(5,0,5,0));

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

        label.setVisible(false);
        progressBar.setVisible(false);

        label.setManaged(false);
        progressBar.setManaged(false);

        generateReportBtn.setOnAction((event) -> {
            handleGenerateReportButton(label, progressBar, generateReportBtn);
        });
    }

    public void handleGenerateReportButton(Label label, ProgressBar progressBar, Button generateReportBtn) {
        loading(label, progressBar, generateReportBtn);
        Task handleGenerateReport = null;
        try {
            handleGenerateReport = new Task<Task>() {
                @Override
                protected Task call() throws Exception {
    //                generateReport();
                    wasteTime();
                    return null;
                }
            }.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        doneLoading(label,progressBar,generateReportBtn);
        }

    private void wasteTime() throws InterruptedException {
        Thread.sleep(10000);
    }

    public void loading(Label label, ProgressBar progressBar, Button generateReportBtn){
        label.setManaged(true);
        label.setVisible(true);
        progressBar.setManaged(true);
        progressBar.setVisible(true);
        generateReportBtn.setManaged(false);
        generateReportBtn.setVisible(false);
    }

    public void doneLoading(Label label, ProgressBar progressBar, Button generateReportBtn){
        label.setManaged(false);
        label.setVisible(false);
        progressBar.setManaged(false);
        progressBar.setVisible(false);
        generateReportBtn.setManaged(true);
        generateReportBtn.setVisible(true);
    }

    public void generateReport() {
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
            System.out.println(GenerateTime.getUnixTime());
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
                        System.out.println(submit.get());
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
                // TODO replace with something more meaningful to client e.g., popup window with notification
                System.out.println("there was an error running the program");
            }
        }
        Excel.generateNewWeatherTemplate(fordDealers);
    }
}