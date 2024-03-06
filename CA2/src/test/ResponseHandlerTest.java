import com.fasterxml.jackson.core.JsonProcessingException;
import objects.ResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseHandlerTest {
    ResponseHandler responseHandler;

    @Before
    @BeforeEach
    public void setup() {
        responseHandler = new ResponseHandler();
        responseHandler.responseBody = "";
    }

    @Test
    public void marshalResponseHandler() throws JsonProcessingException {
        responseHandler.responseBody = "the fault in our stars";
        responseHandler.responseStatus = true;
        String jsonString = responseHandler.marshalResponse(responseHandler);
        String expected = "{\n" +
                "  \"responseStatus\" : true,\n" +
                "  \"responseBody\" : \"the fault in our stars\"\n" +
                "}";
//        assertEquals(expected, jsonString);
    }
}
