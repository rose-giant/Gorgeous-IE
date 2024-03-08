package models;

import objects.*;


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
                if (restaurantData.length == 10) {

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
                    restaurant.id = restaurantData[9];
                    restaurantList.add(restaurant);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    public ArrayList<Review> readReviewsFromFile(String fileAddress) throws IOException {
        String line;
        String csvSplitBy = ",";
        ArrayList<Review> reviews = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileAddress))) {
            while ((line = br.readLine()) != null) {
                String[] reviewData = line.split(csvSplitBy);
                if (reviewData.length == 8) {

                    Review review = new Review();
                    review.responseHandler = new ResponseHandler();

                    review.restaurantName = reviewData[0];
                    review.username = reviewData[1];
                    review.reviewDate = reviewData[2];
                    review.comment = reviewData[3];
                    review.overall = Double.parseDouble(reviewData[4]);
                    review.foodRate = Double.parseDouble(reviewData[5]);
                    review.ambianceRate = Double.parseDouble(reviewData[6]);
                    review.serviceRate = Double.parseDouble(reviewData[7]);

                    reviews.add(review);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public ArrayList<Reservation> readReservationsFromFile(String fileAddress) throws IOException {
        String line;
        String csvSplitBy = ",";
        ArrayList<Reservation> reservations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileAddress))) {
            while ((line = br.readLine()) != null) {
                String[] restaurantData = line.split(csvSplitBy);
                if (restaurantData.length == 5) {

                    Reservation reservation = new Reservation();

                    reservation.username = restaurantData[0];
                    reservation.reservationNumber = Integer.parseInt(restaurantData[1]);
                    reservation.restaurantName = restaurantData[2];
                    reservation.tableNumber = Integer.parseInt(restaurantData[3]);
                    reservation.datetime = restaurantData[4];

                    reservations.add(reservation);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public ArrayList<Table> readTablesFromFile(String filePath) {
        String line;
        String csvSplitBy = ",";
        ArrayList<Table> tables = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 4) {
                    Table table = new Table();
                    table.restaurantName = data[0];
                    table.managerUsername = data[1];
                    table.tableNumber = Integer.parseInt(data[2]);
                    table.seatsNumber = Integer.parseInt(data[3]);
                    tables.add(table);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public String getActive(String filePath) throws IOException {
        String firstLine = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            firstLine = br.readLine();
        }
        return firstLine;
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


