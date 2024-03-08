package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@JsonPropertyOrder({ "role", "username", "password", "email", "city", "country"})
public class User {
    public static final String MANAGER_ROLE = "manager";
    public static final String CLIENT_ROLE = "client";
    private final char[] forbiddenCharacters = {'`', '~', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-',
            '=', '{', '[', '}', ']', ';', ':', '|', '/', '\'', '.', '>', ',', '<', ' '};
    public ArrayList<String> validRoles = new ArrayList<>();

    public String role;
    public String username;
    public  String password;
    public String email;
    public String city;
    public String country;
    public Address address;
    public ResponseHandler responseHandler;
    public ArrayList<Reservation> reservations = new ArrayList<>();
    @JsonCreator
    public User(@JsonProperty("role") String role, @JsonProperty("username") String username,
                @JsonProperty("password") String password, @JsonProperty("email") String email
                ,@JsonProperty("city") String city,  @JsonProperty("country") String country
            ){
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = new Address(city, country);
        this.responseHandler = new ResponseHandler();
    }
    public User(){};
    public boolean isUsernameValid(String username) {
        for (int i = 0 ; i < forbiddenCharacters.length ; i++) {
            if (username.contains(forbiddenCharacters[i]+"")) {
                return false;
            }
        }

        return true;
    }
}
