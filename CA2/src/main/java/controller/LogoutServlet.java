package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Table;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MizDooni mizDooni = new MizDooni();
        mizDooni.restartMizdooni();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("logout.jsp");
        requestDispatcher.forward(request, response);
    }
}
