package fr.imt.cepi.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GoHistorique", urlPatterns = {"/GoHistorique"})
public class GoHistorique extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Liste_Menu liste = new Liste_Menu(request);
        request.setAttribute("liste", liste);
        RequestDispatcher rd = request.getRequestDispatcher("/historique.jsp");
        rd.include(request, response);
    }
}
