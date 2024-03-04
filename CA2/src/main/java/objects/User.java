package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

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
    @JsonCreator
    public User(@JsonProperty("role") String role, @JsonProperty("username") String username,
                @JsonProperty("password") String password, @JsonProperty("email") String email
                //,@JsonProperty("address") Address address
                ,@JsonProperty("city") String city,  @JsonProperty("country") String country
            ){
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = new Address(city, country);
    }
    public User(){};

    public ResponseHandler responseHandler;
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public boolean isRoleValid(String role) {
        validRoles.add(MANAGER_ROLE);
        validRoles.add(CLIENT_ROLE);
        if (validRoles.contains(role)) {
            return true;
        }  else {
            return false;
        }
    }

    public boolean isUsernameValid(String username) {
        for (int i = 0 ; i < forbiddenCharacters.length ; i++) {
            if (username.contains(forbiddenCharacters[i]+"")) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmailValid(String email) {
        String[] adsSplit = email.split("@");

        if (adsSplit.length != 2 ) {
            return false;
        }

        if (!adsSplit[1].endsWith(".com")) {
            return false;
        }

        String middleSplit = (String) adsSplit[1].subSequence(0, adsSplit[1].length() - 4);
        if (middleSplit.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isPasswordValid(String password) {
        return !password.isEmpty();
    }

    public boolean isAddressValid(Address address) {
        boolean isCityValid = address.isCityValid(address.city);
        boolean isCountryValid = address.isCountryValid(address.country);
        return isCountryValid && isCityValid;
    }

    public User unmarshlIntoUser(String jsonString) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        User user  = om.readValue(jsonString, User.class);
        return user;
    }

    public ResponseHandler addUserResponseGenerator(User user) {
        user.responseHandler.responseBody = "";
        boolean isRoleValid = isRoleValid(user.role);
        if (!isRoleValid) {
            user.responseHandler.responseBody += " role is not valid.";
        }
        boolean isUsernameValid = isUsernameValid(user.username);
        if (!isUsernameValid) {
            user.responseHandler.responseBody += " username is not valid.";
        }
        boolean isPasswordValid = isPasswordValid(user.password);
        if (!isPasswordValid) {
            user.responseHandler.responseBody += " password is not valid.";
        }
        boolean isEmailValid = isEmailValid(user.email);
        if (!isEmailValid) {
            user.responseHandler.responseBody += " email address is not valid.";
        }
        boolean isAddressValid = isAddressValid(user.address);
        if (!isAddressValid) {
            user.responseHandler.responseBody += " user address is not valid.";
        }

        boolean isUserRepeated = isUserRepeated(user);
        if (isUserRepeated) {
            user.responseHandler.responseBody += " user already exists.";
        }

        user.responseHandler.responseStatus = isRoleValid && isUsernameValid && isPasswordValid && isEmailValid && isAddressValid && !isUserRepeated;
        if (user.responseHandler.responseStatus) {
            user.responseHandler.responseBody += "User added successfully.";
            users.add(user);
        }
        return user.responseHandler;
    }

    public boolean isUserRepeated(User user) {
        for (User value : users) {
            System.out.println(value.username);
            if (Objects.equals(user.username, value.username) || Objects.equals(user.email, value.email)) {
                return true;
            }
        }

        return false;
    }

    public void handleRepeatedUser() {
        this.responseHandler.responseBody = " user already exists.";
        this.responseHandler.responseStatus = false;
    }

    public String useless(String name) {
        return "hello"+name;
    }
    public void addUserHandler(String jsonString) throws JsonProcessingException {
        User user = unmarshlIntoUser(jsonString);
        user.responseHandler = new ResponseHandler();
        user.responseHandler = addUserResponseGenerator(user);
        this.responseHandler = user.responseHandler;
        this.address = user.address;
        this.role = user.role;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean hasReserved(int reservationNumber) {
        for (Reservation reservation: reservations) {
            if (reservation.reservationNumber == reservationNumber){
                return true;
            }
        }
        return false;
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public  ReservationList getReservationHistory() {
        ArrayList<Reservation.ReservationInfo> resInfo = new ArrayList<>();
        for (Reservation res:reservations) {
            resInfo.add(new Reservation.ReservationInfo(res.restaurantName, res.reservationNumber, res.tableNumber, res.datetime));
        }
        return new ReservationList(resInfo);
    }

    public static class UserName{
        public String username;
        @JsonCreator
        public UserName(@JsonProperty("username") String username) {
            this.username = username;
        }
    }

    static class ReservationList{
        public ArrayList<Reservation.ReservationInfo> reservationHistory;
        @JsonCreator
        public ReservationList(@JsonProperty("reservationHistory") ArrayList<Reservation.ReservationInfo> reservationHistory) {
            this.reservationHistory = reservationHistory;
        }
    }

}
