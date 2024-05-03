package Mizdooni.Model.Table;

import Mizdooni.Model.Reservation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class Table {

    public int tableNumber;
    public String restaurantName;
    public String managerUsername;
    public int seatsNumber;
    @JsonIgnore
    public ArrayList<LocalDateTime> reservedDateTimes = new ArrayList<>();

    public Table(@JsonProperty("managerUsername") String managerUsername,
                 @JsonProperty("tableNumber") int tableNumber,
                 @JsonProperty("restaurantName") String restaurantName,
                 @JsonProperty("seatsNumber") int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.managerUsername = managerUsername;
        this.seatsNumber = seatsNumber;
    }

    public void removeReservation(Reservation reservation) {
        reservedDateTimes.remove(reservation.datetimeFormatted);
    }

    public boolean hasReservationAt(LocalDateTime newDT) {
        for (LocalDateTime reserved : reservedDateTimes) {
            if (reserved == newDT) {
                return true;
            }
        }
        return false;
    }
}