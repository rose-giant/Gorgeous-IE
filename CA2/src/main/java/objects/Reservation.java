package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Reservation {
    public int reservationNumber;
    public String username;
    public String restaurantName;
    public int tableNumber;
    public String datetime;
    public LocalDateTime datetimeFormatted;
    private static int counter;

    public void checkSafetyRemoval() throws Exception {
        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(datetimeFormatted)){
            throw new Exception("Reservation has expired.");
        }
    }


    public static class CancelReservation{
        public String username;
        public int reservationNumber;
        @JsonCreator
        public CancelReservation(@JsonProperty("username") String username, @JsonProperty("reservationNumber") int reservationNumber) {
            this.username = username;
            this.reservationNumber = reservationNumber;
        }
    }
    public static class ResNumber{
        public int reservationNumber;
        public ResNumber(int rn){reservationNumber = rn;};
    }

    static class ReservationInfo{
        public int reservationNumber;
        public String restaurantName;
        public int tableNumber;
        public String datetime;
        @JsonCreator
        public ReservationInfo(@JsonProperty("restaurantName") String restaurantName, @JsonProperty("reservationNumber") int reservationNumber,
                               @JsonProperty("tableNumber") int tableNumber, @JsonProperty("datetime") String datetime) {
            this.tableNumber = tableNumber;
            this.reservationNumber = reservationNumber;
            this.datetime = datetime;
            this.restaurantName = restaurantName;
        }
    }
    private void checkOutdatedDateTimes(LocalDateTime datetime) throws Exception {
        if(LocalDateTime.now().isAfter(datetime)) {
            throw new Exception("Date expired!");
        }
    }

    public void checkDateTimeFormat(String datetime) throws Exception {
        if(!datetime.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:00")){
            throw new Exception("DateTime format in not correct");
        }
    }
}
