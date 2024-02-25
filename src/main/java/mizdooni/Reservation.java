package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//reserveReservation {“username”: “user1”, “restaurantName”: “restaurant1”, “reservationNumber”: 1,
//        “datetime”: “2024-02-14 21:00”}
public class Reservation {
    public int reservationNumber;
    public String username;

    public String restaurantName;

    public String tableNumber;
    
    public String datetime;

    private static int counter;

    public Reservation(String jsonString) throws Exception {
        counter += 1;
        ObjectMapper om = new ObjectMapper();
        Reservation reservation  = om.readValue(jsonString, Reservation.class);
        username = reservation.username;
        restaurantName = reservation.restaurantName;
        tableNumber = reservation.tableNumber;
        checkOutdatedDateTimes(reservation.datetime);
        checkDateTimeFormat(reservation.datetime);
        reservationNumber = counter;
    }
//    public Reservation(){}
////    public static void main(String[] args){
////        Reservation res = new Reservation();
////        res.datetime = "2024-02-14 1:00";
////        try{
////        res.checkOutdatedDateTimes("2024-02-26 01:00");
////        }catch (Exception error){
////            System.out.println(error.getMessage());
////        }
//    }
    private void checkOutdatedDateTimes(String datetime) throws Exception {
        String[] dt= datetime.split(" ");
        if(LocalDate.now().isAfter(LocalDate.parse(dt[0])))
            throw new Exception("Date expired!");
        if(LocalDate.now().isEqual(LocalDate.parse(dt[0])) && LocalTime.now().isAfter(LocalTime.parse(dt[1])))
            throw new Exception("Time expired!");
    }

    public void checkDateTimeFormat(String datetime) throws Exception {
        if(datetime.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:00")){
            this.datetime = datetime;
        }else throw new Exception("DateTime format in not correct");
    }
}
