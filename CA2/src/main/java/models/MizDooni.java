package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.Reader;
import objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.SplittableRandom;

import static models.Addresses.*;

public class MizDooni {
    private static MizDooni instance;
    public ResponseHandler responseHandler = new ResponseHandler();
    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Review> reviews = new ArrayList<>();
    public ArrayList<Restaurant> restaurants = new ArrayList<>();
    ObjectMapper om = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    Object returnedData;
    Restaurant relatedRestaurant;
    User relatedUser;
    Table relatedTable;
    Reservation relatedReservation;
    Restaurant currentRestaurant = new Restaurant();

    public MizDooni() throws IOException {
        Reader rd = new Reader();
        this.users = rd.readUsersFromFile(USERS_CSV);
        this.restaurants = rd.readRestaurantsFromFile(RESTAURANTS_CSV);
//        tables = rd.readFromFile(DATABASE_ADDRESS +"tables.csv", Table.class);
//        reservations = rd.readFromFile(DATABASE_ADDRESS +"reservations.csv", Reservation.class);
    }

    public static MizDooni getInstance() throws Exception {
        if(instance == null)
            instance = new MizDooni();
        return instance;
    }

    public boolean userAlreadyExists(User user) {
        System.out.println(user.username + ", "+user.password);
        for(User value: users) {
            System.out.println(value.username + " and "+value.password);
            if (Objects.equals(user.username, value.username) && Objects.equals(user.password, value.password)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Restaurant> getRestaurantsByName(String search) {
        ArrayList<Restaurant> restaurants1 = new ArrayList<>();
            for (Restaurant r : restaurants) {
                if (r.name.contains(search)) {
                    restaurants1.add(r);
                }
            }

            return restaurants1;
    }

    public ArrayList<Restaurant> filterRestaurants(String filter, String search) {
        ArrayList<Restaurant> restaurants1 = new ArrayList<>();
        switch (filter) {
            case "search_by_type":

                break;

            case "search_by_name":
                restaurants1 = getRestaurantsByName(search);
                break;

            case "search_by_city":
                break;

            default:
                restaurants1 = restaurants;
        }

        return restaurants1;
    }

    public String createHTMLForRestaurantsList(String filter, String search) {
        System.out.println("mizdooni says filter is " + filter + " and search is " + search);
        ArrayList<Restaurant> filteredRestaurants = filterRestaurants(filter, search);
        String html = "";
        for (Restaurant r : filteredRestaurants) {
            getRestaurantScores(r.name);
            html += "<tr>\n" +
                    "        <th>" + r.id +"</th>\n" +
                    "        <th>" + "<a href=" +"restaurant?" + r.name+ ">" + r.name + "</a>" + "</th>\n" +
                    "        <th>" + r.address.city + "</th>\n" +
                    "        <th>" + r.type + "</th>\n" +
                    "        <th>" + r.startTime + " - " + r.endTime + "</th>\n" +
                    "        <th>"+ relatedRestaurantOverall +"</th>\n" +
                    "        <th>"+ relatedRestaurantFood +"</th>\n" +
                    "        <th>" + relatedRestaurantAmbiance + "</th>\n" +
                    "        <th>" + relatedRestaurantService + "</th>\n" +
                    "    </tr>";
        }
//<a href="/error">hi</a>
        return html;
    }

    public double relatedRestaurantOverall;
    public double relatedRestaurantAmbiance;
    public double relatedRestaurantFood;
    public double relatedRestaurantService;

    public void getRestaurantScores(String restaurantName) {
        double overallSum = 0;
        double ambianceSum = 0;
        double foodSum = 0;
        double serviceSum = 0;
        for (Review r : reviews) {
            if(Objects.equals(r.restaurantName, restaurantName)) {
                overallSum += r.overall;
                ambianceSum += r.ambianceRate;
                foodSum += r.foodRate;
                serviceSum += r.serviceRate;
            }
        }

        relatedRestaurantOverall = (overallSum) / reviews.size();
        relatedRestaurantAmbiance = (ambianceSum) / reviews.size();
        relatedRestaurantFood = (foodSum) / reviews.size();
        relatedRestaurantService = (serviceSum) / reviews.size();
    }

    public boolean restaurantManagerUsernameExists(String managerUsername) {
        boolean exists = false;
        for(User value: users) {
            if (Objects.equals(managerUsername, value.username)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean userRoleIsManager(String managerUsername) {
        boolean isCorrect = false;
        for(User value: users) {
            if (Objects.equals(managerUsername, value.username)) {
                if (Objects.equals(value.role, User.MANAGER_ROLE)) {
                    isCorrect = true;
                    break;
                }
            }
        }
        return isCorrect;
    }

    public Restaurant findRestaurantByName(String restaurantName) {
        System.out.println("req name is " + restaurantName);
        for(Restaurant rest: restaurants) {
            System.out.println("iterate" + rest.name);
            if(Objects.equals(rest.name, restaurantName)) {
                System.out.println("it is " + rest.name);
                return rest;
            }
        }
        return null;
    }

    public User findUserByUserName(String userName) {
        for(User user: users) {
            if(Objects.equals(user.username, userName)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(String jsonString) throws JsonProcessingException {
        User user = new User();
        user.addUserHandler(jsonString);
        if(userAlreadyExists(user)) {
        user.handleRepeatedUser();
        }
        if (user.responseHandler.responseStatus) {
        users.add(user);
        }
        responseHandler = user.responseHandler;
    }

    public boolean restaurantNameAlreadyExists(String restaurantName) {
        boolean alreadyExists = false;
        for(Restaurant value: restaurants) {
            if(Objects.equals(value.name, restaurantName)) {
                alreadyExists = true;
                break;
            }
        }
        return alreadyExists;
    }

    public ResponseHandler searchRestaurantByNameHandler(String jsonString) throws JsonProcessingException {
        Restaurant restaurant = new Restaurant();
        restaurant = restaurant.unmarshlIntoRestaurant(jsonString);
        ResponseHandler responseHandler1 = new ResponseHandler();
        responseHandler1.responseBody = "Restaurant not found.";
        responseHandler1.responseStatus = false;

        for(Restaurant value : restaurants) {
            if (Objects.equals(restaurant.name, value.name)) {
                responseHandler1.responseBody = value.marshalRestaurant(value);
                responseHandler1.responseStatus = true;
                break;
            }
        }
        return responseHandler1;
    }

    public ResponseHandler searchRestaurantByTypeHandler(String jsonString) throws JsonProcessingException {
        Restaurant restaurant = new Restaurant();
        restaurant = restaurant.unmarshlIntoRestaurant(jsonString);
        ResponseHandler responseHandler1 = new ResponseHandler();
        responseHandler1.responseBody = "Restaurant not found.";
        responseHandler1.responseStatus = false;

        for(Restaurant value : restaurants) {
            if (Objects.equals(restaurant.type, value.type)) {
                responseHandler1.responseBody = value.marshalRestaurant(value);
                responseHandler1.responseStatus = true;
                break;
            }
        }
        return responseHandler1;
    }


    public void addRestaurant(String jsonString) throws JsonProcessingException {
        Restaurant restaurant = new Restaurant();
        restaurant.addRestaurantHandler(jsonString);

        if (!restaurantManagerUsernameExists(restaurant.managerUsername)) {
            restaurant.handleOuterErrorMessage(" manager username does not exist.");
        }

        if(!userRoleIsManager(restaurant.managerUsername)) {
            restaurant.handleOuterErrorMessage(" manager role is not correct.");
        }

        if(restaurantNameAlreadyExists(restaurant.name)) {
            restaurant.handleOuterErrorMessage(" restaurant name is repeated.");
        }

        if (restaurant.responseHandler.responseStatus) {
            restaurants.add(restaurant);
        }
        responseHandler = restaurant.responseHandler;
    }

    public void searchRestaurantsByName(String jsonString) throws JsonProcessingException {
        responseHandler = searchRestaurantByNameHandler(jsonString);
    }

    public void searchRestaurantsByType(String jsonString) throws JsonProcessingException {
        responseHandler = searchRestaurantByTypeHandler(jsonString);
    }

    public void addTable(String jsonString) throws Exception {
        Table table = new Table(jsonString);
        relatedRestaurant = findRestaurantByName(table.restaurantName);
        relatedUser = findUserByUserName(table.managerUsername);
        if (relatedUser == null){
            throw new Exception("Manager username not found.");
        } else if (Objects.equals(relatedUser.role, User.CLIENT_ROLE) ||
                !Objects.equals(relatedRestaurant.managerUsername, table.managerUsername)) {
            throw new Exception("This user is not allowed to add a table.");
        }
        if (relatedRestaurant == null){
            throw new Exception("Restaurant name not found.");
        }else if(relatedRestaurant.findTableByNumber(table.tableNumber) != null){
            throw new Exception("Table number already exists.");
        }

        relatedRestaurant.addTable(table);
        returnedData = "Table added successfully.";
        responseHandler = new ResponseHandler(true, returnedData);
    }

    public void reserveTable(String jsonString) throws Exception {
        Reservation reservation = new Reservation(jsonString);
        relatedRestaurant = findRestaurantByName(reservation.restaurantName);
        relatedUser = findUserByUserName(reservation.username);
        if (relatedUser == null){
            throw new Exception("Username not found.");
        } else if (Objects.equals(relatedUser.role, User.MANAGER_ROLE)) {
            throw new Exception("This user is not allowed to reserve a table.");
        }
        if (relatedRestaurant == null){
            throw new Exception("Restaurant name not found.");
        } else if (!relatedRestaurant.isOpenAt(reservation.datetimeFormatted)){
            throw new Exception("Restaurant doesn't work at this DateTime");
        }
        relatedRestaurant.reserve(reservation);
        //Because we need to handle showReservationHistory command, it's better to add reservation to the ordering user too.
        relatedUser.addReservation(reservation);

        returnedData = new Reservation.ResNumber(reservation.reservationNumber);
        this.responseHandler = new ResponseHandler(true, returnedData);

    }

    public void cancelReservation(String jsonString) throws Exception {
        Reservation.CancelReservation cr = om.readValue(jsonString, Reservation.CancelReservation.class);
        relatedUser = findUserByUserName(cr.username);
        relatedReservation = relatedUser.findReservationByNumber(cr.reservationNumber);
        if(relatedReservation == null){
            throw new Exception("Reservation not found");
        }
        relatedRestaurant = findRestaurantByName(relatedReservation.restaurantName);
        relatedReservation.checkSafetyRemoval();

        relatedUser.removeReservation(relatedReservation);
        relatedRestaurant.removeReservation(relatedReservation);

        returnedData = "Reservation cancelled successfully";
        responseHandler = new ResponseHandler(true, returnedData);
    }

    public void showReservationHistory(String jsonString) throws JsonProcessingException {
        User.UserName un = om.readValue(jsonString, User.UserName.class);
        relatedUser = findUserByUserName(un.username);

        returnedData = relatedUser.getReservationHistory();
        responseHandler = new ResponseHandler(true, returnedData);
    }

    public void showAvailableTables(String jsonString) throws Exception {
        Restaurant.RestaurantName rn = om.readValue(jsonString, Restaurant.RestaurantName.class);
        relatedRestaurant = findRestaurantByName(rn.restaurantName);

        if (relatedRestaurant == null) {
            throw new Exception("Restaurant name not found.");
        }
        returnedData = relatedRestaurant.getAvailableTables();
        this.responseHandler = new ResponseHandler(true, returnedData);
    }

    public void addReview(String jsonString) throws JsonProcessingException {
        Review review = new Review();
        review.addReviewHandler(jsonString);
        relatedUser = findUserByUserName(review.username);
        if(!userAlreadyExists(relatedUser)) {
            review.handleOuterErrorMessage(" username does not exist.");
        }

        if(userRoleIsManager(review.username)) {
            review.handleOuterErrorMessage(" username role is not client.");
        }

        if(!restaurantNameAlreadyExists(review.restaurantName)) {
            review.handleOuterErrorMessage(" restaurant name does not exist.");
        }

        if(!relatedUser.hasExperienced(review.restaurantName)){
            review.handleOuterErrorMessage(" You are not allowed to comment on this restaurant.");
        }

        relatedRestaurant.addReview(review);
        reviews.add(review);
        responseHandler = review.responseHandler;
    }

    public void saveActiveUser(User user) {
        Writer writer = new Writer();
        writer.writeUser(user);
    }

    public void saveActiveRestaurant(Restaurant restaurant) {
        Writer writer = new Writer();
        writer.writeRestaurant(restaurant);
    }

    public User findUserByUsernameAndPass(String username, String password) {
        User user = findUserByUserName(username);
        if(user != null && Objects.equals(user.password, password)){
            return user;
        }
        return null;
    }
}

