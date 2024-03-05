import com.fasterxml.jackson.core.JsonProcessingException;
import models.MizDooni;
import objects.Address;

import objects.ResponseHandler;
import objects.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MizDooniTest {
    MizDooni commandHandler;
    Restaurant restourant;
    ResponseHandler responseHandler;
    Address address;
    @Before
    @BeforeEach
    public void setup() throws IOException {
        commandHandler = new MizDooni();
        restourant = new Restaurant();
        responseHandler = new ResponseHandler();
        address = new Address();
        restourant.address = address;
        restourant.responseHandler = responseHandler;
    }

    @Test
    public void searchRestaurantByNameReturnsCorrectResponseForNoneExistingRestaurant() throws JsonProcessingException {
        String jsonString = "{\"name\": \"namnam\"}";
        commandHandler.restaurants = new ArrayList<>();
        restourant.responseHandler = commandHandler.searchRestaurantByNameHandler(jsonString);
        assertFalse(restourant.responseHandler.responseStatus);
        assertEquals(restourant.responseHandler.responseBody, "Restaurant not found.");
    }

    @Test
    public void searchRestaurantByNameReturnsCorrectResponseForExistingRestaurant() throws JsonProcessingException {
        String jsonString = "{\"name\": \"namnam\"}";

        Restaurant restourant1 = new Restaurant("restName", "iranian", "21:00", "23:00", "desc",new Address("tehran", "iran"));
        restourant1.name = "namnam";
        commandHandler.restaurants = new ArrayList<>();
        commandHandler.restaurants.add(restourant1);
        String expected = "{\"name\":\"namnam\",\"managerUsername\":null,\"type\":null,\"startTime\":null,\"endTime\":null,\"parsedStartTime\":null,\"parsedEndTIme\":null,\"description\":null,\"address\":{\"city\":null,\"country\":null,\"street\":null},\"responseHandler\":null}";
        restourant.responseHandler = commandHandler.searchRestaurantByNameHandler(jsonString);
        assertTrue(restourant.responseHandler.responseStatus);
//        assertEquals(expected, restourant.responseHandler.responseBody);
    }

    @Test
    public void searchRestaurantByTypeReturnsCorrectResponseForNoneExistingRestaurant() throws JsonProcessingException {
        String jsonString = "{\"type\": \"italian\"}";
        commandHandler.restaurants = new ArrayList<>();
        restourant.responseHandler = commandHandler.searchRestaurantByTypeHandler(jsonString);
        assertFalse(restourant.responseHandler.responseStatus);
        assertEquals(restourant.responseHandler.responseBody, "Restaurant not found.");
    }

    @Test
    public void searchRestaurantByTYpeReturnsCorrectResponseForExistingRestaurant() throws JsonProcessingException {
        String jsonString = "{\"type\": \"iranian\"}";

        Restaurant restourant1 = new Restaurant("restName", "iranian", "21:00", "23:00", "desc",new Address("tehran", "iran"));
        restourant1.type = "iranian";
        commandHandler.restaurants = new ArrayList<>();
        commandHandler.restaurants.add(restourant1);
        String expected = "{\"name\":restName,\"managerUsername\":null,\"type\":\"iranian\",\"startTime\"21:00,\"endTime\":23:00,\"parsedStartTime\":null,\"parsedEndTIme\":null,\"description\":null,\"address\":{\"city\":null,\"country\":null,\"street\":null},\"responseHandler\":null}";
        restourant.responseHandler = commandHandler.searchRestaurantByTypeHandler(jsonString);
        assertTrue(restourant.responseHandler.responseStatus);
//        assertEquals(expected, restourant.responseHandler.responseBody);
    }
}
