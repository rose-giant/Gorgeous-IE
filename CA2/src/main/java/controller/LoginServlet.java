package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MizDooni;
import objects.Address;
import objects.ResponseHandler;
import objects.User;

import java.io.IOException;
import java.util.Objects;

import static objects.User.CLIENT_ROLE;
import static objects.User.MANAGER_ROLE;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password", password);


        try {
            MizDooni mizDooni = MizDooni.getInstance();
            User user = mizDooni.findUserByUsernameAndPass(username, password);
            if (user != null) {

                if (Objects.equals(user.role, MANAGER_ROLE)) {
                    response.sendRedirect("/manager_home.jsp");
                }
                else if (Objects.equals(user.role, CLIENT_ROLE)) {
                    response.sendRedirect("/client_home");
                }

            } else {
                response.sendRedirect("/error");
            }

        } catch (Exception e) {
            response.sendRedirect("/error");
        }
    }

}