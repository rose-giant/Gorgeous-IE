import com.fasterxml.jackson.core.JsonProcessingException;
import mizdooni.Address;
import mizdooni.ResponseHandler;
import mizdooni.User;
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

}


















