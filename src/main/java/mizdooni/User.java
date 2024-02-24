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

}
