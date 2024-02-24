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
}
