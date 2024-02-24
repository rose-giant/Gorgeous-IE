package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Restourant {
    public String name;
    public String managerUsername;
    public String type;
    public String startTime;
    public String endTime;
    public Date parsedStartTime;
    public Date parsedEndTIme;
    public String description;
    public Address address = new Address();
    public ResponseHandler responseHandler;

    public Restourant unmarshlIntoRestaurant(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Restourant restourant  = om.readValue(jsonString, Restourant.class);
        return restourant;
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


}
