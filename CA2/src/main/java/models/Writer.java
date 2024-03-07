package models;

import objects.Address;
import objects.ResponseHandler;
import objects.Restaurant;
import objects.User;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import static models.Addresses.CURRENT_RESTAURANT_ADDRESS;
import static models.Addresses.CURRENT_USER_ADDRESS;

public class Writer {

    public static void writeUser(User user) {
        String fileName = CURRENT_USER_ADDRESS;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(user.username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeRestaurant(Restaurant restaurant) {
        String fileName = CURRENT_RESTAURANT_ADDRESS;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(restaurant.name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.username = "rose";
        user.address = new Address();
        user.responseHandler = new ResponseHandler();
        writeUser(user);

        User user1 = new User();
        user1.username = "j";
        user1.address = new Address();
        user1.responseHandler = new ResponseHandler();
        writeUser(user1);

        Restaurant r = new Restaurant();
        r.name = "charstoon";
        r.address = new Address();
        r.responseHandler = new ResponseHandler();
        writeRestaurant(r);
    }
}
