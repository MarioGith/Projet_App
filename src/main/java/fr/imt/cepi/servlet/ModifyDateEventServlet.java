package fr.imt.cepi.servlet;
import fr.imt.cepi.util.Evenement;
import fr.imt.cepi.util.Liste_Event;
import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@MultipartConfig
@WebServlet(name = "Modify_Date_Event", urlPatterns = {"/Modify_Date_Event"})

public class ModifyDateEventServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(ModifyDateEventServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int NumEvenement = Integer.parseInt(request.getParameter("idevent"));
        String horaire = request.getParameter("horaire");
        String date = request.getParameter("date");
        String datec = date+horaire;
        String errorMsg = null;
        if (horaire == null || horaire.equals("")) {
            errorMsg = "L'horaire est obligatoire";
        }
        if (date == null || date.equals("")) {
            errorMsg = "La date est obligatoire";
        }

        if (errorMsg != null) {
            RequestDispatcher rd = request.getRequestDispatcher("/modify_event.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Date datejava = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(datec);
                java.sql.Timestamp datesql = new java.sql.Timestamp(datejava.getTime());
                ps = con.prepareStatement("UPDATE tst.evenement SET datec=? WHERE idevent=?");
                ps.setTimestamp(1, datesql);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (ParseException | SQLException e) {
                e.printStackTrace();
                  logger.error("Problème avec la base de données");
                throw new ServletException("Problème d'accès à la base de données.");
            }
            try {
                ps = con.prepareStatement("select organisateur,type_event,datec,description,prix,idevent,id_createur from tst.evenement where idevent = ?");
                ps.setInt(1, NumEvenement);
                rs = ps.executeQuery();
                if (rs != null && rs.next()) {
                    Evenement evenement = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"), rs.getTimestamp("datec"), rs.getInt("id_createur"));
                    //logger.info("Evenement trouvé" + evenement);
                    request.setAttribute("evenement", evenement);
                    RequestDispatcher rd = request.getRequestDispatcher("/modify_event.jsp");
                    request.setAttribute("message", "<font color=green>" + "La date a ete modifie.  " + "</font>");
                    rd.include(request, response);
                }
            }catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problème d'accès à la base de données");
                throw new ServletException("Problème d'accès à la base de données");
            }
        }
    }
}
