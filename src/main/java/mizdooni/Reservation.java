package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

//reserveReservation {“username”: “user1”, “restaurantName”: “restaurant1”, “reservationNumber”: 1,
//        “datetime”: “2024-02-14 21:00”}
public class Reservation {
    public String username;

    public String restaurantName;

    public String reservationNumber;
    
    public String datetime;

    public Reservation(String jsonString) throws Exception {
        ObjectMapper om = new ObjectMapper();
        Reservation reservation  = om.readValue(jsonString, Reservation.class);
        username = reservation.username;
        restaurantName = reservation.restaurantName;
        reservationNumber = reservation.reservationNumber;
        checkDateTimeFormat(reservation.datetime);
    }
    public void checkDateTimeFormat(String datetime) throws Exception {
        if(datetime.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:00")){
            this.datetime = datetime;
        }else throw new Exception("DateTime format in not correct");

    }
}
