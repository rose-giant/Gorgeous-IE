package Mizdooni.Model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeanScores {
    public Double meanService ;
    public Double meanFood;
    public Double meanAmbiance;
    public Double meanOverall;
    public Integer numberOfReviews;

    @JsonCreator
    public MeanScores(@JsonProperty("meanService") Double meanService, @JsonProperty("meanFood")  Double meanFood, @JsonProperty("meanAmbiance")  Double meanAmbiance, @JsonProperty("meanOverall") Double meanOverall) {
        this.meanService = meanService;
        this.meanFood = meanFood;
        this.meanAmbiance = meanAmbiance;
        this.meanOverall = meanOverall;
        this.numberOfReviews = 0;
    }
}
