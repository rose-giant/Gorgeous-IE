package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Table {
    public int tableNumber;
    public String restaurantName;
    public String managerUsername;
    public ArrayList<LocalDateTime> reservedDateTimes = new ArrayList<>();
    public int seatsNumber;

    public void removeReservation(Reservation reservation) {
        reservedDateTimes.remove(reservation.datetimeFormatted);
    }

    public boolean hasReservationAt(LocalDateTime newDT) {
        for (LocalDateTime reserved:reservedDateTimes) {
            if(reserved == newDT){
                return true;
            }
        }
        return false;
    }

    public static class TableInfo{
        public int tableNumber;
        public ArrayList<LocalDateTime> availableDateTimes = new ArrayList<>();
        public int seatsNumber;
        @JsonCreator
        public TableInfo(@JsonProperty("tableNumber") int tableNumber, @JsonProperty("seatsNumber") int seatsNumber
                        ,@JsonProperty("availableDateTimes") ArrayList<LocalDateTime> availableDateTimes) {
            this.availableDateTimes = availableDateTimes;
            this.tableNumber = tableNumber;
            this.seatsNumber  = seatsNumber;
        }
    }
}
