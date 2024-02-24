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

    public boolean restaurantManagerRoleIsCorrect(String managerUsername) {
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

    public void CommandHandlerCaller() throws JsonProcessingException {
        User user = new User();
        user.address = new Address();
        user.responseHandler = new ResponseHandler();

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
                Restourant restourant = new Restourant();
                restourant.address = new Address();
                restourant.responseHandler = new ResponseHandler();
                restourant.addRestaurantHandler(this.jsonString);

                if (!restaurantManagerUsernameExists(restourant.managerUsername)) {
                    restourant.handleNoneExistingUsername();
                }

                if(!restaurantManagerRoleIsCorrect(restourant.managerUsername)) {
                    restourant.handleIncorrectManagerRole();
                }

                if(restaurantNameAlreadyExists(restourant.name)) {
                    restourant.handleRepeatedRestaurantName();
                }

                if (restourant.responseHandler.responseStatus) {
                    restaurants.add(restourant);
                    System.out.println(restaurants.size());
                }

                this.responseHandler = restourant.responseHandler;
                break;

            case "addTable":
                Table table = new Table(this.jsonString);
                Restourant relatedRestaurant = findRestaurantByName(table.restaurantName);
                User manager = findUserByUserName(table.managerUsername);
                if (manager == null){
                    this.responseHandler.responseBody += "Manager username not found.\n";
                } else if (manager.role == User.CLIENT_ROLE) {
                    this.responseHandler.responseBody += "This user is not allowed to add a table.\n";
                }
                if (relatedRestaurant == null){
                    this.responseHandler.responseBody += "Restaurant name not found.\n";
                }
                if (findTableByRestaurantNameAndTableNumber(table.restaurantName, table.tableNumber) != null){
                    this.responseHandler.responseBody += "Table number already exists.\n";
                }
                break;

            default:
        }
    }

    public void mainHandler() throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);
        String inputString;
        while (!(inputString =scanner.nextLine()).equals("stop")) {
            System.out.println("How can I help you baby?");
            parseCommand(inputString);
            CommandHandlerCaller();
            System.out.println(this.responseHandler.marshalResponse(this.responseHandler));
        }
    }
}

//addUser {"role":"manager","username":"lisa","password":"John Doe","email":"jooe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"lisa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}

//addUser {"role":"client","username":"aa","password":"John Doe","email":"john.doe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"aa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}
//addRestaurant {"name":"w**we", "description":"d", "type":"", "managerUsername":"eunhoo", "startTime":"11:00", "endTime":"12:11", "address":{"city":"", "country":"i", "street":"k"}}
















