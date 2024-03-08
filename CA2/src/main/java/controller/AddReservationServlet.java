package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Reservation;
import java.util.Random;
import java.io.IOException;

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        MizDooni mizDooni = new MizDooni();
        String username = mizDooni.getActiveUser();
        if(username == null) {
            request.getSession().setAttribute("error", "no user exists");
            response.sendRedirect("/error");
        }

        else {
            String tableNumber = request.getParameter("table_number");
            String reservationDate = request.getParameter("date_time");
            Reservation reservation = new Reservation();
            reservation.datetime = reservationDate;
            reservation.tableNumber = Integer.parseInt(tableNumber);
            System.out.println(tableNumber );
            reservation.username = username;
            reservation.restaurantName = mizDooni.getCurrentRestaurant();
            Random random = new Random();
            reservation.reservationNumber = random.nextInt(1000);
            mizDooni.addReservation(reservation);
            response.sendRedirect("reservations.jsp");
        }
    }
}
