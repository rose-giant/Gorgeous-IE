package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({ "name", "managerUsername", "type", "startTime", "endTime", "description", "country", "city", "street"})
public class Restaurant {
    public String name;
    public String id;
    public String managerUsername;
    public String type;
    public String startTime;
    public String endTime;
    public Date parsedStartTime;
    public Date parsedEndTIme;
    public String description;
    public String country;
    public String city;
    public String street;

    public Address address = new Address();
    public ResponseHandler responseHandler;
    private final ArrayList<Table> tables = new ArrayList<>();
    public ArrayList<Review> reviews = new ArrayList<>();
    public MeanScores means = new MeanScores(0.0, 0.0,0.0, 0.0);
    @JsonCreator
    public Restaurant(@JsonProperty("name")String name,@JsonProperty("type") String type,
                      @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                      @JsonProperty("description") String description,@JsonProperty("address") Address address) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
    }
    public Restaurant(){};
    public void addTable(Table table){
        tables.add(table);
    }

    public void addReview(Review review){
        Review prevReview = findReviewByUsername(review.username);
        if(prevReview != null){
            reviews.remove(prevReview);
        }
        reviews.add(review);
        means.updateMeans(review);
    }

    public Review findReviewByUsername(String username){
        for (Review rv:reviews) {
            if(Objects.equals(rv.username, username)){
                return rv;
            }
        }
        return null;
    }

    public boolean isRestaurantNameValid(String name) {
        if (name.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isAddressValid(Address address) {
        boolean isCityValid = address.isCityValid(address.city);
        boolean isCountryValid = address.isCountryValid(address.country);
        boolean isStreetValid = address.isStreetValid(address.street);
        return isCountryValid && isCityValid && isStreetValid;
    }

    public Date parseDate(String dateString) {
        Date date = new Date();
        if (dateString == null) {
            return date;
        }
        String[] timeString = dateString.split(":");
        date.setHours(Integer.parseInt(timeString[0]));
        date.setMinutes(Integer.parseInt(timeString[1]));
        return date;
    }

    public boolean isTimeValid(Date date) {
        int minutes = date.getMinutes();
        return minutes == 0;
    }

    public boolean isManagerUsernameValid(String managerUsername) {
        User user = new User();
        return user.isUsernameValid(managerUsername);
    }

    public boolean isDescriptionValid(String description) {
        return !description.isEmpty();
    }

    public boolean isTypeValid(String type) {
        return !type.isEmpty();
    }

    public void handleOuterErrorMessage(String errorMessage) {
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = errorMessage;
        } else {
            this.responseHandler.responseBody += errorMessage;
        }

        this.responseHandler.responseStatus = false;
    }

    public void addRestaurantResponseGenerator() {
        this.responseHandler = new ResponseHandler();
        this.responseHandler.responseBody = "";
        boolean isRestaurantNameValid = isRestaurantNameValid(this.name);
        if (!isRestaurantNameValid) {
            this.responseHandler.responseBody += " name is not valid.";
        }

        boolean isManagerUsernameValid = isManagerUsernameValid(this.managerUsername);
        if (!isManagerUsernameValid) {
            this.responseHandler.responseBody += " manager username is not valid.";
        }

        boolean isDescriptionValid = isDescriptionValid(this.description);
        if (!isDescriptionValid) {
            this.responseHandler.responseBody += " description is not valid.";
        }

        boolean isTypeValid = isTypeValid(this.type);
        if (!isTypeValid) {
            this.responseHandler.responseBody += " type is not valid.";
        }

        boolean isAddressValid = isAddressValid(this.address);
        if (!isAddressValid) {
            this.responseHandler.responseBody += " address is not valid.";
        }

        this.parsedStartTime = parseDate(this.startTime);
        boolean isStartTimeValid = isTimeValid(this.parsedStartTime);
        if (!isStartTimeValid) {
            this.responseHandler.responseBody += " start time is not rond.";
        }

        this.parsedEndTIme = parseDate(this.endTime);
        boolean isEndTimeValid = isTimeValid(this.parsedEndTIme);
        if (!isEndTimeValid) {
            this.responseHandler.responseBody += " end time is not rond.";
        }

        this.responseHandler.responseStatus = isTypeValid && isDescriptionValid && isManagerUsernameValid &&
                isRestaurantNameValid && isAddressValid && isStartTimeValid && isEndTimeValid;
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = "Restaurant added successfully.";
        }
    }
    public void addRestaurantHandler(String jsonString) throws JsonProcessingException {
        Restaurant restaurant = unmarshlIntoRestaurant(jsonString);
        this.managerUsername = restaurant.managerUsername;
        this.name = restaurant.name;
        this.type = restaurant.type;
        this.startTime = restaurant.startTime;
        this.parsedStartTime = restaurant.parsedStartTime;
        this.endTime = restaurant.endTime;
        this.parsedEndTIme = restaurant.parsedEndTIme;
        this.address = restaurant.address;
        this.description = restaurant.description;
        addRestaurantResponseGenerator();
    }

    public String marshalRestaurant(Restaurant restaurant) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(restaurant);
    }

    public Restaurant unmarshlIntoRestaurant(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonString, Restaurant.class);
    }

    public boolean isOpenAt(LocalDateTime datetime) {
        LocalTime time = datetime.toLocalTime();
        LocalTime parsedStartTime = LocalTime.parse(startTime);
        LocalTime parsedEndTime = LocalTime.parse(endTime);
        if(parsedStartTime.isBefore(time) && parsedEndTime.isAfter(time)){
            return true;
        }else return false;
    }

    public Object getAvailableTables() {
        ArrayList<Table.TableInfo> ATT = new ArrayList<>();
        LocalDateTime current = LocalDateTime.parse(LocalDate.now().toString() +'t'+startTime);
        LocalTime startTime = LocalTime.parse(this.startTime);
        LocalTime endTime = LocalTime.parse(this.endTime);
        int diff = endTime.getHour() - startTime.getHour();
        ArrayList<LocalDateTime> availableTimes;
        for (Table table:tables) {
            availableTimes = new ArrayList<>();
            for (int i = 0; i < diff; i++) {
                LocalDateTime newDT = current.plusHours(i);
                if (!table.hasReservationAt(newDT)) {
                    availableTimes.add(newDT);
                }
            }
            Table.TableInfo newTime = new Table.TableInfo(table.tableNumber, table.seatsNumber, availableTimes);
            ATT.add(newTime);
        }
        return new AvailableTableTimes(ATT);
    }

    public void reserve(Reservation reservation) throws Exception {
        int tableNum = reservation.tableNumber;
        for (Table table: tables) {
            if(table.tableNumber == reservation.tableNumber){
                table.reservedDateTimes.add(reservation.datetimeFormatted);
                return;
            }
        }
        throw new Exception("Table number not found.");
    }

    public Table findTableByNumber(int tableNumber) {
        for(Table tb: tables) {
            if(Objects.equals(tb.tableNumber, tableNumber)) {
                return tb;
            }
        }
        return null;
    }

    public void removeReservation(Reservation reservation) {
        Table table = findTableByNumber(reservation.tableNumber);
        table.removeReservation(reservation);
    }

    public static class AvailableTableTimes{
        public ArrayList<Table.TableInfo>availableTables;

        @JsonCreator
        public AvailableTableTimes(@JsonProperty("availableTables") ArrayList<Table.TableInfo> availableTables){
            this.availableTables = availableTables;
        }

    }
    public static class RestaurantName {
        public String restaurantName;
        @JsonCreator
        public RestaurantName(@JsonProperty("restaurantName") String restaurantName) {
            this.restaurantName = restaurantName;
    }
}
}