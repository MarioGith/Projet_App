package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Liste_Event;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "New_Event", urlPatterns = {"/New_Event"})
public class NewEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(NewEventServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        String horaire = request.getParameter("horaire");
        String prix = request.getParameter("prix");
        String organisateur = request.getParameter("organisateur");
        String typeevent = request.getParameter("typeevent");
        String date = request.getParameter("date");
        Part filePart = request.getPart("image_pre");
        Part filePart2 = request.getPart("menu");

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
            ResultSet rs = null;
            try {
                ps = con.prepareStatement("insert into tst.evenement(description, prix, date, organisateur, type_event, image_pre, horaire, menu) values (?,?,?,?,?,?,?,?)");
                ps.setString(1, description);
                ps.setString(2, prix);
                ps.setString(3,date);
                ps.setString(4, organisateur);
                ps.setString(5, typeevent);
                ps.setBinaryStream(6,filePart.getInputStream());
                ps.setString(7,horaire);
                ps.setBinaryStream(8,filePart2.getInputStream());
                ps.execute();

                logger.info("Event crée avec description"+description);

                Liste_Event liste = new Liste_Event(request);
                request.setAttribute("liste", liste);
                RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
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
        }
    }

}
