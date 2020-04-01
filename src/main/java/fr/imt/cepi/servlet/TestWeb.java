package fr.imt.cepi.servlet;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "New_Event", urlPatterns = {"/New_Event"})
public class TestWeb extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(TestWeb.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        String horaire = request.getParameter("horaire");
        String prix = request.getParameter("prix");
        String errorMsg = null;
        if (description == null || horaire.equals("")) {
            errorMsg = "La description est obligatoire";
        }
        if (description == null || horaire.equals("")) {
            errorMsg = "L'horaire est obligatoire";
        }

        if (errorMsg != null) {
            RequestDispatcher rd = request.getRequestDispatcher("/New_Event.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement("insert into event(descri, prix, horaire, image) values (?,?,?)");
                ps.setString(1, description);
                ps.setString(2, prix);
                ps.setString(3, horaire);
                ps.execute();

                logger.info("Event crée avec description"+description);

                // forward to login page to login
                RequestDispatcher rd = request.getRequestDispatcher("/login.html");
                request.setAttribute("message",
                        "<font color=green>Enregistrement effectué avec succès, veuillez vous identifier.</font>");
                rd.include(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problème avec la base de données");
                throw new ServletException("Problème d'accès à la base de données.");
            } finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("Erreur lors de la fermeture du statement");
                }
            }
        }
    }

}
