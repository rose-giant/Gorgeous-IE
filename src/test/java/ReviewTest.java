import mizdooni.ResponseHandler;
import mizdooni.Review;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {
    Review review;
    ResponseHandler responseHandler;

    @Before
    @BeforeEach
    public void setup() {
        review = new Review();
        responseHandler = new ResponseHandler();
        review.responseHandler = responseHandler;
    }

    @AfterEach
    public void teardown() {
        review = null;
        responseHandler = null;
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.2, 4.55, 0, 5})
    public void isRateValidReturnsTrueForValidRates(Double rate) {
        assertTrue(review.isRateValid(rate));
    }

    @ParameterizedTest
    @ValueSource(doubles = {11.2, -4.55, 10, 500, -99})
    public void isRateValidReturnsFalseForInvalidRates(Double rate) {
        assertFalse(review.isRateValid(rate));
    }

    @Test
    public void handleNoneExistingUserConcatsNewErrorMessage() {
        review.responseHandler.responseBody = "anyeong hasaeyo!";
        review.handleOuterErrorMessage("saranghae!");
        assertEquals(review.responseHandler.responseBody, "anyeong hasaeyo!saranghae!");
        assertFalse(review.responseHandler.responseStatus);
    }

    @Test
    public void responseGeneratorGeneratesSuccessfullResponseForValidReview(){
        review.username = "razi";
        review.restaurantName = "charsotoon";
        review.foodRate = 4.6;
        review.serviceRate = 4.4;
        review.ambianceRate = 4.8;
        review.comment = "pasta mashissoe!";
        review.overall = 4.7;
        review.responseHandler = review.responseGenerator();

        assertTrue(review.responseHandler.responseStatus);
        assertEquals(review.responseHandler.responseBody, " Review added successfully.");
    }

    @Test
    public void responseGeneratorGeneratesResponseForInvalidReview(){
        review.username = "razi";
        review.restaurantName = "charsotoon";
        review.foodRate = 14.6;
        review.serviceRate = 4.4;
        review.ambianceRate = 4.8;
        review.comment = "pasta mashissoe!";
        review.overall = 4.7;
        review.responseHandler = review.responseGenerator();

        assertFalse(review.responseHandler.responseStatus);
        assertEquals(review.responseHandler.responseBody, " food rate range is not valid.");
    }

    @Test
    public void responseGeneratorGeneratesResponseFor2InvalidReviewFields(){
        review.username = "razi";
        review.restaurantName = "charsotoon";
        review.foodRate = 14.6;
        review.serviceRate = 4.4;
        review.ambianceRate = 4.8;
        review.comment = "";
        review.overall = 4.7;
        review.responseHandler = review.responseGenerator();

        assertFalse(review.responseHandler.responseStatus);
        assertEquals(review.responseHandler.responseBody, " food rate range is not valid. review comment is empty.");
    }

    @Test
    public void responseGeneratorGeneratesResponseFor4InvalidReviewFields(){
        review.username = "razi";
        review.restaurantName = "";
        review.foodRate = 14.6;
        review.serviceRate = 4.4;
        review.ambianceRate = 54.8;
        review.comment = "";
        review.overall = 4.7;
        review.responseHandler = review.responseGenerator();

        assertFalse(review.responseHandler.responseStatus);
        assertEquals(review.responseHandler.responseBody, " food rate range is not valid. ambiance rate range is not valid. review comment is empty.");
    }
}
