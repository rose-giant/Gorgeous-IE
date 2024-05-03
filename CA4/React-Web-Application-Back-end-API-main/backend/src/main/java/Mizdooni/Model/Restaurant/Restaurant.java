package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Address;
import Mizdooni.Model.ResponseHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public class Restaurant {
    public String name;
    public String managerUsername;
    public String type;
    public String startTime;
    public String endTime;
    public String description;
    public String image;
    public Address address = new Address();

    @JsonIgnore
    public double overall;
    @JsonIgnore
    private String id;

    public Restaurant(
            @JsonProperty("address") Address address,
            @JsonProperty("description") String description,
            @JsonProperty("endTime") String endTime,
            @JsonProperty("image") String image,
            @JsonProperty("managerUsername") String managerUsername,
            @JsonProperty("name")String name,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("type") String type
        ) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.image = image;
        this.managerUsername = managerUsername;
    }
    public Restaurant(){};

}
