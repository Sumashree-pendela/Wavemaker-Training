package todo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.LoggedInUser;
import todo.dto.Login;
import todo.exception.LoginNotFoundException;
import todo.service.LoggedInUserService;
import todo.service.LoginService;
import todo.service.impl.LoggedInUserServiceImpl;
import todo.service.impl.LoginServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    LoginService loginService = new LoginServiceImpl();

    LoggedInUserService loggedInUserService = new LoggedInUserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        PrintWriter printWriter = resp.getWriter();

        Login login = null;

        Login reqLogin = new Login();
        reqLogin.setUserName(userName);
        reqLogin.setPassword(password);

        try {
            login = loginService.getByUserName(reqLogin.getUserName());
        } catch (LoginNotFoundException e) {
            resp.sendRedirect("login.html");
        }

        //check if password is same
        try {
            if (login.getPassword().equals(reqLogin.getPassword())) {
                UUID uuid = UUID.randomUUID();
                String cookieValue = uuid.toString();


                HttpSession httpSession = req.getSession(true);
                Cookie cookie = new Cookie("loginCookie", cookieValue);
                httpSession.setAttribute("loginCookie", cookieValue);

                //setting userName...
                LoggedInUser loggedInUser = new LoggedInUser();
                loggedInUser.setUserName(reqLogin.getUserName());
                loggedInUser.setPassword(reqLogin.getPassword());
                loggedInUserService.update(loggedInUser);
                logger.debug("Inserted in logged in user");

                cookie.setMaxAge(60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                resp.addCookie(cookie);
                resp.sendRedirect("index.html");
                printWriter.println("Logged In successfully");
            } else {
                resp.sendRedirect("login.html");
            }
        } catch (Exception e) {
            resp.sendRedirect("login.html");
        }

    }

}
