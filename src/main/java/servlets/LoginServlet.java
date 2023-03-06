package servlets;

import com.example.demo.service.impl.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userLogin = request.getParameter("login");
        Integer idUserByLogin = userService.findUserByLogin("MMM").getId();
        String userPassword = request.getParameter("password");
        if ("MMM".equals(userLogin) && "user1".equals(userPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", idUserByLogin);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
