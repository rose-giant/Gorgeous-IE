package java.Objects;
import java.time.LocalDateTime;

public class Reservation {
    public int reservationNumber;
    public String username;
    public String restaurantName;
    public int tableNumber;
    public String datetime;
    public LocalDateTime datetimeFormatted;
    private static int counter;

    @JsonCreator
    public Reservation(@JsonProperty("username") String username, @JsonProperty("restaurantName") String restaurantName,
                       @JsonProperty("tableNumber") int tableNumber, @JsonProperty("datetime") String datetime) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.datetime  = datetime;
    }

    public Reservation(String jsonString) throws Exception {
        counter += 1;
        ObjectMapper om = new ObjectMapper();
        Reservation reservation  = om.readValue(jsonString, Reservation.class);
        username = reservation.username;
        restaurantName = reservation.restaurantName;
        tableNumber = reservation.tableNumber;

        checkDateTimeFormat(reservation.datetime);
        datetime = reservation.datetime;
        datetimeFormatted = LocalDateTime.parse(reservation.datetime.replace(' ', 't'));

        checkOutdatedDateTimes(datetimeFormatted);
        reservationNumber = counter;
    }
//    public static void main(String[] args) throws JsonProcessingException {
//        String resStr = "{\"username\": \"user1\", \"restaurantName\": \"restaurant1\", \"tableNumber\": 1,\"datetime\": \"2024-02-14 21:00\"}";
//
//        ObjectMapper om = new ObjectMapper();
//        try{
//            Reservation reservation  = om.readValue(resStr, Reservation.class);
//            System.out.println("good");
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//    }

    public void checkSafetyRemoval() throws Exception {
        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(datetimeFormatted)){
            throw new Exception("Reservation has expired.");
        }
    }


    static class CancelReservation{
        public String username;
        public int reservationNumber;
        @JsonCreator
        public CancelReservation(@JsonProperty("username") String username, @JsonProperty("reservationNumber") int reservationNumber) {
            this.username = username;
            this.reservationNumber = reservationNumber;
        }
    }
    static class ResNumber{
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
