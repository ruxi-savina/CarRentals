package Model;

import java.io.Serializable;

//----------------------------------------------------Class Car----------------------------------------------------//
public class Car implements Identifiable<String>, Serializable {
    private String id;
    private String brand;
    private String colour;
    private String type;
    private int price;
    private boolean free;

//----------------------------------------------------Constructor----------------------------------------------------//
    public Car(String id, String brand, String colour, String type, int price) {
        this.id = id;
        this.brand = brand;
        this.colour = colour;
        this.type = type;
        this.price = price;
        this.free = true;
    }

    public Car(String id) {
        this.id = id;
        this.brand = "";
        this.colour = "";
        this.type = "";
        this.price = 0;
        this.free = true;
    }

//----------------------------------------------------Getters----------------------------------------------------//
    @Override
    public String getId(){
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }

    public int getPrice(){
        return price;
    }

    public boolean getFree(){
        return free;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    public void setId(String id){
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setFree(boolean free){
        this.free = free;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    public String toString() {
        StringBuilder carString = new StringBuilder();
        carString.append("Licence Plate: ").append(id).append("\nBrand: ").append(brand).append("\nColour: ").append(colour).append("\nType: ").append(type).append("\nPrice for a day: ").append(price).append("\nFree: ");
        if(free == true)
            carString.append("Yes");
        else carString.append("No");
        return carString.append("\n").toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof  Car){
            Car other_car = (Car) object;
            return this.id.equals(other_car.id);
        }
        return false;
    }
}
