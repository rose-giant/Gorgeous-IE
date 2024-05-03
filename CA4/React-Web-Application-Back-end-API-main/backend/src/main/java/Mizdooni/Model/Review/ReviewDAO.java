package Mizdooni.Model.Review;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ReviewDAO extends DAO {
    public ArrayList<Review> getFromAPI() throws Exception{
        String ReviewsJsonString = getRequest(Constants.GET_REVIEWS_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(ReviewsJsonString, new TypeReference<ArrayList<Review>>(){});
    }
}
