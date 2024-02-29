package mizdooni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Review {
    public Double foodRate;
    public Double serviceRate;
    public Double ambianceRate;
    public Double overall;
    public String comment;
    public String reviewDate;
    public String username;
    public String restaurantName;
    public ResponseHandler responseHandler = new ResponseHandler();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void unmarshalFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review review = objectMapper.readValue(jsonString ,Review.class);
        this.foodRate = review.foodRate;
        this.serviceRate = review.serviceRate;
        this.ambianceRate = review.ambianceRate;
        this.overall = review.overall;
        this.comment = review.comment;
        this.username = review.username;
        this.restaurantName = review.restaurantName;

        LocalDateTime now = LocalDateTime.now();
        this.reviewDate = dateTimeFormatter.format(now);
    }

    public boolean isRateValid(Double rate) {
        return rate >= 0 && rate <= 5;
    }

    public boolean isCommentValid(String comment) {
        return !comment.isEmpty();
    }

    public ResponseHandler responseGenerator() {
        ResponseHandler responseHandler1 = new ResponseHandler();
        responseHandler1.responseBody = "";
        Boolean isFoodRateValid = isRateValid(this.foodRate);
        if (!isFoodRateValid) {
            responseHandler1.responseBody += " food rate range is not valid.";
        }

        Boolean isServiceRateValid = isRateValid(this.serviceRate);
        if (!isServiceRateValid) {
            responseHandler1.responseBody += " service rate range is not valid.";
        }

        Boolean isAmbianceRateValid = isRateValid(this.ambianceRate);
        if (!isAmbianceRateValid) {
            responseHandler1.responseBody += " ambiance rate range is not valid.";
        }

        Boolean isOverallRateValid = isRateValid(this.overall);
        if (!isOverallRateValid) {
            responseHandler1.responseBody += " overall rate range is not valid.";
        }

        Boolean isCommentValid = isCommentValid(this.comment);
        if (!isCommentValid) {
            responseHandler1.responseBody += " review comment is empty.";
        }

        responseHandler1.responseStatus = isFoodRateValid && isServiceRateValid && isAmbianceRateValid && isOverallRateValid && isCommentValid;
        if(responseHandler1.responseStatus) {
            responseHandler1.responseBody = " Review added successfully.";
        }
        return responseHandler1;
    }

    public void addReviewHandler(String jsonString) throws JsonProcessingException {
        this.unmarshalFromJson(jsonString);
        this.responseHandler = this.responseGenerator();
    }

    public void handleOuterErrorMessage(String errorMessage) {
        if (this.responseHandler.responseStatus) {
            this.responseHandler.responseBody = errorMessage;
        } else {
            this.responseHandler.responseBody += errorMessage;
        }

        this.responseHandler.responseStatus = false;
    }
}













