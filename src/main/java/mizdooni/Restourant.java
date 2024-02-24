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

    public void handleNoneExistingUsername() {
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = " manager username does not exist.";
        } else {
            this.responseHandler.responseBody += " manager username does not exist.";
        }

        this.responseHandler.responseStatus = false;
    }

    public void handleIncorrectManagerRole() {
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = " manager role is not correct.";
        } else {
            this.responseHandler.responseBody += " manager role is not correct.";
        }

        this.responseHandler.responseStatus = false;
    }

    public void handleRepeatedRestaurantName() {
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = " restaurant name is repeated.";
        } else {
            this.responseHandler.responseBody += " restaurant name is repeated.";
        }

        this.responseHandler.responseStatus = false;
    }

    public ResponseHandler addRestaurantResponseGenerator() {
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
        return this.responseHandler;
    }
    public void addRestaurantHandler(String jsonString) throws JsonProcessingException {
        Restourant restourant = unmarshlIntoRestaurant(jsonString);
        this.managerUsername = restourant.managerUsername;
        this.name = restourant.name;
        this.type = restourant.type;
        this.startTime = restourant.startTime;
        this.parsedStartTime = restourant.parsedStartTime;
        this.endTime = restourant.endTime;
        this.parsedEndTIme = restourant.parsedEndTIme;
        this.address = restourant.address;
        this.description = restourant.description;
        addRestaurantResponseGenerator();
    }
}