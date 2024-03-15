package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Restaurant;
import objects.Table;

import java.io.IOException;

@WebServlet("/manager_home")
public class ManagerHomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("manager_home.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MizDooni mizDooni = new MizDooni();
        String username = mizDooni.getActiveUser();
        String restaurant = mizDooni.getCurrentRestaurant();
        String tableNumber = request.getParameter("table_number");
        String seatNumber = request.getParameter("seats_number");

        Table table = new Table();
        table.restaurantName = restaurant;
        table.managerUsername = username;
        table.tableNumber = Integer.parseInt(tableNumber);
        table.seatsNumber = Integer.parseInt(seatNumber);

        mizDooni.addTable(table);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("manager_home.jsp");
        requestDispatcher.forward(request, response);
    }
}