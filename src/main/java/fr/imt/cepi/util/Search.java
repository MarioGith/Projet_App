package fr.imt.cepi.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class Search extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Liste_Search liste_search = new Liste_Search(request);
            request.setAttribute("liste_search", liste_search);
            RequestDispatcher rd = request.getRequestDispatcher("/resulat.jsp");
            rd.include(request, response);
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Liste_Search liste_search = new Liste_Search(request);
        request.setAttribute("liste_search", liste_search);
        RequestDispatcher rd = request.getRequestDispatcher("/resultat.jsp");
        rd.include(request, response);
    }
}