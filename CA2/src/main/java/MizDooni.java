import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.Reader;
import objects.*;

import java.util.ArrayList;
import java.util.Objects;

public class MizDooni {
    public ResponseHandler responseHandler = new ResponseHandler();
    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Restourant> restaurants = new ArrayList<>();
    public ArrayList<Table> tables = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();

    ObjectMapper om = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    Object returnedData;
    Restourant relatedRestaurant;
    User relatedUser;
    Table relatedTable;
    Reservation relatedReservation;


    public MizDooni() {
        Reader rd = new Reader();
        users = rd.ReadFromFile("users", User.class);
        restaurants = rd.ReadFromFile("restaurants", Restourant.class);
        tables = rd.ReadFromFile("tables", Table.class);
        reservations = rd.ReadFromFile("reservations", Reservation.class);
    }

    public boolean userAlreadyExists(User user) {
        for(User value: users) {
            if (Objects.equals(user.username, value.username) || Objects.equals(user.email, value.email)) {
                return true;
            }
        }

        return false;
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

    public Restourant findRestaurantByName(String restaurantName) {
        for(Restourant rest: restaurants) {
            if(Objects.equals(rest.name, restaurantName)) {
                return rest;
            }
        }
        return null;
    }

    public Table findTableByRestaurantNameAndTableNumber(String restaurantName, int tableNumber) {
        for(Table tb: tables) {
            if(Objects.equals(tb.restaurantName, restaurantName) && Objects.equals(tb.tableNumber, tableNumber)) {
                return tb;
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

    private Reservation findReservationByNumber(int reservationNumber) {
        for(Reservation res: reservations) {
            if(Objects.equals(res.reservationNumber, reservationNumber)) {
                return res;
            }
        }
        return null;
    }

    public ArrayList<Table> findTablesByRestaurantName(String restaurantName) {
        ArrayList<Table> wantedTables = new ArrayList<>();
        for(Table tb: tables) {
            if(Objects.equals(tb.restaurantName, restaurantName)) {
                wantedTables.add(tb);
            }
        }
        return wantedTables;
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
        for(Restourant value: restaurants) {
            if(Objects.equals(value.name, restaurantName)) {
                alreadyExists = true;
                break;
            }
        }
        return alreadyExists;
    }

    public ResponseHandler searchRestaurantByNameHandler(String jsonString) throws JsonProcessingException {
        Restourant restourant = new Restourant();
        restourant = restourant.unmarshlIntoRestaurant(jsonString);
        ResponseHandler responseHandler1 = new ResponseHandler();
        responseHandler1.responseBody = "Restaurant not found.";
        responseHandler1.responseStatus = false;

        for(Restourant value : restaurants) {
            if (Objects.equals(restourant.name, value.name)) {
                responseHandler1.responseBody = value.marshalRestaurant(value);
                responseHandler1.responseStatus = true;
                break;
            }
        }
        return responseHandler1;
    }

    public ResponseHandler searchRestaurantByTypeHandler(String jsonString) throws JsonProcessingException {
        Restourant restourant = new Restourant();
        restourant = restourant.unmarshlIntoRestaurant(jsonString);
        ResponseHandler responseHandler1 = new ResponseHandler();
        responseHandler1.responseBody = "Restaurant not found.";
        responseHandler1.responseStatus = false;

        for(Restourant value : restaurants) {
            if (Objects.equals(restourant.type, value.type)) {
                responseHandler1.responseBody = value.marshalRestaurant(value);
                responseHandler1.responseStatus = true;
                break;
            }
        }
        return responseHandler1;
    }


    public void addRestaurant(String jsonString) throws JsonProcessingException {
        Restourant restourant = new Restourant();
        restourant.addRestaurantHandler(jsonString);

        if (!restaurantManagerUsernameExists(restourant.managerUsername)) {
            restourant.handleOuterErrorMessage(" manager username does not exist.");
        }

        if(!userRoleIsManager(restourant.managerUsername)) {
            restourant.handleOuterErrorMessage(" manager role is not correct.");
        }

        if(restaurantNameAlreadyExists(restourant.name)) {
            restourant.handleOuterErrorMessage(" restaurant name is repeated.");
        }

        if (restourant.responseHandler.responseStatus) {
            restaurants.add(restourant);
            System.out.println(restaurants.size());
        }
        responseHandler = restourant.responseHandler;
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
        } else if (Objects.equals(relatedUser.role, User.CLIENT_ROLE)) {
            throw new Exception("This user is not allowed to add a table.");
        }
        if (relatedRestaurant == null){
            throw new Exception("Restaurant name not found.");
        }
        if (findTableByRestaurantNameAndTableNumber(table.restaurantName, table.tableNumber) != null){
            throw new Exception("Table number already exists.");
        }

        tables.add(table);
        relatedRestaurant.addTable(table);
        returnedData = "Table added successfully.";
        responseHandler = new ResponseHandler(true, returnedData);
    }

    public void reserveTable(String jsonString) throws Exception {
        Reservation reservation = new Reservation(jsonString);
        relatedRestaurant = findRestaurantByName(reservation.restaurantName);
        relatedUser = findUserByUserName(reservation.username);
        relatedTable = findTableByRestaurantNameAndTableNumber(reservation.restaurantName, reservation.tableNumber);
        if (relatedUser == null){
            throw new Exception("Username not found.");
        } else if (Objects.equals(relatedUser.role, User.MANAGER_ROLE)) {
            throw new Exception("This user is not allowed to reserve a table.");
        }
        if (relatedRestaurant == null){
            throw new Exception("Restaurant name not found.");
        } else if (relatedTable == null){
            throw new Exception("Table number not found.");
        } else if (!relatedRestaurant.isOpenAt(reservation.datetimeFormatted)){
            throw new Exception("Restaurant doesn't work at this DateTime");
        }
        relatedRestaurant.reserve(reservation);
        relatedTable.addReservation(reservation);
        reservations.add(reservation);

        //Because we need to handle showReservationHistory command, it's better to add reservation to the ordering user too.
        relatedUser.addReservation(reservation);

        returnedData = new Reservation.ResNumber(reservation.reservationNumber);
        this.responseHandler = new ResponseHandler(true, returnedData);

    }

    public void cancelReservation(String jsonString) throws Exception {
        Reservation.CancelReservation cr = om.readValue(jsonString, Reservation.CancelReservation.class);
        relatedUser = findUserByUserName(cr.username);
        relatedReservation = findReservationByNumber(cr.reservationNumber);
        if(!relatedUser.hasReserved(cr.reservationNumber)){
            throw new Exception("Reservation not found");
        }
        relatedTable = findTableByRestaurantNameAndTableNumber(relatedReservation.restaurantName, relatedReservation.tableNumber);
        relatedReservation.checkSafetyRemoval();

        reservations.remove(relatedReservation);
        relatedUser.removeReservation(relatedReservation);
        relatedTable.removeReservation(relatedReservation);

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
        Restourant.RestaurantName rn = om.readValue(jsonString, Restourant.RestaurantName.class);
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
        User user1 = new User();
        user1.username = review.username;
        if(!userAlreadyExists(user1)) {
        review.handleOuterErrorMessage(" username does not exist.");
        }

        if(userRoleIsManager(review.username)) {
        review.handleOuterErrorMessage(" username role is not client.");
        }

        if(!restaurantNameAlreadyExists(review.restaurantName)) {
        review.handleOuterErrorMessage(" restaurant name does not exist.");
        }

        responseHandler = review.responseHandler;
    }
}

