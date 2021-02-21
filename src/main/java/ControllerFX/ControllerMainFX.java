package ControllerFX;

import Model.Car;
import Model.Date;
import Model.Hour;
import Model.Reservation;
import Service.Service;
import com.sun.javafx.fxml.builder.URLBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerMainFX {
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableView<Reservation> reservationTableView;
    @FXML
    private TextField idCField, brandField, colourField, typeField, priceField;
    @FXML
    private TextField nameField, hourField, noDaysField;
    @FXML
    private TextField nameSearchField, carSearchField;
    @FXML
    private Label idRLabel;
    @FXML
    private ComboBox<String> carComboBox;
    @FXML
    private DatePicker datePicker, datePickerSearch;
    @FXML
    private Button reportsCarButton;
    @FXML
    private Button reportsReservationButton;
    @FXML
    private CheckBox allCheckBox, freeCheckBox, occupiedCheckBox, priceCheckBox, brandCheckBox;
    @FXML
    private CheckBox allRCheckBox, nameCheckBox, carCheckBox, dateCheckBox;
    @FXML
    private Spinner<Integer> priceMinSpinner;
    @FXML
    private Spinner<Integer> priceMaxSpinner;
    @FXML
    private ChoiceBox<String> brandChoiceBox;
    @FXML
    private TableView<CustomImageView> recycleBinTableView;
    @FXML
    private TableView<CustomImageView> recycleBinTableView2;

    private final ObservableList<Car> carList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private final ObservableList<CustomImageView> recycleBinList = FXCollections.observableArrayList();
    private final ObservableList<CustomImageView> recycleBinList2 = FXCollections.observableArrayList();
    private final ObservableList<String> reservationCarsList = FXCollections.observableArrayList();
    private final ObservableList<String> brandList = FXCollections.observableArrayList();

    DatePickerConverter dateConverter = new DatePickerConverter();

    private Service service;

    public ControllerMainFX() {
    }

    @FXML
    public void initialize() {
        carTableView.setItems(carList);
        reservationTableView.setItems(reservationList);

        recycleBinTableView.setItems(recycleBinList);
        recycleBinTableView2.setItems(recycleBinList2);
        carTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem)->showCar(newItem));
        reservationTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem)->showReservation(newItem));
        brandChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem)->filterBrandCarsListen(newItem));

        allCheckBox.setSelected(true);
        allRCheckBox.setSelected(true);

        carComboBox.setItems(reservationCarsList);

        brandChoiceBox.setItems(brandList);

        datePicker.setConverter(dateConverter);
        datePicker.setPromptText(LocalDate.now().format(dateConverter.getDtFormatter()));
        datePickerSearch.setConverter(dateConverter);
        datePickerSearch.setValue(LocalDate.now());
    }

    public void setService(Service service) {
        this.service = service;
        carList.addAll(service.getCarList());
        reservationList.addAll(service.getReservationList());
        updateCarComboBox();
        setRecycleBinList();
        setRecycleBinList2();

        brandList.add("Choose brand...");
        brandList.addAll(service.allBrands());
        brandChoiceBox.setValue(brandList.get(0));

        idRLabel.setText("" + service.getIdGenerator());
    }

    //`````````````````````````````````````````````````Cars`````````````````````````````````````````````````//
    private void showCar(Car car) {
        if (car == null)
            clearFieldsCar();
        else{
            idCField.setText(car.getId());
            brandField.setText(car.getBrand());
            colourField.setText(car.getColour());
            typeField.setText(car.getType());
            priceField.setText("" + car.getPrice());
        }
    }

    @FXML
    private void clearFieldsCar() {
        idCField.setText("");
        brandField.setText("");
        colourField.setText("");
        typeField.setText("");
        priceField.setText("");
    }

    //----------------------------------------------------CRUD----------------------------------------------------//
    @FXML
    private void addCar() throws Exception {
        String id = idCField.getText();
        String brand = brandField.getText();
        String colour = colourField.getText();
        String type = typeField.getText();
        String priceS = priceField.getText();

        if (id.equals("") || colour.equals("") || brand.equals("") || type.equals("") || priceS.equals("")) {
            showErrorMessage("All the fields must be completed");
            return;
        }
        try {
            int price = Integer.parseInt(priceS);
            service.addCar(id, brand, colour, type, price);
            carList.setAll(service.getAllCars());
            setRecycleBinList();
            updateCarComboBox();
            showNotification("Car successfully added! ", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException ex) {
            showErrorMessage("The price should be an integer");
        } catch (Exception exception) {
            showErrorMessage(exception.getMessage());
        }
    }

    @FXML
    private void deleteCar() throws Exception {
        String id = idCField.getText();

        if (id.equals("")) {
            showErrorMessage("No car selected");
            return;
        }
        try {
            service.deleteCar(id);
            carList.setAll(service.getAllCars());
            setRecycleBinList();
            updateCarComboBox();
            showNotification("Car successfully deleted! ", Alert.AlertType.INFORMATION);
        } catch (Exception exception) {
            showErrorMessage(exception.getMessage());
        }
    }

    public void deleteCarRecycleBin(){
        int row = recycleBinTableView.getSelectionModel().getSelectedIndex();
        if(row != -1){
            String licencePlate = carTableView.getItems().get(row).getId();
            try {
                service.deleteCar(licencePlate);
                carList.remove(row);
                setRecycleBinList();
                updateCarComboBox();
                showNotification("Car successfully deleted!", Alert.AlertType.INFORMATION);
            } catch (Exception exception) {
                showErrorMessage(exception.getMessage());
            }
        }
    }

    @FXML
    private void updateCar() {
        String id = idCField.getText();
        String brand = brandField.getText();
        String colour = colourField.getText();
        String type = typeField.getText();
        String priceS = priceField.getText();

        if (id.equals("") || colour.equals("") || brand.equals("") || type.equals("") || priceS.equals("")) {
            showErrorMessage("All the fields must be completed");
            return;
        }

        try {
            int price = Integer.parseInt(priceS);
            service.updateCar(id, brand, colour, type, price);
            carList.setAll(service.getAllCars());
            showNotification("Car successfully updated! ", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException ex) {
            showErrorMessage("The price should be an integer");
        } catch (Exception exception) {
            showErrorMessage(exception.getMessage());
        }
    }

//----------------------------------------------------Filter----------------------------------------------------//
    public void filterAllCars() {
        freeCheckBox.setSelected(false);
        occupiedCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        brandCheckBox.setSelected(false);
        carList.setAll(service.getAllCars());
        setRecycleBinList();
    }

    public void filterFreeCars() {
        allCheckBox.setSelected(false);
        occupiedCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        brandCheckBox.setSelected(false);
        carList.setAll(service.freeCars());
        setRecycleBinList();
    }

    public void filterOccupiedCars() {
        allCheckBox.setSelected(false);
        freeCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        brandCheckBox.setSelected(false);
        carList.setAll(service.occupiedCars());
        setRecycleBinList();
    }

    public void filterPriceCars() {
        allCheckBox.setSelected(false);
        freeCheckBox.setSelected(false);
        occupiedCheckBox.setSelected(false);
        brandCheckBox.setSelected(false);
        carList.setAll(service.carsBetweenPrice(priceMinSpinner.getValue(), priceMaxSpinner.getValue()));
        setRecycleBinList();
    }

    public void filterBrandCarsListen(String brand){
        if(brandCheckBox.isSelected()) {
            allCheckBox.setSelected(false);
            freeCheckBox.setSelected(false);
            occupiedCheckBox.setSelected(false);
            priceCheckBox.setSelected(false);
            carList.setAll(service.carsByBrand(brand));
            setRecycleBinList();
        }
    }

    public void filterBrandCars(){
        allCheckBox.setSelected(false);
        freeCheckBox.setSelected(false);
        occupiedCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        carList.setAll(service.carsByBrand(brandChoiceBox.getValue()));
        setRecycleBinList();
    }

    public void backToFilterAll(){
        allCheckBox.setSelected(true);
        freeCheckBox.setSelected(false);
        occupiedCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        brandCheckBox.setSelected(false);
        carList.setAll(service.getAllCars());
        setRecycleBinList();
    }

//----------------------------------------------------CarComboBox----------------------------------------------------//
    public void updateCarComboBox(){
        List<String> freeCars = service.freeCars().stream().map(Car::getId).collect(Collectors.toList());
        reservationCarsList.setAll(freeCars);
    }


    //----------------------------------------------------RecycleBin----------------------------------------------------//
    public void setRecycleBinList() {
        recycleBinList.clear();
        for (Car var : carList) {
            CustomImageView imageView = new CustomImageView("recycleBin.png");
            recycleBinList.add(imageView);
        }
    }

//----------------------------------------------------Open reports----------------------------------------------------//
    public void openCarReports(){
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../CarReports.fxml"));
            root = loader.load();
            ControllerCarReportsFX controller = loader.getController();
            controller.setService(service);
            controller.setPrevScene(reportsCarButton.getScene());
            Scene scene = new Scene(root);
            Stage window = (Stage)reportsCarButton.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Car Rentals/Car Reports");
            window.show();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    //`````````````````````````````````````````````````Reservation`````````````````````````````````````````````````//
    private void showReservation(Reservation reservation) {
        if (reservation == null)
            clearFieldsReservation();
        else{
            idRLabel.setText("" + reservation.getId());
            nameField.setText(reservation.getName());
            carComboBox.setValue(reservation.getCarId());
            Date date = reservation.getStartDate();
            datePicker.setValue(LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
            hourField.setText("" + reservation.getStartHour());
            noDaysField.setText("" + reservation.getNumberOfDays());
        }
    }

    @FXML
    private void clearFieldsReservation() {
        idRLabel.setText("" + service.getIdGenerator());
        nameField.setText("");
        carComboBox.setValue("");
        datePicker.setValue(null);
        hourField.setText("");
        noDaysField.setText("");
    }

    //----------------------------------------------------CRUD----------------------------------------------------//
    @FXML
    private void addReservation() throws Exception{
        String name = nameField.getText();
        String carS = carComboBox.getValue();
        String startDateS;
        try {
            startDateS = datePicker.getValue().format(dateConverter.getDtFormatter());
        }catch(Exception ex){
            showErrorMessage("All fields must be completed");
            return;
        }
        String startHourS = hourField.getText();
        String numberOfDaysS = noDaysField.getText();

        if (name.equals("") || carS.equals("") || startDateS.equals("") ||startHourS.equals("") || numberOfDaysS.equals("")) {
            showErrorMessage("All the fields must be completed");
            return;
        }
        try {
            Car car = new Car(carS);
            String[] dataSplit = startDateS.split("/");
            Date startDate = new Date(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
            String[] hourSplit = startHourS.split(":");
            Hour startHour = new Hour(Integer.parseInt(hourSplit[0]), Integer.parseInt(hourSplit[1]));
            int numberOfDays = Integer.parseInt(numberOfDaysS);
            int id = service.addReservation(name, car, startDate, startHour, numberOfDays).getId();
            reservationList.setAll(service.getAllReservations());
            carList.setAll(service.getAllCars());
            setRecycleBinList2();
            updateCarComboBox();
            backToFilterAll();
            idRLabel.setText("" + (id + 1));
            showNotification("Reservation successfully added! ", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException ex) {
            showErrorMessage("Invalid number format");
        }
        catch (Exception exception){
            showErrorMessage(exception.getMessage());
        }
    }

    public void deleteReservation(){
        String id = idRLabel.getText();

        if (id.equals("") || reservationTableView.getSelectionModel().getSelectedIndex() == -1){
            showErrorMessage("No reservation selected");
            return;
        }
        try {
            service.deleteReservation(Integer.parseInt(id));
            reservationList.setAll(service.getAllReservations());
            carList.setAll(service.getAllCars());
            setRecycleBinList2();
            backToFilterAll();
            updateCarComboBox();
            showNotification("Reservation successfully deleted! ", Alert.AlertType.INFORMATION);
        } catch (Exception exception) {
            showErrorMessage(exception.getMessage());
        }
    }

    public void deleteReservationRecycleBin(){
        int row = recycleBinTableView2.getSelectionModel().getSelectedIndex();
        if(row != -1){
            int id = reservationTableView.getItems().get(row).getId();
            try {
                service.deleteReservation(id);
                reservationList.remove(row);
                carList.setAll(service.getAllCars());
                setRecycleBinList2();
                backToFilterAll();
                updateCarComboBox();
                showNotification("Reservation successfully deleted!", Alert.AlertType.INFORMATION);
            } catch (Exception exception) {
                showErrorMessage(exception.getMessage());
            }
        }
    }

    public void updateReservation(){
        String idS = idRLabel.getText();
        String name = nameField.getText();
        String carS = carComboBox.getValue();
        String startDateS;
        try {
            startDateS = datePicker.getValue().format(dateConverter.getDtFormatter());
        }catch(Exception ex){
            showErrorMessage("All fields must be completed");
            return;
        }
        String startHourS = hourField.getText();
        String numberOfDaysS = noDaysField.getText();

        if (name.equals("") || carS.equals("") || startDateS.equals("") ||startHourS.equals("") || numberOfDaysS.equals("")) {
            showErrorMessage("All the fields must be completed");
            return;
        }
        try {
            int id = Integer.parseInt(idS);
            Car car = new Car(carS);
            String[] dataSplit = startDateS.split("/");
            Date startDate = new Date(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
            String[] hourSplit = startHourS.split(":");
            Hour startHour = new Hour(Integer.parseInt(hourSplit[0]), Integer.parseInt(hourSplit[1]));
            int numberOfDays = Integer.parseInt(numberOfDaysS);
            service.updateReservation(id, name, car, startDate, startHour, numberOfDays);
            reservationList.setAll(service.getAllReservations());
            carList.setAll(service.getAllCars());
            backToFilterAll();
            updateCarComboBox();
            showNotification("Reservation successfully updated! ", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException ex) {
            showErrorMessage("Invalid number format");
        }
        catch (Exception exception){
            showErrorMessage(exception.getMessage());
        }
    }

    //----------------------------------------------------Filter----------------------------------------------------//
    public void filterAllReservations(){
        nameCheckBox.setSelected(false);
        carCheckBox.setSelected(false);
        dateCheckBox.setSelected(false);
        reservationList.setAll(service.getAllReservations());
        setRecycleBinList2();
    }

    public void filterNameReservations(){
        allRCheckBox.setSelected(false);
        carCheckBox.setSelected(false);
        dateCheckBox.setSelected(false);
        reservationList.setAll(service.filterReservationsByName(nameSearchField.getText()));
        setRecycleBinList2();;
    }

    public void filterCarReservations(){
        allRCheckBox.setSelected(false);
        nameCheckBox.setSelected(false);
        dateCheckBox.setSelected(false);
        reservationList.setAll(service.filterReservationsByCar(carSearchField.getText()));
        setRecycleBinList2();
    }

    public void filterDateReservations(){
        allRCheckBox.setSelected(false);
        carCheckBox.setSelected(false);
        String dateS = datePickerSearch.getValue().format(dateConverter.getDtFormatter());
        String[] dataSplit = dateS.split("/");
        Date date = new Date(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
        reservationList.setAll(service.reservationsOnDate(date));
        setRecycleBinList2();
    }

    public void backToFilterAllReservations(){
        allRCheckBox.setSelected(true);
        nameCheckBox.setSelected(false);
        carCheckBox.setSelected(false);
        dateCheckBox.setSelected(false);
        reservationList.setAll(service.getAllReservations());
        setRecycleBinList2();
    }
    //----------------------------------------------------RecycleBin2----------------------------------------------------//
    public void setRecycleBinList2() {
        recycleBinList2.clear();
        for (Reservation var : reservationList) {
            CustomImageView imageView = new CustomImageView("recycleBin.png");
            recycleBinList2.add(imageView);
        }
    }

//----------------------------------------------------Open reports----------------------------------------------------//
    public void openReservationReports(){
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ReservationReports.fxml"));
            root = loader.load();
            ControllerReservationReportsFX controller = loader.getController();
            controller.setService(service);
            controller.setPrevScene(reportsReservationButton.getScene());
            Scene scene = new Scene(root);
            Stage window = (Stage)reportsReservationButton.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Car Rentals/Reservation Reports");
            window.show();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    //`````````````````````````````````````````````````ShowMessage`````````````````````````````````````````````````//
    void showErrorMessage (String text){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }

    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
