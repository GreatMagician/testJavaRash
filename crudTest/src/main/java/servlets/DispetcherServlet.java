package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Александр on 31.10.2015.
 */
@WebServlet("/DispetcherServlet/*")
public class DispetcherServlet extends HttpServlet {

// Метод вызывает сервлет по параметру "to"
protected void forward(String to, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(to);
        dispatcher.forward(request,response);
        }
}
