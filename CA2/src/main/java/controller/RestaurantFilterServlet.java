package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;

import java.io.IOException;
@WebServlet("/filterRestaurants")
public class RestaurantFilterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in filtering...");
        String action = request.getParameter("action");
        String search = request.getParameter("search");
        if (action == null || search == null) {
            action = "";
            search = "";
        }

        System.out.println(1+action);
        System.out.println(2+search);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("restaurants.jsp");
        requestDispatcher.forward(request, response);
    }}
