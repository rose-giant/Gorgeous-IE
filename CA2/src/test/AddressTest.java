import objects.Address;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressTest {
    Address address;

    @Before
    public void setup() {
        address = new Address();
    }
    @Test
    public void isCityValidReturnsFalseForEmptyCity() {
        String city = "";
        assertFalse(address.isCityValid(city));
    }

    @Test
    public void isCityValidReturnsTrueForValidCity() {
        String city = "Milan";
        assertTrue(address.isCityValid(city));
    }

    @Test
    public void isCountryValidReturnsFalseForEmptyCountry() {
        String country = "";
        assertFalse(address.isCountryValid(country));
    }

    @Test
    public void isCountryValidReturnsTrueForValidCountry() {
        String city = "Italy";
        assertTrue(address.isCountryValid(city));
    }
}
