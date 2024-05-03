package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.ArrayList;
import java.util.regex.Pattern;


@Getter
public class User {
    public String role;
    public String username;
    public  String password;
    public String email;
    public Address address;

    @JsonIgnore
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public User(@JsonProperty("address") Address address,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("role") String role,
                @JsonProperty("username") String username
                ){
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }
    public User(){};
}
