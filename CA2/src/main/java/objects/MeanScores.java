package objects;

public class MeanScores {
    Double meanService = 0.0;
    Double meanFood = 0.0;
    Double meanAmbiance = 0.0;
    Double meanOverall = 0.0;
    Integer numberOfReviews = 0;

    public void updateMeans(Review newReview){
        meanService = numberOfReviews * meanService + newReview.serviceRate;
        meanFood = numberOfReviews * meanFood + newReview.foodRate;
        meanAmbiance = numberOfReviews * meanAmbiance + newReview.ambianceRate;
        meanOverall = numberOfReviews * meanOverall + newReview.overall;
    }
}
