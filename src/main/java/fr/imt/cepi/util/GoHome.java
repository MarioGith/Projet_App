package fr.imt.cepi.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GoHome", urlPatterns = {"/GoHome"})
public class GoHome extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Liste_Event liste = new Liste_Event(request);
        request.setAttribute("liste", liste);
        RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
        rd.include(request, response);
    }
}
