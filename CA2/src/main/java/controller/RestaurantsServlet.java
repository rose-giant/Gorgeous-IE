package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;

import java.io.IOException;

@WebServlet("/restaurants")
public class RestaurantsServlet extends HttpServlet {
    public String restaurantsHtml = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");
        if (action == null || search == null) {
            action = "";
            search = "";
        }

        MizDooni mizDooni = new MizDooni();
        restaurantsHtml = mizDooni.createHTMLForRestaurantsList(action, search);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("restaurants.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");
        if (action == null || search == null) {
            action = "";
            search = "";
        }
        MizDooni mizDooni = new MizDooni();
        restaurantsHtml = mizDooni.createHTMLForRestaurantsList(action, search);
//        System.out.println("filtered data is " + restaurantsHtml);
    }
}