package ControllerFX;

import Service.Service;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerCarReportsFX {

    @FXML
    private TextField  expensiveCarField, expensivePriceField, cheapCarField, cheapPriceField, noFreeField, noOccupiedField, noBrandsField;
    @FXML
    private  Button backButton;

    private Scene prevScene;

    private Service service;

    public ControllerCarReportsFX(){}

    public void setService(Service service){
        this.service = service;
    }

    public void setPrevScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public void mostExpensiveCar(){
        if(service.getAllCars().size() == 0){
            showErrorMessage("There are no cars");
            return;
        }
        expensiveCarField.setText(service.mostExpensiveCar());
        expensivePriceField.setText("" + service.mostExpensivePrice());
    }

    public void cheapestCar(){
        cheapCarField.setText(service.cheapestCar());
        cheapPriceField.setText("" + service.cheapestPrice());
    }

    public void noFreeCars(){
        noFreeField.setText("" + service.freeCars().size());
    }

    public void noOccupiedCars(){
        noOccupiedField.setText("" + service.occupiedCars().size());
    }

    public void noBrands(){
        noBrandsField.setText("" + service.allBrands().size());
    }

    public void closeCarReports(){
        Stage window = (Stage)backButton.getScene().getWindow();
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
