package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;

import java.io.IOException;

@WebServlet("/reservations")
public class ReservationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("reservations.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MizDooni mizDooni = new MizDooni();
        String username = mizDooni.getActiveUser();

        if(username == null) {
            request.getSession().setAttribute("error", "no user exists");
            response.sendRedirect("/error");
        }

        else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("reservations.jsp");
            requestDispatcher.forward(request, response);
            request.setAttribute("username", username);
        }
    }
}
