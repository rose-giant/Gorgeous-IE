package Mizdooni.Controller;

import Mizdooni.Model.Restaurant.Restaurant;
import Mizdooni.Model.Restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepo;
    @Autowired
    public RestaurantController() throws Exception {
        restaurantRepo = RestaurantRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Restaurant> getAll() {
        return restaurantRepo.getAll();
    }
    
}
