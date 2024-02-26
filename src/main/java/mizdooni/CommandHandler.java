package mizdooni;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CommandHandler {
    public String command;
    public String jsonString;
    public ResponseHandler responseHandler = new ResponseHandler();
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Restourant> restaurants = new ArrayList<>();
    public ArrayList<Table> tables = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public void parseCommand(String userInput) {
       this.jsonString = "";
       String[] splittedInput = userInput.split(" ");
       this.command = splittedInput[0];

       for (int i = 1 ; i < splittedInput.length ; i++) {
           this.jsonString += splittedInput[i];

           if (i < splittedInput.length - 1) {
               this.jsonString += " ";
           }
       }
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
                if (Objects.equals(value.role, "manager")) {
                    isCorrect = true;
                    break;
                }
            }
        }
        return isCorrect;
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

    public Restourant findRestaurantByName(String restaurantName) {
        for(Restourant rest: restaurants) {
            if(Objects.equals(rest.name, restaurantName)) {
                return rest;
            }
        }
        return null;
    }

    public Table findTableByRestaurantNameAndTableNumber(String restaurantName, String tableNumber) {
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

    public ArrayList<Table> findTablesByRestaurantName(String restaurantName) {
        ArrayList<Table> wantedTables = new ArrayList<>();
        for(Table tb: tables) {
            if(Objects.equals(tb.restaurantName, restaurantName)) {
                wantedTables.add(tb);
            }
        }
        return wantedTables;
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

    public void CommandHandlerCaller() throws JsonProcessingException {
        try{
            User user = new User();
            user.address = new Address();
            user.responseHandler = new ResponseHandler();
            Restourant restourant = new Restourant();
            restourant.address = new Address();
            restourant.responseHandler = new ResponseHandler();
            String returnedData;
            Restourant relatedRestaurant;
            User relatedUser;
            Table relatedTable;

            switch (this.command){
                case "addUser":
                    user.addUserHandler(this.jsonString);
                    if(userAlreadyExists(user)) {
                        user.handleRepeatedUser();
                    }
                    if (user.responseHandler.responseStatus) {
                        users.add(user);
                    }

                    this.responseHandler = user.responseHandler;

                    break;
                case "addRestaurant":
                    restourant.addRestaurantHandler(this.jsonString);

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

                    this.responseHandler = restourant.responseHandler;
                    break;

                case "searchRestaurantsByName":
                    this.responseHandler = searchRestaurantByNameHandler(this.jsonString);
                    break;

                case "searchRestaurantsByType":
                    this.responseHandler = searchRestaurantByTypeHandler(this.jsonString);
                    break;

                case "addTable":
                    Table table = new Table(this.jsonString);
                    relatedRestaurant = findRestaurantByName(table.restaurantName);
                    relatedUser = findUserByUserName(table.managerUsername);
                    if (relatedUser == null){
                        throw new Exception("Manager username not found.");
                    } else if (relatedUser.role == User.CLIENT_ROLE) {
                        throw new Exception("This user is not allowed to add a table.");
                    }
                    if (relatedRestaurant == null){
                        throw new Exception("Restaurant name not found.");
                    }
                    if (findTableByRestaurantNameAndTableNumber(table.restaurantName, table.tableNumber) != null){
                        throw new Exception("Table number already exists.");
                    }

                    tables.add(table);
                    returnedData = "Table added successfully.";
                    this.responseHandler = new ResponseHandler(true, returnedData);
                    break;

                case "reserveTable":
                    Reservation reservation = new Reservation(this.jsonString);
                    relatedRestaurant = findRestaurantByName(reservation.restaurantName);
                    relatedUser = findUserByUserName(reservation.username);
                    relatedTable = findTableByRestaurantNameAndTableNumber(reservation.restaurantName, reservation.tableNumber);
                    if (relatedUser == null){
                        throw new Exception("Username not found.");
                    } else if (relatedUser.role == User.MANAGER_ROLE) {
                        throw new Exception("This user is not allowed to reserve a table.");
                    }
                    if (relatedRestaurant == null){
                        throw new Exception("Restaurant name not found.");
                    } else if (relatedTable == null){
                        throw new Exception("Table number not found.");
                    } else if (relatedTable.hasDateTimeConflict(reservation)) {
                        throw new Exception("This table already reserved");
                    } else if (!relatedRestaurant.isOpenAt(reservation.datetime)){
                        throw new Exception("Restaurant doesn't work at this DateTime");
                    }

                    reservations.add(reservation);
                    returnedData = String.format("{“reservationNumber”: %d}", reservation.reservationNumber);
                    this.responseHandler = new ResponseHandler(true, returnedData);
                    break;

                case "addReview":
                    Review review = new Review();
                    review.addReviewHandler(this.jsonString);
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

                    this.responseHandler = review.responseHandler;
                    break;
                default:
            }

        }catch (Exception error){
            this.responseHandler = new ResponseHandler(false, error.getMessage());
        }
    }

    public void mainHandler() throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);
        String inputString;
        while (!(inputString =scanner.nextLine()).equals("stop")) {
            parseCommand(inputString);
            CommandHandlerCaller();
            System.out.println(this.responseHandler.marshalResponse(this.responseHandler));
        }
    }
}

//addReview {"foodRate": 12, "comment":"a", "serviceRate":3, "ambianceRate":1, "overall":30, "username": "rose", "restaurantName": "nakhl"}
//addReview {"foodRate": 12, "comment":"", "serviceRate":3, "ambianceRate":1, "overall":3}
//searchRestaurantsByType {"type":"italian"}

//addUser {"role":"manager","username":"lisa","password":"John Doe","email":"jooe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"lisa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}

//addUser {"role":"client","username":"aa","password":"John Doe","email":"john.doe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"aa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}
//addRestaurant {"name":"w**we", "description":"d", "type":"", "managerUsername":"eunhoo", "startTime":"11:00", "endTime":"12:11", "address":{"city":"", "country":"i", "street":"k"}}




//addReview {"foodRate": 2, "comment":"a", "serviceRate":3, "ambianceRate":1, "overall":3, "username": "lia", "restaurantName": "wwe"}











