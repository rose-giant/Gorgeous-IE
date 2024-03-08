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
        } else {
            request.getSession().setAttribute("error", "no restaurant exists");
            response.sendRedirect("/error");
        }

        request.setAttribute("restaurant", restaurantName);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/restaurant.jsp");
        requestDispatcher.forward(request, response);
    }
}