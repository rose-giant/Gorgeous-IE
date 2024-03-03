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

    public ArrayList<String> reservedDateTimes = new ArrayList<>();

    public int seatsNumber;

    @JsonCreator
    public Table(@JsonProperty("tableNumber") int tableNumber, @JsonProperty("restaurantName") String restaurantName,
                       @JsonProperty("managerUsername") String managerUsername, @JsonProperty("seatsNumber") int seatsNumber) {
        this.managerUsername = managerUsername;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.seatsNumber  = seatsNumber;
    }

    public Table(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Table table  = om.readValue(jsonString, Table.class);
        tableNumber = table.tableNumber;
        restaurantName = table.restaurantName;
        managerUsername = table.managerUsername;
    }

    public boolean hasDateTimeConflict(Reservation reservation) {
        for(String rs: reservedDateTimes) {
            if(Objects.equals(rs, reservation.datetime)) {
                return true;
            }
        }
        return false;
    }

    public void addReservation(Reservation reservation) throws Exception {
        if (hasDateTimeConflict(reservation)) {
            throw new Exception("This table is already reserved");
        }
        reservedDateTimes.add(reservation.datetime);
    }

    public void removeReservation(Reservation reservation) {
        reservedDateTimes.remove(reservation.datetime);
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
