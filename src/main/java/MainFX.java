import ControllerFX.ControllerMainFX;
import Repository.CarRepositoryTextFile;
import Repository.ReservationRepositoryTextFile;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {
    static public String carFileName;
    static public String reservationFileName;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            root = loader.load();
            ControllerMainFX controller = loader.getController();
            controller.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Car Rentals");
            primaryStage.show();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    //----------------------------------------------------GetService----------------------------------------------------//
    static Service getService(){
        readFile();
        CarRepositoryTextFile carRepository = new CarRepositoryTextFile(carFileName);
        ReservationRepositoryTextFile reservationRepository = new ReservationRepositoryTextFile(reservationFileName);
        Service service = new Service(carRepository, reservationRepository);
        service.occupyCars();
        return service;
    }

    //----------------------------------------------------Read From File----------------------------------------------------//
    public static void readFile(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("CarRentalsApp.properties"));
            carFileName=properties.getProperty("CarFile");
            if (carFileName==null){ //the property does not exist in the file
                carFileName="CarTextFile.txt";
                System.err.println("Requests file not found. Using default"+carFileName);
            }
            reservationFileName=properties.getProperty("ReservationFile");
            if (reservationFileName==null){
                reservationFileName="RepairedForms.txt";
                System.err.println("RepairedForms file not found. Using default"+reservationFileName);
            }
        }catch (IOException ex){
            System.err.println("Error reading the configuration file"+ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
