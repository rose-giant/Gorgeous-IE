package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Restaurant.Restaurant;
import Mizdooni.Model.Restaurant.RestaurantDAO;
import Mizdooni.Model.Restaurant.RestaurantRepository;

import java.util.ArrayList;
import java.util.Objects;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepository() throws Exception {
        RestaurantDAO dao = new RestaurantDAO();
        restaurants = dao.getFromAPI();
    }

    public static RestaurantRepository getInstance() throws Exception {
        if(instance == null)
            return new RestaurantRepository();
        else return instance;
    }

    public ArrayList<Restaurant> getAll() {
        return restaurants;
    }
}
