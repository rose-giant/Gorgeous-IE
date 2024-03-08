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
}
