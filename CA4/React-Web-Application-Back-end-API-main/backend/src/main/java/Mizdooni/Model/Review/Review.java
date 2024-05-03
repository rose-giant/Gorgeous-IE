package Mizdooni.Model.Review;


import Mizdooni.Model.ResponseHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class Review {
    public Double ambianceRate;
    public String comment;
    public Double foodRate;
    public Double overallRate;
    public String restaurantName;
    public Double serviceRate;
    public String username;

    public Review(@JsonProperty("ambianceRate") Double ambianceRate,
                  @JsonProperty("comment") String comment,
                  @JsonProperty("foodRate") Double foodRate,
                  @JsonProperty("overallRate") Double overallRate,
                  @JsonProperty("restaurantName") String restaurantName,
                  @JsonProperty("serviceRate") Double serviceRate,
                  @JsonProperty("username") String username) {
        this.ambianceRate = ambianceRate;
        this.comment = comment;
        this.foodRate = foodRate;
        this.overallRate = overallRate;
        this.restaurantName = restaurantName;
        this.serviceRate = serviceRate;
        this.username = username;
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

        Boolean isOverallRateValid = isRateValid(this.overallRate);
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
}













