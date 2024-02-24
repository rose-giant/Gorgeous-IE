package mizdooni;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CommandHandler {
    public String command;
    public String jsonString;

    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Restourant> restaurants = new ArrayList<Restourant>();

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

    public boolean CommandHandlerCaller(String userIput) throws JsonProcessingException {
        User user = new User();
        user.address = new Address();
        user.responseHandler = new ResponseHandler();
        parseCommand(userIput);
        switch (this.command){
            case "addUser":

                break;
            case "addRestaurant":

                break;
            default:
                return false;
        }

        return true;
    }

    public void mainHandler() throws JsonProcessingException {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("How can I help you baby?");
            String inputString = scanner.nextLine();
            CommandHandlerCaller(inputString);
        }
    }
}

//addUser {"role":"manager","username":"lisa","password":"John Doe","email":"jooe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"lisa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}

//addUser {"role":"client","username":"aa","password":"John Doe","email":"john.doe@example.com", "address":{"city":"milan", "country":"italy"}}
//addRestaurant {"name":"wwe", "description":"d", "type":"a", "managerUsername":"aa", "startTime":"11:00", "endTime":"12:0", "address":{"city":"c", "country":"i", "street":"k"}}
//addRestaurant {"name":"w**we", "description":"d", "type":"", "managerUsername":"eunhoo", "startTime":"11:00", "endTime":"12:11", "address":{"city":"", "country":"i", "street":"k"}}
















