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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password", password);

        MizDooni mizDooni = new MizDooni();
        User user = new User();
        user.username = username;
        user.password = password;
        user.address = new Address();
        user.responseHandler = new ResponseHandler();

        try {
            if (mizDooni.userAlreadyExists(user)) {
                user.role = mizDooni.findUserByUserName(username).role;

                if (Objects.equals(user.role, "manager")) {
                    response.sendRedirect("/manager_home.jsp");
                }
                else if (Objects.equals(user.role, "client")) {
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