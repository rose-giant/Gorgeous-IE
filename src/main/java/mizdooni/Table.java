package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Table {
    public String tableNumber;

    public String restaurantName;

    public String managerUsername;

    public ArrayList<String> reservedDateTimes;

    public int seatsNumber;

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

}
