package mizdooni;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.plaf.SplitPaneUI;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    public enum POSITION {
        MANAGER, SOFTWARE_ENGINEER
    }
    private static final String ROLE_1 = "manager";
    private static final String ROLE_2 = "client";
    private char[] forbiddenCharacters = {'`', '~', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-',
            '=', '{', '[', '}', ']', ';', ':', '|', '/', '\'', '.', '>', ',', '<', ' '};
    public String role;
    public ArrayList<String> validRoles = new ArrayList<String>();
    public String username;
    public  String password;
    public String email;
    public Address address;

    public ResponseHandler responseHandler;
    public String addUserResponse;
    public ArrayList<User> users = new ArrayList<User>();
    public boolean isRoleValid(String role) {
        validRoles.add(ROLE_1);
        validRoles.add(ROLE_2);
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

    public boolean addUserHandler(String jsonString) throws JsonProcessingException {
        User user = unmarshlIntoUser(jsonString);
        user.responseHandler = new ResponseHandler();
        user.responseHandler = addUserResponseGenerator(user);
        this.responseHandler = user.responseHandler;
        this.address = user.address;
        this.role = user.role;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        return user.responseHandler.responseStatus;
    }
}
