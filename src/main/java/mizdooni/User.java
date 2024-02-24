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

}
