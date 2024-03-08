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
            MizDooni mizDooni = new MizDooni();
            User user = new User();
            user.address = new Address();
            user.responseHandler = new ResponseHandler();
            user = mizDooni.findUserByUsernameAndPass(username, password);

            mizDooni.saveActiveUser(user);
            if (Objects.equals(user.role, "manager")) {
                System.out.println(2);
                response.sendRedirect("/manager_home");
            }
            else if (Objects.equals(user.role, "client")) {
                System.out.println(3);
                response.sendRedirect("/client_home");
            }

            else {
                request.getSession().setAttribute("error", "no user exists");
                response.sendRedirect("/error");
            }

        } catch (Exception e) {
            request.getSession().setAttribute("error", "no user exists");
            response.sendRedirect("/error");
        }
    }

}