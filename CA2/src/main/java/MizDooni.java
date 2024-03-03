import models.Reader;
import objects.*;

import java.util.ArrayList;

public class MizDooni {
    public ResponseHandler responseHandler = new ResponseHandler();
    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Restourant> restaurants = new ArrayList<>();
    public ArrayList<Table> tables = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();


    public MizDooni() {
        Reader rd = new Reader();
        users = rd.ReadFromFile("users", User.class);
        restaurants = rd.ReadFromFile("restaurants", Restourant.class);
        tables = rd.ReadFromFile("tables", Table.class);
        reservations = rd.ReadFromFile("reservations", Reservation.class);

    }
}
//case "addUser":
//        user.addUserHandler(this.jsonString);
//        if(userAlreadyExists(user)) {
//        user.handleRepeatedUser();
//        }
//        if (user.responseHandler.responseStatus) {
//        users.add(user);
//        }
//
//        this.responseHandler = user.responseHandler;
//
//        break;
//        case "addRestaurant":
//        restourant.addRestaurantHandler(this.jsonString);
//
//        if (!restaurantManagerUsernameExists(restourant.managerUsername)) {
//        restourant.handleOuterErrorMessage(" manager username does not exist.");
//        }
//
//        if(!userRoleIsManager(restourant.managerUsername)) {
//        restourant.handleOuterErrorMessage(" manager role is not correct.");
//        }
//
//        if(restaurantNameAlreadyExists(restourant.name)) {
//        restourant.handleOuterErrorMessage(" restaurant name is repeated.");
//        }
//
//        if (restourant.responseHandler.responseStatus) {
//        restaurants.add(restourant);
//        System.out.println(restaurants.size());
//        }
//
//        this.responseHandler = restourant.responseHandler;
//        break;
//
//        case "searchRestaurantsByName":
//        this.responseHandler = searchRestaurantByNameHandler(this.jsonString);
//        break;
//
//        case "searchRestaurantsByType":
//        this.responseHandler = searchRestaurantByTypeHandler(this.jsonString);
//        break;
//
//        case "addTable":
//        Table table = new Table(this.jsonString);
//        relatedRestaurant = findRestaurantByName(table.restaurantName);
//        relatedUser = findUserByUserName(table.managerUsername);
//        if (relatedUser == null){
//        throw new Exception("Manager username not found.");
//        } else if (relatedUser.role == User.CLIENT_ROLE) {
//        throw new Exception("This user is not allowed to add a table.");
//        }
//        if (relatedRestaurant == null){
//        throw new Exception("Restaurant name not found.");
//        }
//        if (findTableByRestaurantNameAndTableNumber(table.restaurantName, table.tableNumber) != null){
//        throw new Exception("Table number already exists.");
//        }
//
//        tables.add(table);
//        relatedRestaurant.addTable(table);
//        returnedData = "Table added successfully.";
//        this.responseHandler = new ResponseHandler(true, returnedData);
//        break;
//
//        case "reserveTable":
//        Reservation reservation = new Reservation(this.jsonString);
//        relatedRestaurant = findRestaurantByName(reservation.restaurantName);
//        relatedUser = findUserByUserName(reservation.username);
//        relatedTable = findTableByRestaurantNameAndTableNumber(reservation.restaurantName, reservation.tableNumber);
//        if (relatedUser == null){
//        throw new Exception("Username not found.");
//        } else if (relatedUser.role == User.MANAGER_ROLE) {
//        throw new Exception("This user is not allowed to reserve a table.");
//        }
//        if (relatedRestaurant == null){
//        throw new Exception("Restaurant name not found.");
//        } else if (relatedTable == null){
//        throw new Exception("Table number not found.");
//        } else if (!relatedRestaurant.isOpenAt(reservation.datetimeFormatted)){
//        throw new Exception("Restaurant doesn't work at this DateTime");
//        }
//        relatedRestaurant.reserve(reservation);
//        relatedTable.addReservation(reservation);
//        reservations.add(reservation);
//
//        //Because we need to handle showReservationHistory command, it's better to add reservation to the ordering user too.
//        relatedUser.addReservation(reservation);
//
//        returnedData = new Reservation.ResNumber(reservation.reservationNumber);
//        this.responseHandler = new ResponseHandler(true, returnedData);
//        break;
//
//        case "cancelReservation":
//        Reservation.CancelReservation cr = om.readValue(this.jsonString, Reservation.CancelReservation.class);
//        relatedUser = findUserByUserName(cr.username);
//        relatedReservation = findReservationByNumber(cr.reservationNumber);
//        if(!relatedUser.hasReserved(cr.reservationNumber)){
//        throw new Exception("Reservation not found");
//        }
//        relatedTable = findTableByRestaurantNameAndTableNumber(relatedReservation.restaurantName, relatedReservation.tableNumber);
//        relatedReservation.checkSafetyRemoval();
//
//        reservations.remove(relatedReservation);
//        relatedUser.removeReservation(relatedReservation);
//        relatedTable.removeReservation(relatedReservation);
//
//        returnedData = "Reservation cancelled successfully";
//        this.responseHandler = new ResponseHandler(true, returnedData);
//        break;
//
//        case "showReservationHistory":
//        User.UserName un = om.readValue(this.jsonString, User.UserName.class);
//        relatedUser = findUserByUserName(un.username);
//
//        returnedData = relatedUser.getReservationHistory();
//        this.responseHandler = new ResponseHandler(true, returnedData);
//        break;
//
//        case "showAvailableTables":
//        Restourant.RestaurantName rn = om.readValue(this.jsonString, Restourant.RestaurantName.class);
//        relatedRestaurant = findRestaurantByName(rn.restaurantName);
//
//        if (relatedRestaurant == null) {
//        throw new Exception("Restaurant name not found.");
//        }
//        returnedData = relatedRestaurant.getAvailableTables();
//        this.responseHandler = new ResponseHandler(true, returnedData);
//        break;
//
//        case "addReview":
//        Review review = new Review();
//        review.addReviewHandler(this.jsonString);
//        User user1 = new User();
//        user1.username = review.username;
//        if(!userAlreadyExists(user1)) {
//        review.handleOuterErrorMessage(" username does not exist.");
//        }
//
//        if(userRoleIsManager(review.username)) {
//        review.handleOuterErrorMessage(" username role is not client.");
//        }
//
//        if(!restaurantNameAlreadyExists(review.restaurantName)) {
//        review.handleOuterErrorMessage(" restaurant name does not exist.");
//        }
//
//        this.responseHandler = review.responseHandler;
//        break;