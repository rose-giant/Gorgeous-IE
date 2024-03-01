package mizdooni.controller;
import mizdooni.models.User;
import mizdooni.models.UserCSVReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public ArrayList<User> users = new ArrayList<>();
//    public static void main(String[] args) throws ServletException {
//        LoginServlet loginServlet = new LoginServlet();
//       // loginServlet.doPost(null,null);
//    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserCSVReader userCSVReader = new UserCSVReader();
        users = userCSVReader.loadUsers();
        User user = isLoginSuccessful(username,password);
        if (user == null) {
            // Set error message
            request.setAttribute("errorMessage", "Invalid username or password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.html");
            try {
                dispatcher.forward(request, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Successful login, proceed with your logic
        }

    }

    private User isLoginSuccessful(String username, String password) {
        for(User user: users) {
            if(Objects.equals(user.username, username) && Objects.equals(user.password, password)) {
                return user;
            }
        }

        return null;
    }
}
