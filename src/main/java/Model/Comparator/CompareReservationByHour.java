package Model.Comparator;

import Model.Reservation;

import java.util.Comparator;

public class CompareReservationByHour implements Comparator<Reservation> {
    public int compare(Reservation reservation1, Reservation reservation2) {
        return reservation1.getStartHour().compare(reservation2.getStartHour());
    }
}
