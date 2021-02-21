package Repository;

import Model.*;

import java.io.*;

//----------------------------------------------------Class ReservationRepository----------------------------------------------------//
public class ReservationRepositoryBinaryFile extends ReservationRepository{
    private String file;

    public ReservationRepositoryBinaryFile(String file){
        this.file = file;
        retrieveReservation();
    }

    private void retrieveReservation() {
        Reservation reservation;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            while((reservation = (Reservation) in.readObject()) != null){
                add(reservation);
            }
        } catch (IOException|ClassNotFoundException exception) {
            System.err.println("File ended\n");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void storeReservation() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            for(Reservation reservation : findAll())
                out.writeObject(reservation);
        } catch (IOException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public Reservation add(Reservation reservation) throws Exception{
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
}