package objects;

public class Address {
    public String city;
    public String country;
    public String street;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public Address(){}

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
