package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Table {
    public String tableNumber;

    public String restaurantName;

    public String managerUsername;

    public int seatsNumber;

    public Table(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Table table  = om.readValue(jsonString, Table.class);
        tableNumber = table.tableNumber;
        restaurantName = table.restaurantName;
        managerUsername = table.managerUsername;
    }

    public void checkTableProperties(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Table table  = om.readValue(jsonString, Table.class);
    }
}
