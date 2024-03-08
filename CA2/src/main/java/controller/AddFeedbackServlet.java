package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Reservation;
import objects.Review;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@WebServlet("/feedback")
public class AddFeedbackServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MizDooni mizDooni = new MizDooni();
        mizDooni.removeRedundantReviews();

        double foodRate = Double.parseDouble(request.getParameter("food_rate"));
        double serviceRate = Double.parseDouble(request.getParameter("service_rate"));
        double ambianceRate = Double.parseDouble(request.getParameter("ambiance_rate"));
        double overallRate = Double.parseDouble(request.getParameter("overall_rate"));
        String comment = request.getParameter("comment");

        String restaurantName = mizDooni.getCurrentRestaurant();
        String username = mizDooni.getActiveUser();
        Reservation reservation = new Reservation();
        boolean isAllowed = false;

        for (Reservation r : mizDooni.reservations) {
            if (Objects.equals(r.username, username) && Objects.equals(r.restaurantName, restaurantName)) {
                reservation = r;
                isAllowed = true;
                break;
            }
        }

        if (!isAllowed) {
            request.setAttribute("error", "you haven't even reserved!");
            response.sendRedirect("http://localhost:8080/error");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String now = dateFormat.format(new Date());

        boolean isAllowed2 = false;
        try {
            Date date1 = dateFormat.parse(reservation.datetime);
            Date date2 = dateFormat.parse(now);

            if (date1.compareTo(date2) <= 0) {
                isAllowed2 = true;
            } else if (date1.compareTo(date2) > 0) {

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!isAllowed2) {
            request.setAttribute("error", "you haven't even come to our restaurant yet!");
            response.sendRedirect("/error");
        }

        else {
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
}
