package Service;

import Model.Car;
import Model.Comparator.CompareReservationByDate;
import Model.Comparator.CompareReservationByHour;
import Model.Date;
import Model.Hour;
import Model.Reservation;
import Repository.CarRepository;
import Repository.ReservationRepository;
import Repository.ReservationRepositoryTextFile;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Service {
    protected CarRepository carRepository;
    protected ReservationRepository reservationRepository;

    public Service(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

//----------------------------------------------------Cars----------------------------------------------------//
//GET
    public Iterable<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Collection<Car> getAllCars() {
        return carRepository.getAll();
    }

    public List<Car> getCarList() {
        return new ArrayList<>(carRepository.getAll());
    }


//CRUD

    public void addCar(String id, String brand, String colour, String type, int price) throws Exception {
        Car car = new Car(id, brand, colour, type, price);
        carRepository.add(car);
    }

    public void deleteCar(String id) throws Exception {
        Car car = new Car(id);
        if (this.occupiedCars().contains(car))
            throw new Exception("You can't delete an occupied car");
        carRepository.delete(car);
    }

    public void updateCar(String id, String brand, String colour, String type, int price) {
        Car car = new Car(id, brand, colour, type, price);
        carRepository.update(car, id);
    }

    //OTHER FUNCTIONS
    public void occupyCars() {
        for (Reservation reservation : reservationRepository.findAll()) {
            Car car = carRepository.findById(reservation.getCar().getId());
            reservation.setCar(car);
            car.setFree(false);
            carRepository.update(car, car.getId());
        }
    }

    //Java8
    public List<Car> occupiedCars() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().filter(car -> !car.getFree()).collect(Collectors.toList());
    }

    public List<Car> freeCars() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().filter(Car::getFree).collect(Collectors.toList());
    }

    public List<String> allBrands() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().map(Car::getBrand).distinct().collect(Collectors.toList());
    }

    public List<Car> carsByBrand(String brand) {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().filter(car -> brand.equals(car.getBrand())).collect(Collectors.toList());
    }

    public List<Car> carsBetweenPrice(int priceMin, int priceMax) {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().filter(car -> car.getPrice() >= priceMin && car.getPrice() <= priceMax).collect(Collectors.toList());
    }

    public String mostExpensiveCar() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().sorted((o1, o2) -> o2.getPrice() - o1.getPrice()).collect(Collectors.toList()).get(0).getId();
    }

    public int mostExpensivePrice() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().map(Car::getPrice).max(Integer::compareTo).get();
    }

    public String cheapestCar() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().sorted(Comparator.comparingInt(Car::getPrice)).collect(Collectors.toList()).get(0).getId();
    }

    public int cheapestPrice() {
        Collection<Car> cars = this.carRepository.getAll();
        return cars.stream().map(Car::getPrice).min(Integer::compareTo).get();
    }


    //----------------------------------------------------Reservations----------------------------------------------------//
//GET
    public Iterable<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Collection<Reservation> getAllReservations() {
        return reservationRepository.getAll();
    }

    public List<Reservation> getReservationList() {
        return new ArrayList<>(reservationRepository.getAll());
    }

    //CRUD
    public Reservation addReservation(String name, Car car, Date startDate, Hour startHour, int numberOfDays) throws Exception {
        //verifies if the car is the car inventory
        if (carRepository.findById(car.getId()) == null)
            throw new Exception("The car isn't in the inventory!");

        //if everything is ok, save the car with all of its attributes(idk why)
        for (Car varCar : findAllCars())
            if (varCar.getId().equals(car.getId()))
                car = varCar;

        //verifies if the car is free
        if (!car.getFree())
            throw new Exception("The car is occupied");
        car.setFree(false);

        carRepository.update(car, car.getId());

        Reservation reservation = new Reservation(name, car, startDate, startHour, numberOfDays);
        return reservationRepository.add(reservation);
    }

    public void deleteReservation(Integer id) throws Exception {
        Reservation reservation = reservationRepository.findById(id);
        Car car = reservation.getCar();
        reservationRepository.delete(reservation);
        car.setFree(true);
        carRepository.update(car, car.getId());

    }

    public void updateReservation(Integer id, String name, Car car, Date startDate, Hour startHour, int numberOfDays) throws Exception {
        Reservation checkReservation = reservationRepository.findById(id);
        if (checkReservation != null && !checkReservation.getCar().equals(car)) {
            Car checkCar = carRepository.findById(car.getId());
            if (checkCar == null)
                throw new Exception("The car isn't in the inventory");

            if (!checkCar.getFree())
                throw new Exception("The car is occupied");

            checkReservation.getCar().setFree(true);
            checkCar.setFree(false);
            carRepository.update(checkCar, checkCar.getId());

            Reservation reservation = new Reservation(id, name, checkCar, startDate, startHour, numberOfDays);
            reservationRepository.update(reservation, id);
        } else {
            Car checkCar = carRepository.findById(car.getId());
            Reservation reservation = new Reservation(id, name, checkCar, startDate, startHour, numberOfDays);
            reservationRepository.update(reservation, id);
        }
    }

    //OTHER FUNCTIONS
    public List<Reservation> sortReservationsDate_Hour() {
        List<Reservation> result = new ArrayList<>();
        findAllReservations().forEach(result::add);
        result.sort(new CompareReservationByDate().thenComparing(new CompareReservationByHour()));
        return result;
    }

    public int getIdGenerator() {
        if (reservationRepository instanceof ReservationRepositoryTextFile) {
            return ((ReservationRepositoryTextFile) reservationRepository).getIdGenerator();
        }
        else return 0;
    }

    //Java8
    public List<Reservation> reservationsOnDate(Date date) {
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().filter(reservation -> date.equals(reservation.getStartDate())).collect(Collectors.toList());
    }

    public List<Reservation> filterReservationsByCar(String licencePlate) {
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().filter(reservation -> reservation.getCar().getId().equals(licencePlate)).collect(Collectors.toList());
    }

    public List<Reservation> filterReservationsByName(String name) {
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().filter(reservation -> reservation.getName().equals(name)).collect(Collectors.toList());
    }

    public long countReservationsOnDate(Date date) {
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().filter(reservation -> date.equals(reservation.getStartDate())).count();
    }

    public Reservation biggestPriceResName(){
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().sorted((o1, o2) -> o2.getPrice() - o1.getPrice()).collect(Collectors.toList()).get(0);
    }

    public Pair<String, Long> mostReservations(){
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        HashMap<String, Long> result = new HashMap<>();
        List<String> names = reservations.stream().map(Reservation::getName).distinct().collect(Collectors.toList());
        names.stream().forEach(name -> result.put(name, reservations.stream().filter(reservation -> reservation.getName().equals(name)).count()));
        Pair<String, Long> max = new Pair<>("", 0L);
        for(int i = 0; i < names.size(); i++)
            if(result.get(names.get(i)) >= max.getValue())
                max = new Pair<>(names.get(i), result.get(names.get(i)));
        return max;
    }

    public Pair<String, Integer> biggestPriceClient(){
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        List<String> names = reservations.stream().map(Reservation::getName).distinct().collect(Collectors.toList());
        Pair<String, Integer> max = new Pair<>("", 0);

        for(var name : names) {
            int sum = 0;
            for (var reservation : reservations)
                if (reservation.getName().equals(name))
                    sum += reservation.getPrice();
            if(sum > max.getValue())
                max = new Pair<>(name, sum);
        }
        return max;
    }

    public Reservation longestReservation(){
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        return reservations.stream().sorted((o1, o2) -> o2.getNumberOfDays() - o1.getNumberOfDays()).collect(Collectors.toList()).get(0);
    }

    public Pair<Date, Integer> busiestDay(){
        Collection<Reservation> reservations = this.reservationRepository.getAll();
        List<Date> dates = reservations.stream().map(Reservation::getStartDate).distinct().collect(Collectors.toList());
        Pair<Date, Integer> max = new Pair<>(new Date(0, 0, 0), 0);
        for(var date : dates){
            int count = 0;
            for(var reservation : reservations)
                if(reservation.getStartDate().compare(date) == 0)
                    count++;
            if(count > max.getValue())
                max = new Pair<>(date, count);
        }
        return max;
    }
}


