package Repository;

import Model.*;

import java.io.*;
import java.util.Map;

//----------------------------------------------------Class CarRepository----------------------------------------------------//
public class CarRepositoryBinaryFile extends CarRepository {
    private String file;

    public CarRepositoryBinaryFile(String file){
        this.file = file;
        retrieveCar();
    }

    @SuppressWarnings("unchecked")
    private void retrieveCar() {
        Car car;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            elem = (Map<String, Car>)in.readObject();
        } catch (IOException|ClassNotFoundException exception) {
            System.err.println("File ended\n");
        }
    }

    private void storeCar() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(elem);
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
    @Override
    public Car add(Car car) throws Exception{
        super.add(car);
        storeCar();
        return car;
    }
    @Override
    public void delete(Car car) throws Exception{
        super.delete(car);
        storeCar();
    }

    @Override
    public void update(Car obj, String id) {
        super.update(obj, id);
        storeCar();
    }
}