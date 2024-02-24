import com.fasterxml.jackson.core.JsonProcessingException;
import mizdooni.CommandHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandHandlerTest {

    @Test
    public void parseCommandParsesCommandAndJsonBody() throws JsonProcessingException {
        CommandHandler commandHandler = new CommandHandler();
        String inputJsonString = "{\"username\": \"razie\"}";
        String inputCommand = "addUser ";
        commandHandler.parseCommand(inputCommand+inputJsonString);
        assertEquals(inputJsonString, commandHandler.jsonString);
    }
}
