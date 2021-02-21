package Model;
import java.io.Serializable;

//----------------------------------------------------Class Reservation----------------------------------------------------//
public class Reservation implements Identifiable<Integer>, Serializable {
    private Integer id;
    private String name;
    private Car car;
    private Date startDate;
    private Hour startHour;
    private int numberOfDays;
    private int price;

//----------------------------------------------------Constructor----------------------------------------------------//
    public Reservation(Integer id, String name, Car car, Date startDate, Hour startHour, int numberOfDays) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.startDate = startDate;
        this.startHour = startHour;
        this.numberOfDays = numberOfDays;
        this.price = this.car.getPrice() * this.numberOfDays;
    }

    public Reservation(Integer id, String name, Car car, Date startDate, Hour startHour, int numberOfDays, int price) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.startDate = startDate;
        this.startHour = startHour;
        this.numberOfDays = numberOfDays;
        this.price = price;
    }

    public Reservation(Integer id) {
        this.id = id;
        this.name = "";
        this.car = new Car("");
        this.startDate = new Date(0, 0, 0);
        this.startHour = new Hour(0, 0);
        this.numberOfDays = 0;
        this.price = 0;
    }

    public Reservation(String name, Car car, Date startDate, Hour startHour, int numberOfDays) {
        this.name = name;
        this.car = car;
        this.startDate = startDate;
        this.startHour = startHour;
        this.numberOfDays = numberOfDays;
        this.price = this.car.getPrice() * this.numberOfDays;
    }

//----------------------------------------------------Getters----------------------------------------------------//
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public String getCarId(){
        return car.getId();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Hour getStartHour() {
        return startHour;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public int getPrice() {
        return price;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartHour(Hour startHour) {
        this.startHour = startHour;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setPrice(int price) {
        this.price = price;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    public String toString() {
        StringBuffer reservationString = new StringBuffer();
        reservationString.append("Id: ").append(id).append("\nName: ").append(name).append("\nCar: ").append(car.getId()).append("\nStart date: ").append(startDate).append("\nStart hour: ").append(startHour).append("\nNumber of Days: ").append(numberOfDays).append("\nPrice: ").append(price).append("\n");
        return reservationString.toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof  Car){
            Reservation other_reservation = (Reservation) object;
            return this.id == other_reservation.id;
        }
        return false;
    }
}


