package models;

import objects.Address;
import objects.ResponseHandler;
import objects.Restaurant;
import objects.User;


import java.io.*;
import java.util.ArrayList;

public class Reader {
    public ArrayList<User> readUsersFromFile(String fileAddress) throws IOException {
        String line;
        String csvSplitBy = ",";
        ArrayList<User> userList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileAddress))) {
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(csvSplitBy);
                if (userData.length == 6) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
                    userList.add(user);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public ArrayList<Restaurant> readRestaurantsFromFile(String fileAddress) throws IOException {
        String line;
        String csvSplitBy = ",";
        ArrayList<Restaurant> restaurantList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileAddress))) {
            while ((line = br.readLine()) != null) {
                String[] restaurantData = line.split(csvSplitBy);
                if (restaurantData.length == 9) {

                    Restaurant restaurant = new Restaurant();
                    restaurant.responseHandler = new ResponseHandler();

                    restaurant.name = restaurantData[0];
                    restaurant.managerUsername = restaurantData[1];
                    restaurant.type = restaurantData[2];
                    restaurant.startTime = restaurantData[3];
                    restaurant.endTime = restaurantData[4];
                    restaurant.description = restaurantData[5];
                    restaurant.country = restaurantData[6];
                    restaurant.city = restaurantData[7];
                    restaurant.street = restaurantData[8];
                    restaurantList.add(restaurant);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(restaurantList.get(0).name + restaurantList.get(0).city);
        return restaurantList;
    }

//    public static void main(String[] args) throws IOException {
//        Reader  csvReader = new Reader();
//        ArrayList<User> users;
//        users = csvReader.readFromFile("src/main/resources/users.csv", User.class);
//        System.out.println(users.size());
//        System.out.println(users.get(0).username);
//        System.out.println(users.get(1).password);
//        System.out.println(users.get(0).role);
//
//    }
}


