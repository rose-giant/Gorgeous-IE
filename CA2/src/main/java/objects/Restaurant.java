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
    public String description;
    public String country;
    public String city;
    public String street;
    public Address address = new Address();
    public ResponseHandler responseHandler;
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

    public String marshalRestaurant(Restaurant restaurant) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(restaurant);
    }

    public Restaurant unmarshlIntoRestaurant(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonString, Restaurant.class);
    }
}
