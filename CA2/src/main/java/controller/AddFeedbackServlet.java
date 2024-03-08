package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Review;

import java.io.IOException;

@WebServlet("/feedback")
public class AddFeedbackServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MizDooni mizDooni = new MizDooni();
        double foodRate = Double.parseDouble(request.getParameter("food_rate"));
        double serviceRate = Double.parseDouble(request.getParameter("service_rate"));
        double ambianceRate = Double.parseDouble(request.getParameter("ambiance_rate"));
        double overallRate = Double.parseDouble(request.getParameter("overall_rate"));
        String comment = request.getParameter("comment");
        String restaurantName = mizDooni.getCurrentRestaurant();
        String username = mizDooni.getActiveUser();
        Review review = new Review();
        review.restaurantName = restaurantName;
        review.username = username;
        review.foodRate = foodRate;
        review.serviceRate = serviceRate;
        review.ambianceRate = ambianceRate;
        review.overall = overallRate;
        review.comment = comment;
        mizDooni.reviews.add(review);
        mizDooni.saveReview(review);

        response.sendRedirect("http://localhost:8080/restaurant/"+restaurantName);

    }
}
