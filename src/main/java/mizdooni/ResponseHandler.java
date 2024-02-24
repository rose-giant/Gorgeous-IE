package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseHandler {
    public boolean responseStatus;
    public String responseBody;

    public ResponseHandler(){};
    public ResponseHandler(boolean status, String body){
        responseStatus = status;
        responseBody = body;
    }
    public String marshalResponse(ResponseHandler responseHandler) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseHandler);
        return jsonStr;
    }
}
