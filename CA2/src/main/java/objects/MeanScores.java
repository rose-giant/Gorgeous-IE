package objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeanScores {
    public Double meanService ;
    public Double meanFood;
    public Double meanAmbiance;
    public Double meanOverall;
    public Integer numberOfReviews;

    @JsonCreator
    public MeanScores(@JsonProperty("meanService") Double meanService,@JsonProperty("meanFood")  Double meanFood,@JsonProperty("meanAmbiance")  Double meanAmbiance, @JsonProperty("meanOverall") Double meanOverall) {
        this.meanService = meanService;
        this.meanFood = meanFood;
        this.meanAmbiance = meanAmbiance;
        this.meanOverall = meanOverall;
        this.numberOfReviews = 0;
    }

    public void updateMeans(Review newReview){
        meanService = numberOfReviews * meanService + newReview.serviceRate;
        meanFood = numberOfReviews * meanFood + newReview.foodRate;
        meanAmbiance = numberOfReviews * meanAmbiance + newReview.ambianceRate;
        meanOverall = numberOfReviews * meanOverall + newReview.overall;
        numberOfReviews += 1;
    }
}
