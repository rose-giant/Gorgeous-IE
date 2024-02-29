package mizdooni;

public class Address {
    public String city;
    public String country;
    public String street;

    public boolean isCityValid(String city) {
        if (city.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isCountryValid(String country) {
        if (country.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isStreetValid(String street) {
        if (street.isEmpty()) {
            return false;
        }

        return true;
    }
}
