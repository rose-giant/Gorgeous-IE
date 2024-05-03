package Mizdooni.Controller;


import Mizdooni.Model.Review.Review;
import Mizdooni.Model.Review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewRepository reviewRepo;
    @Autowired
    public ReviewController() throws Exception {
        reviewRepo = ReviewRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Review> getAll() {
        return reviewRepo.getAll();
    }
}
