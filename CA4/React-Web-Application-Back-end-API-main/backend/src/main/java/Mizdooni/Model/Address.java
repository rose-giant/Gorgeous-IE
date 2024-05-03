package Mizdooni.Model;

public class Address {
    public String city;
    public String country;
    public String street;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }
    public Address(String city, String country, String street) {
        this.city = city;
        this.country = country;
        this.street = street;
    }

    public Address(){}
}
