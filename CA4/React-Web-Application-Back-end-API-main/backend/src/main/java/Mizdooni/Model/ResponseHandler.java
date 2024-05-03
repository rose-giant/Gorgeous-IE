package Mizdooni.Model;


public class ResponseHandler {
    public boolean responseStatus;
    public Object responseBody;

    public ResponseHandler(){};
    public ResponseHandler(boolean status, Object body){
        responseStatus = status;
        responseBody = body;
    }
}
