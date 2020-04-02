package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "event_register", urlPatterns = {"/event_register"})
public class EventRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(EventRegisterServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idevent = request.getParameter("idevent");
        int idEvent = 0;
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        int idUser = ((Utilisateur) session.getAttribute("utilisateur")).getId();

        String errorMsg = null;
        if (errorMsg==null || idevent.equals("")) {
            errorMsg = "L'évènement est obligatoire";
        }
        if (errorMsg != null) {
            RequestDispatcher rd = request.getRequestDispatcher("/event_register.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        }else {

            if (request.getParameter("yes") != null) {
                Connection con = (Connection) getServletContext().getAttribute("DBConnection");
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    ps = con.prepareStatement("insert into tst.lien(idEvent,idUser) values (?,?)");
                    ps.setInt(1, idEvent);
                    ps.setInt(2, idUser);
                    ps.execute();

                    logger.info("Inscription faites à "+idevent);

                    // forward to accueil page
                    RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
                    request.setAttribute("message",
                            "<font color=green>Enregistrement effectué avec succès</font>");
                    rd.include(request, response);

                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("Problème avec la base de données");
                    throw new ServletException("Problème d'accès à la base de données.");
                } finally {
                    try {
                        assert ps != null;
                        ps.close();
                    } catch (SQLException e) {
                        logger.error("Erreur lors de la fermeture du statement");
                    }
                }
            } else {
                if (request.getParameter("no") != null) {
                    // forward to acceuil page
                    RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
                    request.setAttribute("message",
                            "<font color=green>Enregistrement effectué avec succès</font>");
                    rd.include(request, response);
                }
            }
        }
    }

}
