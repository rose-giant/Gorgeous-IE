import com.fasterxml.jackson.core.JsonProcessingException;

import objects.Address;
import objects.ResponseHandler;
import objects.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;

public class UserTest {
    static User user = new User();
    static Address address;
    static ResponseHandler responseHandler;

    @Before
    @BeforeEach
    public void setup() {
        user = new User();
        address = new Address();
        responseHandler = new ResponseHandler();
        responseHandler.responseBody = "";
        user.address = address;
        user.responseHandler = responseHandler;

    }

    @AfterEach
    public void teardown() {
        user = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"manger", "ient", "", "chef", "1"})
    public void isRoleValidReturnsFalseForInvalidRoles(String role) {
        assertFalse(user.isRoleValid(role));
    }

    @ParameterizedTest
    @ValueSource(strings = {"manager", "client"})
    public void isRoleValidReturnsTrueForValidRoles(String role) {
        assertTrue(user.isRoleValid(role));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@a.com", "12sdfwf@a.com", "sdfsf@aa...a.com"})
    public void isEmailValidReturnsTrueForValidEmails(String email) {
        boolean isEmailValid = user.isEmailValid(email);
        assertTrue(isEmailValid);
    }

    //@asdas.com
    @ParameterizedTest
    @ValueSource(strings = {"a@.com", "a.com", "sdfsf@aa...acom", "@asdascom"})
    public void isEmailValidReturnsFalseForValidEmails(String email) {
        boolean isEmailValid = user.isEmailValid(email);
        assertFalse(isEmailValid);
    }

    @Test
    public void isPasswordValidReturnsFalseForEmptyPassword() {
        assertFalse(user.isPasswordValid(""));
    }
    @Test
    public void unmarshalUserIntoObject() throws JsonProcessingException {
        String jsonString = "{\"role\":\"manager\",\"username\":\"rosepose\",\"password\":\"John Doe\",\"email\":\"john.doe@example.com\", \"address\":{\"city\":\"milan\", \"country\":\"italy\"}}";
        user = user.unmarshlIntoUser(jsonString);
        assertEquals(user.role, "manager");
        assertEquals(user.username, "rosepose");
        assertEquals(user.email, "john.doe@example.com");
        assertEquals(user.address.city, "milan");
        assertEquals(user.address.country, "italy");
    }

    @Test
    public void addUserResponseGeneratorTestForInvalidUserRole() {
        user.role = "chef";
        user.username = "razi";
        user.password = "12";
        user.email = "r@r.com";
        address.city = "milan";
        address.country = "italy";
        user.address = address;
        user.addUserResponseGenerator(user);
        ResponseHandler expectesResponse = new ResponseHandler();
        expectesResponse.responseBody =" role is not valid.";
        expectesResponse.responseStatus = false;
        assertEquals(user.responseHandler.responseBody, expectesResponse.responseBody);
        assertEquals(expectesResponse.responseStatus, user.responseHandler.responseStatus);
    }

    @ParameterizedTest
    @ValueSource (strings = {"sojfs&", "**sfs", "wwe.33"})
    public void addUserResponseGeneratorTestForInvalidUsername(String username) {
        user.role = "manager";
        user.username = username;
        user.password = "12";
        user.email = "r@r.com";
        address.city = "milan";
        address.country = "italy";
        user.address = address;
        user.addUserResponseGenerator(user);
        ResponseHandler expectesResponse = new ResponseHandler();
        expectesResponse.responseBody =" username is not valid.";
        expectesResponse.responseStatus = false;
        assertEquals(user.responseHandler.responseBody, expectesResponse.responseBody);
        assertEquals(expectesResponse.responseStatus, user.responseHandler.responseStatus);
    }

    @Test
    public void addUserResponseGeneratorTestForValidUsername() {
        user.role = "manager";
        user.username = "razi";
        user.password = "12";
        user.email = "r@r.com";
        address.city = "milan";
        address.country = "italy";
        user.address = address;
        user.addUserResponseGenerator(user);
        ResponseHandler expectesResponse = new ResponseHandler();
        expectesResponse.responseBody ="User added successfully.";
        expectesResponse.responseStatus = true;
        assertEquals(user.responseHandler.responseBody, expectesResponse.responseBody);
        assertEquals(expectesResponse.responseStatus, user.responseHandler.responseStatus);
    }
}


















