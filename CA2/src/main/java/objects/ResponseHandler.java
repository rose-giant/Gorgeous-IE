package objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ResponseHandler {
    public boolean responseStatus;
    public Object responseBody;

    public ResponseHandler(){};
    public ResponseHandler(boolean status, Object body){
        responseStatus = status;
        responseBody = body;
    }
    public String marshalResponse(ResponseHandler responseHandler) throws JsonProcessingException, JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                                    .addModule(new JavaTimeModule())
                                    .build();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseHandler);
        return jsonStr;
    }
}
