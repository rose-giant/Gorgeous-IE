package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Restaurant;

import java.io.IOException;

@WebServlet("/restaurant/*")
public class RestaurantServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String restaurantName = new String();
        String pathInfo = request.getPathInfo();
        String[] pathComponents = pathInfo.split("/");
        if (pathComponents.length >= 2) {
            restaurantName = pathComponents[1];
            System.out.println("Restaurant name: " + restaurantName);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("No restaurant name provided in the URL");
        }

        request.setAttribute("restaurant", restaurantName);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/restaurant.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");

    }
}