package mizdooni.models;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.mainHandler();
    }
}