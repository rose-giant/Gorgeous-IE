import com.fasterxml.jackson.core.JsonProcessingException;
import mizdooni.ResponseHandler;
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
        assertEquals(jsonString, "{\"responseStatus\":true,\"responseBody\":\"the fault in our stars\"}");
    }
}
