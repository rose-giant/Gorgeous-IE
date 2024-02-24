import mizdooni.Address;
import mizdooni.ResponseHandler;
import mizdooni.Restourant;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    Restourant restaurant;

    @Before
    @BeforeEach
    public void setup (){
        restaurant = new Restourant();
        restaurant.address = new Address();
        restaurant.responseHandler = new ResponseHandler();
    }

    @Test
    public void parseDate() {
        String dateString = "01:50";
        Date date = new Date();
        date.setHours(1);
        date.setMinutes(50);
        Date parseDate = restaurant.parseDate(dateString);
        assertEquals(parseDate.getHours(), date.getHours());
        assertEquals(parseDate.getMinutes(), date.getMinutes());
    }

    @Test
    public void isTimeValidReturnsTrueForValidTime() {
        Date date = new Date();
        date.setHours(12);
        date.setMinutes(0);
        assertTrue(restaurant.isTimeValid(date));
    }

    @Test
    public void isTimeValidReturnsFalseForValidTime() {
        Date date = new Date();
        date.setHours(12);
        date.setMinutes(10);
        assertFalse(restaurant.isTimeValid(date));
    }

    @Test
    public void handleNoneExistingUsernameFillsResponseHandler() {
        restaurant.responseHandler.responseStatus = true;
        restaurant.responseHandler.responseBody = "";

        restaurant.handleNoneExistingUsername();
        assertEquals(restaurant.responseHandler.responseBody, " manager username does not exist.");
        assertFalse(restaurant.responseHandler.responseStatus);
    }

    @Test
    public void handleNoneExistingUsernameUpdatesResponseHandler() {
        restaurant.responseHandler.responseStatus = false;
        restaurant.responseHandler.responseBody = "hello!";

        restaurant.handleNoneExistingUsername();
        assertEquals(restaurant.responseHandler.responseBody, "hello! manager username does not exist.");
        assertFalse(restaurant.responseHandler.responseStatus);
    }

    @Test
    public void handleIncorrectManagerRoleFillsResponseHandler() {
        restaurant.responseHandler.responseStatus = true;
        restaurant.responseHandler.responseBody = "";

        restaurant.handleNoneExistingUsername();
        assertEquals(restaurant.responseHandler.responseBody, " manager role is not correct.");
        assertFalse(restaurant.responseHandler.responseStatus);
    }

    @Test
    public void handleIncorrectManagerRoleUpdatesResponseHandler() {
        restaurant.responseHandler.responseStatus = false;
        restaurant.responseHandler.responseBody = "hello!";

        restaurant.handleIncorrectManagerRole();
        assertEquals(restaurant.responseHandler.responseBody, "hello! manager role is not correct.");
        assertFalse(restaurant.responseHandler.responseStatus);
    }

    @Test
    public void addRestaurantResponseGeneratorGeneratesSuccessfulResponse() {
        restaurant.address.city = "milan";
        restaurant.address.country = "italy";
        restaurant.address.street = "alferedo";
        restaurant.managerUsername = "Eunhoo";
        restaurant.type = "italian";
        restaurant.description = "yes";
        restaurant.startTime = "11:0";
        restaurant.endTime = "12:0";
        restaurant.name = "vanila";

        restaurant.addRestaurantResponseGenerator();
        assertTrue(restaurant.responseHandler.responseStatus);
        assertEquals(restaurant.responseHandler.responseBody, "Restaurant added successfully.");
    }

    @Test
    public void addRestaurantResponseGeneratorGeneratesResponseForInvalidTime() {
        restaurant.responseHandler.responseBody = "";
        restaurant.address.city = "milan";
        restaurant.address.country = "italy";
        restaurant.address.street = "alferedo";
        restaurant.managerUsername = "me";
        restaurant.type = "italian";
        restaurant.description = "yes";
        restaurant.startTime = "11:05";
        restaurant.endTime = "12:30";
        restaurant.name = "vanila";

        restaurant.addRestaurantResponseGenerator();
        assertFalse(restaurant.responseHandler.responseStatus);
        assertEquals( " start time is not rond. end time is not rond.", restaurant.responseHandler.responseBody);
    }

    @Test
    public void addRestaurantResponseGeneratorGeneratesResponseForInvalidManagerUsername() {
        restaurant.responseHandler.responseBody = "";
        restaurant.address.city = "milan";
        restaurant.address.country = "italy";
        restaurant.address.street = "alferedo";
        restaurant.managerUsername = "me*";
        restaurant.type = "italian";
        restaurant.description = "yes";
        restaurant.startTime = "11:0";
        restaurant.endTime = "12:0";
        restaurant.name = "vanila";

        restaurant.addRestaurantResponseGenerator();
        assertFalse(restaurant.responseHandler.responseStatus);
        assertEquals( " manager username is not valid.", restaurant.responseHandler.responseBody);
    }

    @Test
    public void addRestaurantResponseGeneratorGeneratesResponseForInvalidAddress() {
        restaurant.responseHandler.responseBody = "";
        restaurant.address.city = "milan";
        restaurant.address.country = "";
        restaurant.address.street = "alferedo";
        restaurant.managerUsername = "me";
        restaurant.type = "italian";
        restaurant.description = "yes";
        restaurant.startTime = "11:0";
        restaurant.endTime = "12:0";
        restaurant.name = "vanila";

        restaurant.addRestaurantResponseGenerator();
        assertFalse(restaurant.responseHandler.responseStatus);
        assertEquals( " address is not valid.", restaurant.responseHandler.responseBody);
    }

    @Test
    public void addRestaurantResponseGeneratorGeneratesResponseForInvalidDescription() {
        restaurant.responseHandler.responseBody = "";
        restaurant.address.city = "milan";
        restaurant.address.country = "italy";
        restaurant.address.street = "alferedo";
        restaurant.managerUsername = "me";
        restaurant.type = "italian";
        restaurant.description = "";
        restaurant.startTime = "11:0";
        restaurant.endTime = "12:0";
        restaurant.name = "vanila";

        restaurant.addRestaurantResponseGenerator();
        assertFalse(restaurant.responseHandler.responseStatus);
        assertEquals( " description is not valid.", restaurant.responseHandler.responseBody);
    }
}
