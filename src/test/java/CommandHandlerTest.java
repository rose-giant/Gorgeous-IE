import com.fasterxml.jackson.core.JsonProcessingException;
import mizdooni.Address;
import mizdooni.CommandHandler;
import mizdooni.ResponseHandler;
import mizdooni.Restourant;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandHandlerTest {
    CommandHandler commandHandler;
    Restourant restourant;
    ResponseHandler responseHandler;
    Address address;
    @Before
    @BeforeEach
    public void setup() {
        commandHandler = new CommandHandler();
        restourant = new Restourant();
        responseHandler = new ResponseHandler();
        address = new Address();
        restourant.address = address;
        restourant.responseHandler = responseHandler;
    }
    @Test
    public void parseCommandParsesCommandAndJsonBody() throws JsonProcessingException {
        String inputJsonString = "{\"username\": \"razie\"}";
        String inputCommand = "addUser ";
        commandHandler.parseCommand(inputCommand+inputJsonString);
        assertEquals(inputJsonString, commandHandler.jsonString);
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

        Restourant restourant1 = new Restourant();
        restourant1.name = "namnam";
        commandHandler.restaurants = new ArrayList<>();
        commandHandler.restaurants.add(restourant1);
        String expected = "{\"name\":\"namnam\",\"managerUsername\":null,\"type\":null,\"startTime\":null,\"endTime\":null,\"parsedStartTime\":null,\"parsedEndTIme\":null,\"description\":null,\"address\":{\"city\":null,\"country\":null,\"street\":null},\"responseHandler\":null}";
        restourant.responseHandler = commandHandler.searchRestaurantByNameHandler(jsonString);
        assertTrue(restourant.responseHandler.responseStatus);
        assertEquals(expected, restourant.responseHandler.responseBody);
    }
}
