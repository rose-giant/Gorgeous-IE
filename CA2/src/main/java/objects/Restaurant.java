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
    private final ArrayList<Table.TableInfo> tables = new ArrayList<>();

    public void addTable(Table table){
        ArrayList<LocalDateTime> availableTimes = new ArrayList<>();
        LocalDateTime current = LocalDateTime.parse(LocalDate.now().toString() +'t'+startTime);
        LocalTime startDate = LocalTime.parse(startTime);
        LocalTime endDate = LocalTime.parse(endTime);
        int diff = endDate.getHour() - startDate.getHour();
        for (int i = 0; i < diff; i++) {
            availableTimes.add(current.plusHours(i));
        }
        Table.TableInfo tableInfo = new Table.TableInfo(table.tableNumber, table.seatsNumber,availableTimes);
        tables.add(tableInfo);
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
        return new AvailableTimes(tables);
    }

    public void reserve(Reservation reservation) {
        int tableNum = reservation.tableNumber;
        for (Table.TableInfo ti: tables) {
            if(Objects.equals(ti.tableNumber, reservation.tableNumber)){
                ti.availableDateTimes.remove(reservation.datetime);
            }
        }
    }

    static class AvailableTimes{
        public ArrayList<Table.TableInfo>availableTables;
        @JsonCreator
        public AvailableTimes(@JsonProperty("availableTables") ArrayList<Table.TableInfo> availableTables) {
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