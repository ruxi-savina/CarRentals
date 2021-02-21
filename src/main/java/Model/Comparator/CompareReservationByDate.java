package Model.Comparator;

import Model.Reservation;

import java.util.Comparator;

//----------------------------------------------------Compare----------------------------------------------------//
public class CompareReservationByDate implements Comparator<Reservation> {
    public int compare(Reservation reservation1, Reservation reservation2) {
        return reservation1.getStartDate().compare(reservation2.getStartDate());
    }
}
