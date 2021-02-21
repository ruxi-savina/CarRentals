package Repository;

import Model.*;

import java.io.*;
import java.util.ArrayList;

//----------------------------------------------------Class ReservationRepository----------------------------------------------------//
public class ReservationRepositoryTextFile extends ReservationRepository{
    private final String file;
    private static int idGenerator = 0;

    public ReservationRepositoryTextFile(String file){
        this.file = file;
        retrieveReservation();
    }

    private void retrieveReservation(){
        try(BufferedReader buffer = new BufferedReader(new FileReader(file))){
            ArrayList<String> attribute = new ArrayList<>();
            String readLine = buffer.readLine();
            try{
                idGenerator=Integer.parseInt(readLine);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator");
            }
            while((readLine = buffer.readLine()) != null) {
                String[] line = readLine.split(": ");

                if (line.length > 1)
                    attribute.add(line[1]);

                if (line[0].equals("Price")) {
                    try {
                        Car car = new Car(attribute.get(2));

                        String[] dataSplit = attribute.get(3).split("/");
                        Date date = new Date(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
                        String[] hourSplit = attribute.get(4).split(":");
                        Hour hour = new Hour(Integer.parseInt(hourSplit[0]), Integer.parseInt(hourSplit[1]));
                        Reservation reservation = new Reservation(Integer.parseInt(attribute.get(0)), attribute.get(1), car, date, hour, Integer.parseInt(attribute.get(5)), Integer.parseInt(attribute.get(6)));
                        super.add(reservation);
                        attribute.clear();
                    } catch (NumberFormatException exception) {
                        System.err.println("Invalid format");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            }catch (IOException exception){
                throw new RepositoryException("File reading format" + exception);
            }
    }

    private void storeReservation() {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(file))){
            buffer.write("" + idGenerator);
            buffer.newLine();
            for(Reservation reservation: findAll()){
                buffer.write(reservation.toString());
                buffer.newLine();
            }
        } catch (IOException exception) {
            throw new RepositoryException("Writing error "+ exception );
        }
    }

    @Override
    public Reservation add(Reservation reservation) throws Exception{
        reservation.setId(getNextId());
        super.add(reservation);
        storeReservation();
        return reservation;
    }

    @Override
    public void delete(Reservation reservation) throws Exception{
        super.delete(reservation);
        storeReservation();
    }

    @Override
    public void update(Reservation reservation, Integer id){
        super.update(reservation, id);
        storeReservation();
    }

    public int getNextId(){
        return idGenerator++;
    }

    public int getIdGenerator(){
        return idGenerator;
    }
}