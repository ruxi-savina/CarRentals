package Repository;

import Model.*;

import java.io.*;
import java.util.ArrayList;

//----------------------------------------------------Class CarRepository----------------------------------------------------//
public class CarRepositoryTextFile extends CarRepository {
    private String file;

    public CarRepositoryTextFile(String file) {
        this.file = file;
        retrieveCar();
    }

    private void retrieveCar() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            ArrayList<String> attribute = new ArrayList<>();
            String readLine;
            while ((readLine = buffer.readLine()) != null) {
                String[] line = readLine.split(": ");
                if (line.length > 1)
                    attribute.add(line[1]);

                if (line[0].equals("Free")) {
                    Car car = new Car(attribute.get(0), attribute.get(1), attribute.get(2), attribute.get(3), Integer.parseInt(attribute.get(4)));
                    add(car);
                    attribute.clear();
                }
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void storeCar() {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(file))) {
            for (Car car : findAll()) {
                buffer.write(car.toString());
                buffer.newLine();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public Car add(Car car) throws Exception {
        super.add(car);
        storeCar();
        return car;
    }

    @Override
    public void delete(Car car) throws Exception {
        super.delete(car);
        storeCar();
    }

    @Override
    public void update(Car obj, String id) {
        super.update(obj, id);
        storeCar();
    }
}