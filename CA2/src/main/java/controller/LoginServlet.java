package controller;

import models.MizDooni;
import objects.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public String username;
    public String password;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       username = request.getParameter("username");
       password = request.getParameter("password");
        MizDooni mizDooni = null;
        try {
            mizDooni = MizDooni.getInstance();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        assert mizDooni != null;
        User logedInUser = mizDooni.findUserByUsernameAndPass(username, password);
        RequestDispatcher requestDispatcher;
        if (logedInUser != null) {
//            jakarta.servlet.RequestDispatcher requestDispatcher = (jakarta.servlet.RequestDispatcher) request.getRequestDispatcher("index.jsp");
            requestDispatcher = request.getRequestDispatcher("index.jsp");
        } else {
            requestDispatcher = request.getRequestDispatcher("error.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
