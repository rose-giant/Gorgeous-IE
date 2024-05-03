package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class RestaurantDAO extends DAO {
    public ArrayList<Restaurant> getFromAPI() throws Exception{
        String RestaurantsJsonString = getRequest(Constants.GET_RESTAURANTS_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(RestaurantsJsonString, new TypeReference<ArrayList<Restaurant>>(){});
    }
}
