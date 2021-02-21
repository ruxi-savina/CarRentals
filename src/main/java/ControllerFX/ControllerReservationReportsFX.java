package ControllerFX;

import Service.Service;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerReservationReportsFX {
    @FXML
    private Button backRButton;
    @FXML
    private TextField biggestPriceNameField, biggestPricePriceField, mostResNameField, mostResNumberField, biggestPriceCNameField, biggestPriceCPriceField, longestResNameField, longestResNoDaysField, busiestDayField, biggestPriceIDField, longestReservationIDField, busiestNoResField;

    private Scene prevScene;

    private Service service;

    public ControllerReservationReportsFX(){}

    public void setService(Service service){
        this.service = service;
    }

    public void setPrevScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public void biggestPriceRes(){
        if(this.service.getAllReservations().size() == 0){
            showErrorMessage("There are no reservations");
            return;
        }
        this.biggestPriceIDField.setText("" + this.service.biggestPriceResName().getId());
        this.biggestPriceNameField.setText(this.service.biggestPriceResName().getName());
        this.biggestPricePriceField.setText("" + this.service.biggestPriceResName().getPrice());
    }

    public void mostReservations(){
        if(this.service.getAllReservations().size() == 0){
            showErrorMessage("There are no reservations");
            return;
        }
        this.mostResNameField.setText(this.service.mostReservations().getKey());
        this.mostResNumberField.setText("" + this.service.mostReservations().getValue());
    }

    public void biggestPriceClient(){
        if(this.service.getAllReservations().size() == 0){
            showErrorMessage("There are no reservations");
            return;
        }
       this.biggestPriceCNameField.setText(this.service.biggestPriceClient().getKey());
       this.biggestPriceCPriceField.setText("" + this.service.biggestPriceClient().getValue());
    }

    public void longestReservation(){
        if(this.service.getAllReservations().size() == 0){
            showErrorMessage("There are no reservations");
            return;
        }
        this.longestReservationIDField.setText("" + this.service.longestReservation().getId());
        this.longestResNameField.setText("" + this.service.longestReservation().getName());
        this.longestResNoDaysField.setText("" + this.service.longestReservation().getNumberOfDays());
    }

    public void busiestDay(){
        if(this.service.getAllReservations().size() == 0){
            showErrorMessage("There are no reservations");
            return;
        }
        this.busiestDayField.setText("" + this.service.busiestDay().getKey());
        this.busiestNoResField.setText("" + this.service.busiestDay().getValue());
    }

    public void closeCarReports(){
        Stage window = (Stage)backRButton.getScene().getWindow();
        window.setScene(prevScene);
        window.setTitle("Car Rentals");
        window.show();
    }

    //`````````````````````````````````````````````````ShowMessage`````````````````````````````````````````````````//
    void showErrorMessage (String text){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}